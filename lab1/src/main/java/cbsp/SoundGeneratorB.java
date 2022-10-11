package cbsp;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class SoundGeneratorB {
	
	// Graph components.
	private Synthesizer synth;
	private LineOut lineOut;

	public static void main(String args[]) {
		new SoundGeneratorB().start();
	}

	private void start() {

		synth = JSyn.createSynthesizer();					// Initialization of JSyn API

		synth.add(lineOut = new LineOut());					// Create line out and add it to the graph.
		synth.start();

		// TODO Task 2.

		lineOut.start();									// Start the data flow in graph.

		// Play signal for 2 seconds.
		try {
			synth.sleepFor(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		synth.stop();										// Turn off JSyn engine
	}
}