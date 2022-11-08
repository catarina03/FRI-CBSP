package cbsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;

public class SimpleDistortor {
	PluckedString ps0;
	Distortor dis0;

	protected TimeStamp playRiff(PluckedString ps, Distortor distortor, double[] notes, double beat, TimeStamp timeStamp) {
		double amp = 1.0;

		ps.pluck(timeStamp, notes[0], 2.0, 1.0, amp, 1);
		distortor.distort(timeStamp, amp, 11.0, notes[0], 0.05);
		timeStamp = timeStamp.makeRelative(0.03);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < notes.length; j++) {
				ps.modify(timeStamp, notes[j], 2.0, 1.0, amp);
				distortor.distort(timeStamp, amp, 11.0, notes[j], 0.05);
				timeStamp = timeStamp.makeRelative(beat);
			}
		}

		ps.modify(timeStamp, notes[0], 0.01, 0.01, amp);
		distortor.distort(timeStamp, amp, 11.0, notes[0], 0.0);
		return timeStamp;
	}

	public void run() {
		// Initialize JSyn framework
		Synthesizer synth = JSyn.createSynthesizer();
		synth.getAudioDeviceManager().setSuggestedOutputLatency(0.5);

		// Setup output file recording
		File outputWave = new File("output.wav");
		WaveRecorder recorder = null;
		try {
			recorder = new WaveRecorder(synth, outputWave);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		// Karplus-Strong string instrument model
		synth.add(ps0 = new PluckedString());

		// Distortor
		synth.add(dis0 = new Distortor());
		dis0.feedback.connect(ps0.input);
		ps0.output.connect(dis0.input);

		dis0.output.connect(0, recorder.getInput(), 0);
		recorder.start();

		// Start synthesis
		synth.start();

		// Get synthesizer time in seconds
		double timeNow = synth.getCurrentTime();

		// Play some riffs
		double[] freqs = { 158.83, 249.811, 205.74 };
		TimeStamp timeStamp = new TimeStamp(timeNow + 0.5);
		timeStamp = playRiff(ps0, dis0, freqs, 0.09, timeStamp);
		timeStamp = timeStamp.makeRelative(5.0);

		try {
			synth.sleepUntil(timeStamp.getTime());
		} catch (InterruptedException e) {
		}

		// Stop recording and shutdown
		recorder.stop();
		try {
			recorder.close();
		} catch (IOException e) {
		}
		synth.stop();
	}

	public static void main(String[] args) {
		new SimpleDistortor().run();
	}
}
