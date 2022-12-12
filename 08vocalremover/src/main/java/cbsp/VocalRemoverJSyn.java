package cbsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

public class VocalRemoverJSyn {
		
	public void run() {

		File inputFile = new File("src/main/media/Phlex_short.wav");
		File outputFile = new File("src/main/media/Output.wav");

		double cutOff = 185.0;

		Synthesizer synthesizer;

		LineOut lineOut;

		FloatSample sample;
		FloatSample originalSample;

		VariableRateDataReader samplePlayer;
		VariableRateDataReader samplePlayerOriginal;

		WaveRecorder recorder;

		Multiply mpo1;
		Multiply mpo2;
		Multiply mpi1;
		Multiply mpi2;
		Multiply mpf1;
		Multiply mpf2;

		Add mixo;
		Add mixi;
		Add mixf;

		FilterLowPass filter;

		// /*
		try {

			synthesizer = JSyn.createSynthesizer();

			sample = SampleLoader.loadFloatSample(inputFile);

			double fr = sample.getFrameRate();
			int ch = sample.getChannelsPerFrame();
			int fs = sample.getNumFrames();

			System.out.println("Sample has: channels  = " + ch);
			System.out.println("            frames    = " + fs);
			System.out.println("            rate      = " + fr);

			float[] dsamples = new float[fs * ch];
			float[] dsamplesOriginal = new float[fs * ch];

			// TASK 1

			// Copy samples from SampleLoader to dsamplesOriginal

			// Copy samples from SampleLoader to dsamples, where you invert the sample values on one of the channels.

			for (int i = 0; i < dsamples.length; i++){
				float current_value = (float) sample.readDouble(i);
				dsamplesOriginal[i] = current_value;
				if (i % 2 == 0){
					dsamples[i] = current_value;
				}
				else{
					dsamples[i] = -current_value;
				}
			}

			// Create new WaveRecorder.
			try {
				recorder = new WaveRecorder(synthesizer, outputFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}

			// Create and add new LineOut to the synthesizer.
			synthesizer.add(lineOut = new LineOut());

			// Create originalSample object with new FloatSample from dsamplesOriginal.
			originalSample = new FloatSample(dsamplesOriginal, ch);
			originalSample.setFrameRate(fr);

			// Create sample object with new FloatSample from dsamples.
			// (?)
			sample = new FloatSample(dsamples, ch);
			sample.setFrameRate(fr);

			// Create samplePlayer object with new VariableRateStereoReader and fill its dataQueue with sample.
			synthesizer.add(samplePlayer = new VariableRateStereoReader());
			samplePlayer.dataQueue.queue(sample);
			samplePlayer.rate.set(fr);

			// Create samplePlayerOriginal object with new VariableRateStereoReader and fill its dataQueue with originalSample.
			synthesizer.add(samplePlayerOriginal = new VariableRateStereoReader());
			samplePlayerOriginal.dataQueue.queue(originalSample);
			samplePlayerOriginal.rate.set(fr);

			// Multiply each channel of samplePlayerOriginal with 0.5 and then sum them together.
			Arrays.fill(dsamples, 0.5F);
			FloatSample halfs = new FloatSample(dsamples);
			halfs.setChannelsPerFrame(2);
			VariableRateStereoReader half = new VariableRateStereoReader();
			half.dataQueue.queue(halfs);
			synthesizer.add(half);

			synthesizer.add(mpo1 = new Multiply());
			mpo1.inputA.connect(0,samplePlayerOriginal.output,0);
			mpo1.inputB.connect(0,half.output,1);

			synthesizer.add(mpo2 = new Multiply());
			mpo2.inputA.connect(0,samplePlayerOriginal.output,1);
			mpo2.inputB.connect(0,half.output,0);

			synthesizer.add(mixo = new Add());
			mixo.inputA.connect(mpo1.output);
			mixo.inputB.connect(mpo2.output);

			// Use a FilterLowPass on the summed signal.
			synthesizer.add(filter = new FilterLowPass());
			filter.frequency.set(cutOff);
			mixo.output.connect(filter.input);

			// Multiply each channel of samplePlayer with 0.5 and then sum them together.
			synthesizer.add(mpi1 = new Multiply());
			mpi1.inputA.connect(0,samplePlayer.output,0);
			mpi1.inputB.connect(0,half.output,1);

			synthesizer.add(mpi2 = new Multiply());
			mpi2.inputA.connect(0,samplePlayer.output,1);
			mpi2.inputB.connect(0,half.output,0);

			synthesizer.add(mixi = new Add());
			mixi.inputA.connect(mpi1.output);
			mixi.inputB.connect(mpi2.output);

			// Multiply both summed signals with 0.5 and then sum them together.
			synthesizer.add(mpf1 = new Multiply());
			mixi.output.connect(mpf1.inputA);
			half.output.connect(mpf1.inputB);

			synthesizer.add(mpf2 = new Multiply());
			filter.output.connect(mpf2.inputA);
			half.output.connect(mpf2.inputB);

			synthesizer.add(mixf = new Add());
			mixf.inputA.connect(mpf1.output);
			mixf.inputB.connect(mpf2.output);

			// Connect the output of the Add object to each channel of the LineOut.
			mixf.output.connect(0,lineOut.input,0);
			mixf.output.connect(0,lineOut.input,1);

			// Connect the output of the Add object to each channel of the WaveRecorder.
			mixf.output.connect(recorder.getInput());
			mixf.output.connect(lineOut.input);

			synthesizer.start();

			recorder.start();	// Uncomment for recording, when running the program.

			lineOut.start();	// Uncomment for playing, when running the program.

			// Play the recording. Uncomment!
			do {
				synthesizer.sleepFor(1.0);
            } while (samplePlayer.dataQueue.hasMore());

			recorder.stop();
			recorder.close();
			synthesizer.stop();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		//*/
    }

	public static void main(String[] args) {
		new VocalRemoverJSyn().run();
	}
}
