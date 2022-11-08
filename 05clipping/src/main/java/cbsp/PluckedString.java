package cbsp;

import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterHighShelf;
import com.jsyn.unitgen.FixedRateMonoReader;
import com.jsyn.unitgen.InterpolatingDelay;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitSource;
import com.softsynth.shared.time.TimeStamp;

public class PluckedString extends Circuit implements UnitSource {
	// Ports
	public final UnitInputPort input;
	public final UnitOutputPort output;

	// Parameters for plucked string circuit and initial random sample
	protected PluckedStringParams m_params;
	protected FloatSample m_burst;

	// Circuit components
	protected FixedRateMonoReader sampleIn;
	protected PassThrough masterIn;
	protected PassThrough externalIn;
	protected InterpolatingDelay masterDelay;
	protected InterpolatingDelay delayD0;
	protected InterpolatingDelay delayD1;
	protected InterpolatingDelay delayD2;
	protected InterpolatingDelay delayD3;
	protected Multiply mixD0;
	protected Multiply mixD1;
	protected Multiply mixD2;
	protected Multiply mixD3;
	protected FilterHighShelf filterDC;

	PluckedString() {
		m_params = new PluckedStringParams();
		// Unit that will output an initial burst of sound generated
		// by the plucked-string parameters.
		add(sampleIn = new FixedRateMonoReader());

		// Master input/mixer
		add(masterIn = new PassThrough());
		sampleIn.output.connect(masterIn.input);

		// Additional (external) input can be directed to the master input
		add(externalIn = new PassThrough());
		input = externalIn.input;
		externalIn.output.connect(masterIn.input);

		// Master delay for the plucked-string generator
		add(masterDelay = new InterpolatingDelay());
		masterDelay.allocate(2205);
		masterDelay.delay.set(0.001);
		masterIn.output.connect(masterDelay.input);
		output = masterDelay.output;

		// Pluck filter
		add(delayD0 = new InterpolatingDelay());
		add(delayD1 = new InterpolatingDelay());
		add(delayD2 = new InterpolatingDelay());
		add(delayD3 = new InterpolatingDelay());
		delayD0.allocate(1);
		delayD1.allocate(2);
		delayD2.allocate(3);
		delayD3.allocate(4);
		delayD0.delay.set(0.001);
		delayD1.delay.set(0.001);
		delayD2.delay.set(0.001);
		delayD3.delay.set(0.001);

		// Master delay distributes samples to all delay units
		masterDelay.output.connect(delayD0.input);
		masterDelay.output.connect(delayD1.input);
		masterDelay.output.connect(delayD2.input);
		masterDelay.output.connect(delayD3.input);

		// Perform mixing of delay unit outputs
		add(mixD0 = new Multiply());
		add(mixD1 = new Multiply());
		add(mixD2 = new Multiply());
		add(mixD3 = new Multiply());
		delayD0.output.connect(mixD0.inputA);
		delayD1.output.connect(mixD1.inputA);
		delayD2.output.connect(mixD2.inputA);
		delayD3.output.connect(mixD3.inputA);

		// FilterHighShelf is a special variant of Low Pass filter that still keeps suppressed higher frequencies. 
		add(filterDC = new FilterHighShelf());
		filterDC.frequency.set(20.0);
		filterDC.gain.set(1.0);
		filterDC.slope.set(1.0);
		mixD0.output.connect(filterDC.input);
		mixD1.output.connect(filterDC.input);
		mixD2.output.connect(filterDC.input);
		mixD3.output.connect(filterDC.input);
		filterDC.output.connect(masterIn.input);
	}

	@Override
	public UnitOutputPort getOutput() {
		return output;
	}

	public void pluck(TimeStamp start, double freq, double tf0, double tfNy, double amp, int squish) {
		// Compute plucked string parameters for given arguments
		m_params.sset(freq, tf0, tfNy);
		m_params.randfill(amp, squish);

		// Delay line length
		int delayLength = (int) m_params.delsamps;
		masterDelay.delay.set((m_params.delsamps - 17.0) / getFrameRate(), start);

		// Convert initial burst to floats
		float[] burst = new float[delayLength];
		for (int i = 0; i < delayLength; i++) {
			burst[i] = (float) m_params.ninit[i];
		}
		m_burst = new FloatSample(burst);

		// Setup delay items
		delayD0.delay.set(1.0 / getFrameRate());
		delayD1.delay.set(2.0 / getFrameRate());
		delayD2.delay.set(3.0 / getFrameRate());
		delayD3.delay.set(4.0 / getFrameRate());

		// Setup loop multiplication factors
		mixD0.inputB.set(m_params.a0, start);
		mixD1.inputB.set(m_params.a1, start);
		mixD2.inputB.set(m_params.a2, start);
		mixD3.inputB.set(m_params.a3, start);
		filterDC.amplitude.set(m_params.dca0, start);

		// Queue in generated initial burst
		sampleIn.dataQueue.queue(m_burst, 0, delayLength, start);
	}

	public void modify(TimeStamp start, double freq, double tf0, double tfNy, double amp) {
		// Compute plucked string parameters for given arguments
		m_params.sset(freq, tf0, tfNy);
		masterDelay.delay.set((m_params.delsamps - 17.0) / getFrameRate(), start);

		// Setup loop multiplication factors
		mixD0.inputB.set(m_params.a0, start);
		mixD1.inputB.set(m_params.a1, start);
		mixD2.inputB.set(m_params.a2, start);
		mixD3.inputB.set(m_params.a3, start);
		filterDC.amplitude.set(m_params.dca0, start);
	}
}
