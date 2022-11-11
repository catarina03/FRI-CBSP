package cbsp;

import java.util.Arrays;

public class GeneralClickFilter extends ClickFilterBase {
	public static void main(String args[]) {
		new GeneralClickFilter().run();
	}

	/**
	 * Performs sample processing.
	 * 
	 * @param samples
	 *            A mutable array of float samples
	 */
	@Override
	protected void process(float[] samples) {
		// TODO General click filter.
		int large_window = 1000;
		int small_window = 20;
		float threshold = 0.226f;

		//ignore the first 1000-(small window+1)
		for (int i = 0; i < (samples.length-large_window); i++) {
			int large_sample_subset_end = i+large_window-1;
			int small_sample_subset_beg = i+large_window-small_window;

			float asv_large = average_of_squared_values(Arrays.copyOfRange(samples, i, large_sample_subset_end));
			float asv_small = average_of_squared_values(Arrays.copyOfRange(samples, small_sample_subset_beg, large_sample_subset_end));

			if (asv_small > asv_large + threshold) {
				// linear interpolation
				float base = samples[i+(large_window-small_window)];
				float interval = (samples[i+large_window-1] - samples[i+(large_window-small_window)])/small_window;

				for (int j = 0; j < small_window; j++){
					samples[i+(large_window-small_window)+j] = base+j*interval;
				}
			}
		}
	}

	protected float average_of_squared_values(float[] sample_window) {
		float sum = 0.0f;

		for(int i = 0; i < sample_window.length; i++) {
			sum += Math.pow(sample_window[i], 2);
		}

		return sum/sample_window.length;
	}
}
