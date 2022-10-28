package cbsp;

import com.jsyn.data.FloatSample;
import com.jsyn.util.SampleLoader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jtransforms.fft.FloatFFT_1D;
import pl.edu.icm.jlargearrays.ConcurrencyUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FrequencySpectrum {

    private final static float THRESHOLD = 0f;

    public static void main(String[] args) {

        File inputFile = new File("src/main/media/siney.wav");
        FloatSample samples;
        try {
            samples = SampleLoader.loadFloatSample(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        double sampleFrequency = samples.getFrameRate();
        float[] sampleData = new float[samples.getChannelsPerFrame() * samples.getNumFrames()];
        samples.read(sampleData);

        if (samples.getChannelsPerFrame() == 1) {
            final float[] fftSamples = hanningFunction(sampleData);
            drawSpectrum(fftSamples, sampleFrequency, 0);
        }
        else {
            for (int j = 0; j < samples.getChannelsPerFrame(); j++) {

                float[] channelData = new float[sampleData.length/2];
                for (int i = 0; i < channelData.length - 1; i++) {
                    channelData[i] = sampleData[2 * (i + j)];
                }
                final float[] fftSamples = hanningFunction(channelData);
                drawSpectrum(fftSamples, sampleFrequency, j);
            }
        }
        ConcurrencyUtils.shutdownThreadPoolAndAwaitTermination();
    }

    private static float[] hanningFunction(float[] in){
        float[] out = new float[in.length];
        for(int i = 0; i < in.length; i++){
            out[i] = (float) (in[i] * 0.5 * (1 - Math.cos(2 * Math.PI * i / in.length)));
        }
        return out;
    }

    public static void drawSpectrum(float[] data, double sampleFrequency, int channel) {
        FloatFFT_1D fftDo = new FloatFFT_1D(data.length);
        float[] fft = new float[data.length * 2];
        System.arraycopy(data, 0, fft, 0, data.length);
        fftDo.realForward(fft);

        XYSeries series = new XYSeries("Power Spectrum");

        for (int i = 0; i < data.length / 2; i++) {
            float re = fft[2 * i];
            float im = fft[(2 * i)+1];
            final double logPower = Math.log(Math.sqrt((re * re) + (im * im))); // as in audacity, we want a log axis for the power

            if(logPower > THRESHOLD) { // audacity cuts of powers below a threshold
                series.add((i * sampleFrequency) / data.length, THRESHOLD);
                series.add((i * sampleFrequency) / data.length, logPower);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Frequency Power Spectrum", "Frequency [Hz]",
                "log Power", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );

        XYPlot xyPlot = (XYPlot) chart.getPlot();
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(0.00, 22000.00);
//         domain.setRange(400.00, 450.00);

        chart.getPlot().setBackgroundPaint(Color.WHITE);

        try {
            ChartUtilities.saveChartAsJPEG(new File("FrequencyPowerSpectrum" + channel + ".png"), chart, 1920, 1080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}