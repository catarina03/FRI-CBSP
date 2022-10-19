package cbsp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundGeneratorA {

    public static void main(String[] args) {
        taskA();
        taskB();
        taskC();
        taskD();
    }

    protected static void taskA() {
        int seconds = 8;                                                            // Duration of our signal.
        int sampleRate = 36037;                                                        // Number of samples recorded in 1 second.

        try {
            AudioFormat af = new AudioFormat((float) sampleRate, 8,
                    1, true, true);                        // Define the format: AudioFormat(sample rate, sample size in bits, channels, signed, endian)
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);        // Define the audio-related functionality.
            SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);        // Define a data line to which data may be written.
            source.open(af);
            source.start();

            byte[] buf = new byte[sampleRate * seconds];                            // sampleRate * seconds = signal length

            double frequency = 1884;
            double amplitude = 75;
            for (int i = 0; i < buf.length; i++) {
                double time = (double) i / sampleRate;
                double sine = amplitude * Math.sin(2 * Math.PI * frequency * time);
                buf[i] = (byte) sine;
            }

            source.write(buf, 0, buf.length);                                    // Write buffer to the SourceDataLine.
            source.drain();                                                            // Play the samples in the buffer.
            source.stop();
            source.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void taskB() {
        int seconds = 7;                                                            // Duration of our signal.
        int sampleRate = 9231;                                                        // Number of samples recorded in 1 second.

        try {
            AudioFormat af = new AudioFormat((float) sampleRate, 8,
                    1, true, true);                        // Define the format: AudioFormat(sample rate, sample size in bits, channels, signed, endian)
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);        // Define the audio-related functionality.
            SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);        // Define a data line to which data may be written.
            source.open(af);
            source.start();

            byte[] buf = new byte[sampleRate * seconds];                            // sampleRate * seconds = signal length

            double frequency = 3309;
            double amplitude = 101;
            for (int i = 0; i < buf.length; i++) {
                double time = (double) i / sampleRate;
                double sine = amplitude * Math.cos(2 * Math.PI * frequency * time);
                buf[i] = (byte) sine;
            }

            source.write(buf, 0, buf.length);                                    // Write buffer to the SourceDataLine.
            source.drain();                                                            // Play the samples in the buffer.
            source.stop();
            source.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void taskC() {
        int seconds = 3;                                                            // Duration of our signal.
        int sampleRate = 36619;                                                        // Number of samples recorded in 1 second.

        try {
            AudioFormat af = new AudioFormat((float) sampleRate, 8,
                    1, true, true);                        // Define the format: AudioFormat(sample rate, sample size in bits, channels, signed, endian)
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);        // Define the audio-related functionality.
            SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);        // Define a data line to which data may be written.
            source.open(af);
            source.start();

            byte[] buf = new byte[sampleRate * seconds];                            // sampleRate * seconds = signal length

            double frequency = 263;
            double amplitude = 62;
            for (int i = 0; i < buf.length; i++) {
                double time = (double) i / sampleRate;
                double sine = amplitude * Math.sin(2 * Math.PI * frequency * time);
                double harmonic1 = 0.74 * sine;
                double harmonic2 = 0.71 * sine;
                double harmonic3 = 0.83 * sine;
                double harmonic4 = 0.44 * sine;
                double harmonic5 = 0.35 * sine;
                double harmonic6 = 0.63 * sine;
                double harmonic7 = 0.45 * sine;
                double harmonic8 = 0.32 * sine;

                float numberOfSignals = 9;
                buf[i] = (byte) ((sine+harmonic1+harmonic2+harmonic3+harmonic4+harmonic5+harmonic6+harmonic7+harmonic8)/numberOfSignals);
            }

            source.write(buf, 0, buf.length);                                    // Write buffer to the SourceDataLine.
            source.drain();                                                            // Play the samples in the buffer.
            source.stop();
            source.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void taskD() {
        int seconds = 8;                                                            // Duration of our signal.
        int sampleRate = 36619;                                                        // Number of samples recorded in 1 second.

        try {
            AudioFormat af = new AudioFormat((float) sampleRate, 8,
                    1, true, true);                        // Define the format: AudioFormat(sample rate, sample size in bits, channels, signed, endian)
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);        // Define the audio-related functionality.
            SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);        // Define a data line to which data may be written.
            source.open(af);
            source.start();

            byte[] buf = new byte[sampleRate * seconds];                            // sampleRate * seconds = signal length

            double frequency = 369;
            double amplitude = 83;
            for (int i = 0; i < buf.length; i++) {
                double time = (double) i / sampleRate;
                double sine = amplitude * Math.cos(2 * Math.PI * frequency * time);
                double harmonic1 = 0.59 * sine;
                double harmonic2 = 0.97 * sine;
                double harmonic3 = 0.94 * sine;
                double harmonic4 = 0.77 * sine;
                double harmonic5 = 0.49 * sine;
                double harmonic6 = 0.89 * sine;
                double harmonic7 = 0.87 * sine;
                double harmonic8 = 0.96 * sine;
                double harmonic9 = 0.4 * sine;
                double harmonic10 = 0.77 * sine;

                float numberOfSignals = 11;
                buf[i] = (byte) ((sine+harmonic1+harmonic2+harmonic3+harmonic4+harmonic5+harmonic6+harmonic7+harmonic8+harmonic9+harmonic10)/numberOfSignals);
            }

            source.write(buf, 0, buf.length);                                    // Write buffer to the SourceDataLine.
            source.drain();                                                            // Play the samples in the buffer.
            source.stop();
            source.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}