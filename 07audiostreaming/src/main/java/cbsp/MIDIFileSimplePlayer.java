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
        try {
            fos = new FileOutputStream("notes.bin", true);

            try (FileOutputStream fos = new FileOutputStream("object.dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos))  {

                // create a new user object
                User user = new User("John Doe", "john.doe@example.com",
                        new String[]{"Member", "Admin"}, true);

                // write object to file
                oos.writeObject(user);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // we need to transfer this string to files
        String st = "TATA";

        Note[] notes = new Note[]{new Note(53,500,300),
                new Note(53,500,300),
                new Note(53,500,300),
                new Note(48,500,300),
                new Note(50,500,300),
                new Note(50,500,300),
                new Note(48,500,300)};

        for (Note note : notes) {
            // we will write the string by writing each
            // character one by one to file
            fos.writeObject(note);
        }

        // by doing fout.close() all the changes which have
        // been made till now in RAM had been now saved to
        // hard disk
        fos.close();

        System.out.println("TRANSMITTER: END");

        mfp.start();	// Start MIDIFileSimplePlayer
    }

}

