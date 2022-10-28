package cbsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.util.WaveRecorder;

public abstract class FiltersBase {
	// JSyn components
	protected Synthesizer synth;
	protected LineOut lineOut;
	protected UnitOutputPort output = null;
	protected double sleep = 2.0;

	public FiltersBase() {
		// Initialize JSyn framework
		synth = JSyn.createSynthesizer();
		synth.add(lineOut = new LineOut());
		synth.start();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void run() {
		// Save fixed samples into some output wave file
		File outputWave = new File("output.wav");
		WaveRecorder recorder = null;
		try {
			recorder = new WaveRecorder(synth, outputWave);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			synth.stop();
			return;
		}

		// TODO Uncomment lineOut code for Task1
		
      boolean play = true;
		if (play)
		output.connect(lineOut.input);

		output.connect(recorder.getInput());
		recorder.start();

		if (play)
		synth.startUnit(lineOut);
		
		do {
			try {
				synth.sleepFor(sleep);
			} catch (InterruptedException e) {
			}
		} while (!isFinished());

		recorder.stop();
		try {
			recorder.close();
		} catch (IOException e) {
		}
		synth.stop();
	}
}
