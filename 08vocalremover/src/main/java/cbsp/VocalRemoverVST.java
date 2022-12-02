package cbsp;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.FixedRateStereoWriter;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateStereoReader;

import jvst.wrapper.VSTPluginAdapter;

public class VocalRemoverVST extends VSTPluginAdapter {
	
	private double cutOff = 185.0;

	private float sampleRate = 44100;
		  
	public VocalRemoverVST(long Wrapper) {
		super(Wrapper);
		
		this.setNumInputs(2);
		this.setNumOutputs(2);
		this.canProcessReplacing(true);
		this.setUniqueID(9999999);
				
		log("Constructor VocalRemoverVST() called.");
	}

	@Override
	public int canDo(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPlugCategory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getProductString() {
		return "VocalRemoverVST";
	}

	public String getEffectName() {
	    return "VocalRemoverVST";
	}
	
	public void setSampleRate(float sampleRate) { 
		this.sampleRate = sampleRate;
	}
	
	@Override
	public String getProgramNameIndexed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVendorString() {
		return "http://jvstwrapper.sourceforge.net/";
	}

	@Override
	public boolean setBypass(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean string2Parameter(int arg0, String arg1) {
		try {
			if (arg1 != null) this.setParameter(arg0, Float.parseFloat(arg1));
				return true;
		}
		catch(Exception e) {   //ignore
			return false;
		}
	}

	@Override
	public int getNumParams() {
		return 0;
	}

	@Override
	public int getNumPrograms() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getParameter(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getParameterDisplay(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameterLabel(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameterName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProgram() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getProgramName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processReplacing(float[][] arg0, float[][] arg1, int arg2) {

		// arg0 present the 2D array of input floats.
		// arg1 present the 2D array of output floats.

		Synthesizer synthesizer;

		FloatSample sample;
		FloatSample originalSample;
		FloatSample sampleOut;

		VariableRateDataReader samplePlayer;
		VariableRateDataReader samplePlayerOriginal;

		FixedRateStereoWriter writer;

		Multiply mpo1;
		Multiply mpo2;
		Multiply mpi1;
		Multiply mpi2;
		Multiply mpf1;
		Multiply mpf2;

		Add mixo;
		Add mixi;
		Add mixf;

		FilterLowPass filter;

		float[] dsamples = new float[arg0.length * arg0[0].length];
		float[] dsamplesOriginal = new float[arg0.length * arg0[0].length];

		// TASK 1

		// Copy arg0 to dsamplesOriginal

		// Copy arg0 to dsamples, where you invert the values on one of the channels.

		/*synthesizer = JSyn.createSynthesizer();

		// Create originalSample object with new FloatSample from dsamplesOriginal.

		// Create sampleOut object with new FloatSample from dsamplesOriginal.

		// Create sample object with new FloatSample from dsamples.

		// Create samplePlayer object with new VariableRateStereoReader and fill its dataQueue with sample.

		// Create samplePlayerOriginal object with new VariableRateStereoReader and fill its dataQueue with originalSample.

		// Multiply each channel of samplePlayerOriginal with 0.5 and then sum them together.

		// Use a FilterLowPass on the summed signal.

		// Multiply each channel of samplePlayer with 0.5 and then sum them together.

		// Multiply both summed signals with 0.5 and then sum them together.

		// Create and add new FixedRateStereoWriter for storing the final data.

		// Connect the output of the Add object to each channel of the FixedRateStereoWriter.

		synthesizer.start();

		writer.start();

		// Set the dataQueue to sampleOut for a writer to write in.

		// Play the recording.
		do {
			try {
				synthesizer.sleepFor(1.0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } while (samplePlayer.dataQueue.hasMore());

		writer.stop();

		// Read the data from the sampleOut and store it into the arg1 output float array.

		synthesizer.stop();*/
	}

	@Override
	public void setParameter(int arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgram(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgramName(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
