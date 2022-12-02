package cbsp;

import java.io.File;
import java.io.IOException;

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

		/*try {

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

			// Create new WaveRecorder.

			// Create and add new LineOut to the synthesizer.

			// Create originalSample object with new FloatSample from dsamplesOriginal.

			// Create sample object with new FloatSample from dsamples.

			// Create samplePlayer object with new VariableRateStereoReader and fill its dataQueue with sample.

			// Create samplePlayerOriginal object with new VariableRateStereoReader and fill its dataQueue with originalSample.

			// Multiply each channel of samplePlayerOriginal with 0.5 and then sum them together.

			// Use a FilterLowPass on the summed signal.

			// Multiply each channel of samplePlayer with 0.5 and then sum them together.

			// Multiply both summed signals with 0.5 and then sum them together.

			// Connect the output of the Add object to each channel of the LineOut.

			// Connect the output of the Add object to each channel of the WaveRecorder.

			synthesizer.start();

//			recorder.start();	// Uncomment for recording, when running the program.

//			lineOut.start();	// Uncomment for playing, when running the program.

			// Play the recording. Uncomment!
			do {
				synthesizer.sleepFor(1.0);
            } while (samplePlayer.dataQueue.hasMore());

			recorder.stop();
			recorder.close();
			synthesizer.stop();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    }

	public static void main(String[] args) {
		new VocalRemoverJSyn().run();
	}
}
