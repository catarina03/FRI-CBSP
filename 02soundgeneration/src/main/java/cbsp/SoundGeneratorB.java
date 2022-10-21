package cbsp;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.*;

import java.util.Arrays;
import java.util.List;

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

		taskA();
		taskB();
		taskC();

		synth.stop();										// Turn off JSyn engine
	}

	private void taskA(){
		double frequency = 2570;
		double amplitude = 0.95;
		double duration = 3;

		SineOscillator sineOsc = new SineOscillator();
		synth.add(sineOsc);

		sineOsc.output.connect( 0, lineOut.input, 0 );   // connect to left channel
		sineOsc.output.connect( 0, lineOut.input, 1 );   // connect to right channel

		sineOsc.frequency.set(frequency);
		sineOsc.amplitude.set(amplitude);

		lineOut.start();									// Start the data flow in graph.

		// Play signal for a certain number of seconds.
		try {
			synth.sleepFor(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		synth.stop();
	}

	private void taskB(){
		double frequency1 = 3231;
		double amplitude1 = 0.81;
		double frequency2 = 2746;
		double amplitude2 = 0.32;
		double duration = 5;

		synth.start();

		// Sine signal 1
		SineOscillator sineOsc1 = new SineOscillator();
		synth.add(sineOsc1);

		sineOsc1.frequency.set(frequency1);
		sineOsc1.amplitude.set(amplitude1);

		// Sine signal 2
		SineOscillator sineOsc2 = new SineOscillator();
		synth.add(sineOsc2);

		sineOsc2.frequency.set(frequency2);
		sineOsc2.amplitude.set(amplitude2);

		sineOsc1.output.connect( 0, lineOut.input, 0 );   // connect to left channel
		sineOsc2.output.connect( 0, lineOut.input, 1 );   // connect to right channel

		lineOut.start();									// Start the data flow in graph.

		// Play signal for a certain number of seconds.
		try {
			synth.sleepFor(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		synth.stop();
	}

	private void taskC(){
		double frequency = 351;
		double amplitude = 0.72;
		int sampleRate = 17683;
		double duration = 5;

		List<Double> amplitudes = Arrays.asList(0.6, 0.76, 0.7, 0.56, 0.81, 0.77, 0.69, 0.94);

		synth.start(sampleRate);

		PassThrough passThrough = new PassThrough();
		synth.add(passThrough);

		// Base Sine signal
		SineOscillator sineOsc = new SineOscillator();
		synth.add(sineOsc);
		sineOsc.frequency.set(frequency);
		sineOsc.amplitude.set(amplitude/(amplitudes.size()+1)); //Dividing by the number of signals to avoid clipping

		sineOsc.output.connect(passThrough.input);

		// Harmonics
		for (int i = 0; i < amplitudes.size(); i++){
			SineOscillator harmonicOsc = new SineOscillator();
			synth.add(harmonicOsc);
			harmonicOsc.frequency.set(frequency);
			harmonicOsc.amplitude.set(amplitudes.get(i)/(amplitudes.size()+1)); //Dividing by the number of signals to avoid clipping
			harmonicOsc.output.connect(passThrough.input);
		}

		passThrough.output.connect( 0, lineOut.input, 0 );   // connect to left channel
		passThrough.output.connect( 0, lineOut.input, 1 );   // connect to right channel

		// Start the data flow in graph.
		lineOut.start();

		// Play signal for a certain number of seconds.
		try {
			synth.sleepFor(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}