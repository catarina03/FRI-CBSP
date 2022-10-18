package cbsp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundGeneratorA {

	public static void main(String[] args) {

        int seconds = 1;															// Duration of our signal.
        int sampleRate = 8000;														// Number of samples recorded in 1 second.

        try {
            AudioFormat af = new AudioFormat((float) sampleRate, 8,
                    1, true, true);		                // Define the format: AudioFormat(sample rate, sample size in bits, channels, signed, endian)
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);		// Define the audio-related functionality.
            SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);		// Define a data line to which data may be written.
            source.open(af);
            source.start();

            byte[] buf = new byte[sampleRate * seconds]; 							// sampleRate * seconds = signal length

            // TODO Task 1.
            // A
            double frequency = 1884;
            double amplitude = 75;
            // Sample rate for second missing
            // Duration missing
            for (int i = 0; i < buf.length; i++){
                double time = (double) i / sampleRate;
                double sine = amplitude * Math.sin(2*Math.PI * frequency * time);
                buf[i] = (byte) sine;
            }

            source.write(buf, 0, buf.length);									// Write buffer to the SourceDataLine.
            source.drain();															// Play the samples in the buffer.
            source.stop();
            source.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
