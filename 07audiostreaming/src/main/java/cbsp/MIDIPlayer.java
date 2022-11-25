package cbsp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public abstract class MIDIPlayer extends Thread {

    protected Synthesizer synthesizer;
    protected MidiChannel channel;
    protected BlockingQueue<Note> buffer;

    MIDIPlayer(int playerChannel, int playerProgram) {
        // TASK 1, 2

        // Create blocking buffer.
        buffer = new LinkedBlockingQueue<>();

        // Create MIDI Synthesizer
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

        // Set the current MIDI channel.
        channel = synthesizer.getChannels()[playerChannel];

        // Set the current MIDI instrument.
        channel.programChange(playerProgram);
    }

    public abstract void rcv();

    public void run() {
        Note t = null;

        System.out.println("PLAYER: START");

        this.rcv();

        while (!buffer.isEmpty()) {
            // TASK 1, 2

            // Get the latest Note from the buffer.
            try {
                t = buffer.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Play the Note.
            try {
                channel.noteOn(t.pitch, t.velocity);
                sleep(t.duration);
                channel.noteOff(t.pitch, t.velocity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
