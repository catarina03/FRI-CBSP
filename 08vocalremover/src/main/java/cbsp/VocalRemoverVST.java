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

import java.util.Arrays;

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

		synthesizer = JSyn.createSynthesizer();

		writer = new FixedRateStereoWriter();

		// TASK 1

		// Copy arg0 to dsamplesOriginal
		for(int i=0;i<arg0.length;i++){
			for(int j=0;i<arg0[0].length;i++){
				dsamples[i*2+j] = arg0[i][j];
			}
		}

		// Copy arg0 to dsamples, where you invert the values on one of the channels.
		dsamplesOriginal = dsamples;
		for(int i = 0;i<dsamples.length;i++){
			if(i%2==1){
				dsamples[i]=-1*dsamples[i];
			}
		}


		// Create originalSample object with new FloatSample from dsamplesOriginal.
		originalSample = new FloatSample(dsamplesOriginal,2);

		// Create sampleOut object with new FloatSample from dsamplesOriginal.
		sampleOut = new FloatSample(dsamplesOriginal,2);

		// Create sample object with new FloatSample from dsamples.
		sample = new FloatSample(dsamples,2);

		// Create samplePlayer object with new VariableRateStereoReader and fill its dataQueue with sample.
		synthesizer.add(samplePlayer = new VariableRateStereoReader());
		samplePlayer.dataQueue.queue(sample);
		samplePlayer.rate.set(sampleRate);

		// Create samplePlayerOriginal object with new VariableRateStereoReader and fill its dataQueue with originalSample.
		synthesizer.add(samplePlayerOriginal = new VariableRateStereoReader());
		samplePlayerOriginal.dataQueue.queue(originalSample);
		samplePlayerOriginal.rate.set(sampleRate);

		// Multiply each channel of samplePlayerOriginal with 0.5 and then sum them together.
		Arrays.fill(dsamples, 0.5F);
		FloatSample halfs = new FloatSample(dsamples);
		halfs.setChannelsPerFrame(2);
		VariableRateStereoReader half = new VariableRateStereoReader();
		half.dataQueue.queue(halfs);
		synthesizer.add(half);

		synthesizer.add(mpo1 = new Multiply());
		mpo1.inputA.connect(0,samplePlayerOriginal.output,0);
		mpo1.inputB.connect(0,half.output,1);

		synthesizer.add(mpo2 = new Multiply());
		mpo2.inputA.connect(0,samplePlayerOriginal.output,1);
		mpo2.inputB.connect(0,half.output,0);

		synthesizer.add(mixo = new Add());
		mixo.inputA.connect(mpo1.output);
		mixo.inputB.connect(mpo2.output);

		// Use a FilterLowPass on the summed signal.
		synthesizer.add(filter = new FilterLowPass());
		filter.frequency.set(cutOff);
		mixo.output.connect(filter.input);

		// Multiply each channel of samplePlayer with 0.5 and then sum them together.
		synthesizer.add(mpi1 = new Multiply());
		mpi1.inputA.connect(0,samplePlayer.output,0);
		mpi1.inputB.connect(0,half.output,1);

		synthesizer.add(mpi2 = new Multiply());
		mpi2.inputA.connect(0,samplePlayer.output,1);
		mpi2.inputB.connect(0,half.output,0);

		synthesizer.add(mixi = new Add());
		mixi.inputA.connect(mpi1.output);
		mixi.inputB.connect(mpi2.output);

		// Multiply both summed signals with 0.5 and then sum them together.
		synthesizer.add(mpf1 = new Multiply());
		mixi.output.connect(mpf1.inputA);
		half.output.connect(mpf1.inputB);

		synthesizer.add(mpf2 = new Multiply());
		filter.output.connect(mpf2.inputA);
		half.output.connect(mpf2.inputB);

		synthesizer.add(mixf = new Add());
		mixf.inputA.connect(mpf1.output);
		mixf.inputB.connect(mpf2.output);

		// Create and add new FixedRateStereoWriter for storing the final data.
		synthesizer.add(writer = new FixedRateStereoWriter());
		mixf.output.connect(writer.input);

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
		float[] tmp = new float[sampleOut.getNumFrames()*2];
		sampleOut.read(tmp);

		for(int i = 0; i<sampleOut.getNumFrames(); i++){
			arg1[i][0]=tmp[i*2];
			arg1[i][1]=tmp[i*2+1];
		}

		synthesizer.stop();
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
