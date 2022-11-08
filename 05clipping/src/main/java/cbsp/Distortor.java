package cbsp;

import com.jsyn.data.DoubleTable;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FunctionEvaluator;
import com.jsyn.unitgen.InterpolatingDelay;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitSource;
import com.softsynth.shared.time.TimeStamp;

public class Distortor extends Circuit implements UnitSource {
	// Ports
	public final UnitInputPort input;
	public final UnitOutputPort output;
	public final UnitOutputPort feedback;

	// Circuit components
	protected Multiply masterIn;
	protected DoubleTable waveShape;
	protected FunctionEvaluator waveShaper;
	protected Multiply outputGain;
	protected InterpolatingDelay delay;

	Distortor() {
		// TODO Task 2.

		//input = new UnitInputPort()


		waveShape = new DoubleTable(new double[]{-0.8, -0.8, -0.8, -0.6, -0.2, 0.0, 0.2, 0.6, 0.8, 0.8, 0.8});
		add(waveShaper = new FunctionEvaluator());
		waveShaper.function.set(waveShape);

		masterIn = new Multiply();
		add(masterIn);
		masterIn.inputB = input;
		masterIn.inputA.set(1);
		masterIn.output.connect(waveShaper.input);

		output = waveShaper.output;


		outputGain = new Multiply();
		outputGain.inputA.set(1);
		outputGain.inputB.connect(waveShaper.output);

		delay = new InterpolatingDelay();
		delay.input.connect(outputGain.output);
		delay.allocate(2205);
		delay.delay.set(0.001);

		feedback = delay.output;
	}

	@Override
	public UnitOutputPort getOutput() {
		return output;
	}

	public void distort(TimeStamp start, double amp, double dgain, double fbfreq, double fbgain) {
        // TODO Task 2 - Uncomment
		masterIn.inputA.set(dgain / amp, start);
        waveShaper.amplitude.set(amp, start);
        delay.delay.set(1.0 / fbfreq, start);
        outputGain.inputA.set(fbgain, start);
	}
}
