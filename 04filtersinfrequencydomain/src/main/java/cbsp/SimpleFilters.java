package cbsp;

import com.jsyn.unitgen.*;

public class SimpleFilters extends FiltersBase {

	SimpleFilters() {
		super();

		double frequency1 = 967;
		double amplitude1 = 0.76;
		double frequency2 = 3845;
		double amplitude2 = 0.62;
		double frequency3 = 5034;
		double amplitude3 = 0.47;
		double duration = 9;

		sleep = duration;

		synth.start();

		// Sine signal 1
		SineOscillator sineOsc1 = new SineOscillator(frequency1, amplitude1/3);
		synth.add(sineOsc1);

		// Sine signal 2
		SineOscillator sineOsc2 = new SineOscillator(frequency2,amplitude2/3);
		synth.add(sineOsc2);

		// Sine signal 2
		SineOscillator sineOsc3 = new SineOscillator(frequency3,amplitude3/3);
		synth.add(sineOsc3);

		PassThrough ps = new PassThrough();
		synth.add(ps);
		ps.input.connect(sineOsc1.output);
		ps.input.connect(sineOsc2.output);
		ps.input.connect(sineOsc3.output);

		output = ps.output;

		taskA(ps);
		taskB(ps);
		taskC(ps);
		taskD(ps);
	}

	protected void taskA(PassThrough ps) {
		FilterLowPass filter_lp = new FilterLowPass();
		synth.add(filter_lp);
		filter_lp.frequency.set(1000);
		filter_lp.Q.set(0.1);
		filter_lp.input.connect(ps.output);

		output = filter_lp.output;
	}

	protected void taskB(PassThrough ps) {
		FilterHighPass filter_hp = new FilterHighPass();
		synth.add(filter_hp);
		filter_hp.frequency.set(5000);
		filter_hp.Q.set(0.1);
		filter_hp.input.connect(ps.output);

		output = filter_hp.output;
	}

	protected void taskC(PassThrough ps) {
		FilterBandStop filter_bs = new FilterBandStop();
		synth.add(filter_bs);
		filter_bs.frequency.set(3845);
		filter_bs.Q.set(0.1);
		filter_bs.input.connect(ps.output);

		output = filter_bs.output;
	}

	protected void taskD(PassThrough ps) {
		FilterBandPass filter_bp = new FilterBandPass();
		synth.add(filter_bp);
		filter_bp.frequency.set(3845);
		filter_bp.Q.set(0.1);
		filter_bp.input.connect(ps.output);

		output = filter_bp.output;
	}


	/*
	Task 1
    Generate three sine signals using JSyn API with the following parameters and sum them together:
    - Frequency 1: 967
    - Amplitude 1: 0.76
    - Frequency 2: 3845
    - Amplitude 2: 0.62
    - Frequency 3: 5034
    - Amplitude 3: 0.47
    - Duration: 9
    A: Filter the top two frequencies out of the signal with the low-pass filter.
    B: Filter the lowest two frequencies out of the signal with the high-pass filter.
    C: Filter the only the middle frequency out of the signal with the band-stop filter.
    D: Filter the lowest and the top frequencies out of the signal with the band-pass filter.
	 */

	public static void main(String[] args) {
		new SimpleFilters().run();
	}
}
