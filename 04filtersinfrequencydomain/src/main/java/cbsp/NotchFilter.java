package cbsp;

public class NotchFilter extends FiltersBase {

	NotchFilter() {
		super();
		sleep = 0.2;
		
		// TODO Task 2.
		
		// TODO Uncomment output code for Task 2.

		//output = filter.output;
	}

	protected boolean isFinished() {
		
		// TODO Uncomment reader code for Task 2.
		
		//return !reader.dataQueue.hasMore();
		return true;
	}

	public static void main(String[] args) {
		new NotchFilter().run();
	}
}
