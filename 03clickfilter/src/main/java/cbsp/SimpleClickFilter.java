package cbsp;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SimpleClickFilter extends ClickFilterBase {
	public static void main(String args[]) {
		new SimpleClickFilter().run();
	}

	/**
	 * Performs sample processing.
	 * 
	 * @param samples
	 *            A mutable array of float samples
	 */
	@Override
	protected void process(float[] samples) {
		// Simple click filter.
		float standard_deviation = standard_deviation(samples);
		for (int i = 1; i<samples.length;i++){
			if (Math.abs((samples[i-1]) - samples[i]) > standard_deviation) { //Standard dev or threshold difined by us?
				samples[i] = 0.0f;
			}
		}
	}

	protected float standard_deviation(float[] array) {
		float mean = mean(array);
		float variance = variance(array, mean);
		double deviation = Math.sqrt(variance);

		return (float) deviation;
	}

	protected float variance(float[] array, float mean) {
		float sum = 0.0f;

		for(int i = 0; i < array.length; i++)
		{
			sum+=Math.pow((array[i]-mean),2);

		}
		float result = sum/(array.length-1);

		return result;
	}

	protected float mean(float[] array) {
		float sum = 0.0f;

		for(int i = 0; i < array.length; i++){
			sum+= array[i];
		}
		float result = sum/array.length;

		return result;
	}
}
