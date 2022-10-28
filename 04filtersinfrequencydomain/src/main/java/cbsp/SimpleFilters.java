package cbsp;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;

public class SimpleFilters extends FiltersBase {

	SimpleFilters() {
		super();
		
		// TODO Task 1.
		
		// TODO Uncomment output code for Task 1.

		double frequency1 = 792;
		double amplitude1 = 0.42;
		double frequency2 = 3202;
		double amplitude2 = 0.55;
		double frequency3 = 7866;
		double amplitude3 = 0.75;
		double duration = 7;

		sleep = duration;

		synth.start();

		// Sine signal 1
		SineOscillator sineOsc1 = new SineOscillator(frequency1, amplitude1);
		synth.add(sineOsc1);

		// Sine signal 2
		SineOscillator sineOsc2 = new SineOscillator(frequency2,amplitude2);
		synth.add(sineOsc2);

		// Sine signal 2
		SineOscillator sineOsc3 = new SineOscillator(frequency3,amplitude3);
		synth.add(sineOsc3);

		PassThrough ps = new PassThrough();
		synth.add(ps);
		ps.input.connect(sineOsc1.output);
		ps.input.connect(sineOsc2.output);
		ps.input.connect(sineOsc3.output);

		FilterLowPass filter_lp = new FilterLowPass();
		synth.add(filter_lp);
		filter_lp.frequency.set(800);
		filter_lp.Q.set(0.1);
		filter_lp.input.connect(ps.output);

		output = filter_lp.output;
		
	}

	public static void main(String[] args) {
		new SimpleFilters().run();
	}
}
