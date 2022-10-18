package cbsp;

import java.io.File;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

public abstract class ClickFilterBase {
	protected void run() {
		try {
			// Initialize JSyn framework
			Synthesizer synth = JSyn.createSynthesizer();
			LineOut lineOut = new LineOut();
			synth.add(lineOut);
			synth.start();

			// Load our media file with added pop sounds
			File wave = new File("src/main/media/pops.wav");
			FloatSample samples = SampleLoader.loadFloatSample(wave);

			// Convert samples to float array
			float[] dsamples = new float[samples.getNumFrames()];
			for (int i = 0; i < dsamples.length; i++)
				dsamples[i] = (float) samples.readDouble(i);

			// Perform processing
			process(dsamples);

			// Retrieve samples and pack them back again
			double fr = samples.getFrameRate();
			samples = new FloatSample(dsamples);
			samples.setFrameRate(fr);

			// Play the (possibly) fixed samples
			VariableRateMonoReader player = new VariableRateMonoReader();
			synth.add(player);
			player.rate.set(samples.getFrameRate());
			player.dataQueue.queue(samples);

			// Save fixed samples into some output wave file
			File outputWave = new File("src/main/media/output.wav");
			WaveRecorder recorder = new WaveRecorder(synth, outputWave);
			player.output.connect(0, recorder.getInput(), 0);
			recorder.start();

			// Play to line out
			player.output.connect(0, lineOut.input, 0);
            player.output.connect(0, lineOut.input, 1);
			synth.startUnit(lineOut);
			do {
				synth.sleepFor(1.0);
			} while (player.dataQueue.hasMore());

			recorder.stop();
			recorder.close();
			synth.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void process(float[] samples);
}
