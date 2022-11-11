package cbsp;

import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.FilterPeakingEQ;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.SampleLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Equalizer extends FiltersBase {
	// TODO Define audio components here
	private VariableRateMonoReader player;

	Equalizer() {
		super();
		sleep = 0.2;
		
		// TODO Task 3.
		/*
		Task 3
    	Make a simple equalizer with the cascade of at least 8 FilterPeakingEQ filters.
		 */
		
		// TODO Create our filters here, don't forget to set the output port for Task 3.
		try {
			double a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;
			Scanner sc = new Scanner(System.in);
			PassThrough pt = new PassThrough();

			System.out.println("Insert 8 Amplitude Values (0-1) for the following ranges...\n34-66Hz: ");
			a1 = sc.nextDouble();
			System.out.println("66-133Hz: ");
			a2 = sc.nextDouble();
			System.out.println("133-266Hz: ");
			a3 = sc.nextDouble();
			System.out.println("266-533Hz: ");
			a4 = sc.nextDouble();
			System.out.println("533-1066Hz: ");
			a5 = sc.nextDouble();
			System.out.println("1066-2133Hz: ");
			a6 = sc.nextDouble();
			System.out.println("2133-4266Hz: ");
			a7 = sc.nextDouble();
			System.out.println("4266-8533Hz: ");
			a8 = sc.nextDouble();
			System.out.println("8533-17066Hz: ");
			a9 = sc.nextDouble();
			System.out.println("17066-34132Hz: ");
			a10 = sc.nextDouble();

			double sum;
			sum = 10;

			File wave = new File("src/main/media/siney.wav");
			FloatSample samples = SampleLoader.loadFloatSample(wave);
			FilterPeakingEQ filter1, filter2, filter3, filter4, filter5, filter6, filter7, filter8, filter9, filter10;
			ArrayList<FilterPeakingEQ> filter_list = new ArrayList<FilterPeakingEQ>();
			filter_list.add(filter1= new FilterPeakingEQ());
			filter_list.add(filter2= new FilterPeakingEQ());
			filter_list.add(filter3= new FilterPeakingEQ());
			filter_list.add(filter4= new FilterPeakingEQ());
			filter_list.add(filter5= new FilterPeakingEQ());
			filter_list.add(filter6= new FilterPeakingEQ());
			filter_list.add(filter7= new FilterPeakingEQ());
			filter_list.add(filter8= new FilterPeakingEQ());
			filter_list.add(filter9= new FilterPeakingEQ());
			filter_list.add(filter10= new FilterPeakingEQ());

			for (FilterPeakingEQ filterPeakingEQ : filter_list) {
				synth.add(filterPeakingEQ);
			}

			synth.add(pt = new PassThrough());
			synth.add(player = new VariableRateMonoReader());
			player.dataQueue.queue(samples);
			player.rate.set( samples.getFrameRate() );

			int freq = 50;
			double interval = 16.5+16.5;
			double q;
			q = freq/interval;

			for (FilterPeakingEQ filterPeakingEQ : filter_list) {
				filterPeakingEQ.frequency.set(freq);
				filterPeakingEQ.Q.set(q);
				freq *= 2;
				interval *= 2;
			}

			filter1.amplitude.set(a1/sum);
			filter2.amplitude.set(a2/sum);
			filter3.amplitude.set(a3/sum);
			filter4.amplitude.set(a4/sum);
			filter5.amplitude.set(a5/sum);
			filter6.amplitude.set(a6/sum);
			filter7.amplitude.set(a7/sum);
			filter8.amplitude.set(a8/sum);
			filter9.amplitude.set(a9/sum);
			filter10.amplitude.set(a10/sum);

			for (FilterPeakingEQ filterPeakingEQ : filter_list) {
				player.output.connect(filterPeakingEQ.input);
				pt.input.connect((filterPeakingEQ.output));
			}

			output = pt.output;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean isFinished() {
		// TODO Uncomment reader code for Task 3.
		return !player.dataQueue.hasMore();
	}

	public static void main(String[] args) {
		new Equalizer().run();
	}
}
