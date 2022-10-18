package cbsp;

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
		float threshold = 0.0f; //use sigma from the std deviation ?

		for (int i = 1; i<samples.length;i++){
			if (Math.abs((samples[i-1]) - samples[i]) > threshold) {
				samples[i] = 0.0f;
			}
		}
	}
}
