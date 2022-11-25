package cbsp;

import java.io.EOFException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MIDIFileSimplePlayer extends MIDIPlayer {

    protected String filename;
    protected FileInputStream fis;
    protected ObjectInputStream in;

    public MIDIFileSimplePlayer(int playerChannel, int playerProgram, String filename) {

        super(playerChannel, playerProgram);
        this.filename = filename;

    }

    @Override
    public synchronized void rcv() {

        System.out.println("RECEIVER FS: START");

        // TASK 4

        // Open the input FileInputStream.
        //fis.

        // Deserialize the input stream with ObjectInputStream.

        // Write Notes from file to buffer.

        System.out.println("RECEIVER FS: END");
    }

    public static void main(String[] args) {

        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        MIDIFileSimplePlayer mfp = new MIDIFileSimplePlayer(0, 1, "notes.bin");

        System.out.println("TRANSMITTER: START");

        // TASK 4

        // Write Notes to a file ("notes.bin").

        System.out.println("TRANSMITTER: END");

        mfp.start();	// Start MIDIFileSimplePlayer
    }

}

