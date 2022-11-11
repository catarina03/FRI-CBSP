package cbsp;

import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.FilterBandStop;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.SampleLoader;

import java.io.File;

public class NotchFilter extends FiltersBase {
	private VariableRateMonoReader player;
	private FilterBandStop filter_bs;

	NotchFilter() {
		super();
		/*
		Task 2
		In file media/siney.wav you have a song which has a narrow-band interference. Find out which is frequency of the interference in the song and use a notch filter to remove the interference.
		You can check the frequency spectrum of media/siney.wav by running FrequencySpectrum class.
		*/


		try {
			// Load our media file with added pop sounds
			File wave = new File("src/main/media/siney.wav");
			FloatSample samples = SampleLoader.loadFloatSample(wave);

			// Convert samples to float array
			float[] dsamples = new float[samples.getNumFrames()];
			for (int i = 0; i < dsamples.length; i++)
				dsamples[i] = (float) samples.readDouble(i);

			// Perform processing
			synth.add(filter_bs = new FilterBandStop());
			filter_bs.frequency.set(440);
			filter_bs.Q.set(0.6);

			// Play the (possibly) fixed samples
			player = new VariableRateMonoReader();
			synth.add(player);
			player.dataQueue.queue(samples);
			player.rate.set(samples.getFrameRate());

			player.output.connect(filter_bs.input);
			output = filter_bs.output;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean isFinished() {
		return !player.dataQueue.hasMore();
	}

	public static void main(String[] args) {
		new NotchFilter().run();
	}
}
