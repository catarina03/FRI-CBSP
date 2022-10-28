package cbsp;

public class Equalizer extends FiltersBase {
	// TODO Define audio components here

	Equalizer() {
		super();
		sleep = 0.2;
		
		// TODO Task 3.
		
		// TODO Create our filters here, don't forget to set the output port for Task 3.
	}

	protected boolean isFinished() {
		// TODO Uncomment reader code for Task 3.
		
		// return !reader.dataQueue.hasMore();
		return true;
	}

	public static void main(String[] args) {
		new Equalizer().run();
	}
}
