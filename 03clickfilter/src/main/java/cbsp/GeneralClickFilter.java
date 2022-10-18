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

		//ignore the first 1000-(small window+1)
		for (int i = 0; i < (samples.length-large_window+1); i++) { //To fix
			float asv_large = average_of_squared_values(Arrays.copyOfRange(samples, i, large_window-1));
			float asv_small = average_of_squared_values(Arrays.copyOfRange(samples, i+(large_window-small_window+1), large_window-1));
		}



		float threshold = 0.226f; //use sigma from the std deviation ?

		for (int i = 1; i<samples.length;i++){
			if (Math.abs((samples[i-1]) - samples[i]) > threshold) {
				samples[i] = 0.0f; //linear interpolation instead
			}
		}
	}

	protected float average_of_squared_values(float[] sample_window) {
		float sum = 0.0f;

		for(int i = 0; i < sample_window.length; i++) {
			sum += Math.pow(sample_window[i], 2);
		}
		float result = sum/sample_window.length;

		return result;
	}
}
