package cbsp;

import java.io.*;
import java.util.ArrayList;

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

        try {
            // Open the input FileInputStream.
            //fis.
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            // Deserialize the input stream with ObjectInputStream.
            for(int i = 0; i <7; i++){
                Note note_read = (Note) in.readObject();
                buffer.put(note_read);
            }
            in.close();
            fis.close();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("RECEIVER FS: END");
    }

    public static void main(String[] args) {

        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        MIDIFileSimplePlayer mfp = new MIDIFileSimplePlayer(0, 1, "notes.bin");

        System.out.println("TRANSMITTER: START");

        // TASK 4

        Note[] notes = new Note[]{new Note(53,500,300),
                new Note(53,500,300),
                new Note(53,500,300),
                new Note(48,500,300),
                new Note(50,500,300),
                new Note(50,500,300),
                new Note(48,500,300)};

        try {
            // Write Notes to a file ("notes.bin").
            fos = new FileOutputStream("notes.bin", true);
            out = new ObjectOutputStream(fos);
            for (Note note : notes) {
                out.writeObject(note);
            }
            out.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("TRANSMITTER: END");

        mfp.start();	// Start MIDIFileSimplePlayer
    }

}

