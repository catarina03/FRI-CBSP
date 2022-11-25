package cbsp;

public class MIDIDummyPlayer extends MIDIPlayer {

    MIDIDummyPlayer(int playerChannel, int playerProgram) {

        super(playerChannel, playerProgram);

    }

    @Override
    public synchronized void rcv() {

        System.out.println("RECEIVER DUMMY: START");

        System.out.println("RECEIVER DUMMY: END");

    }

    public static void main(String[] args) {

        MIDIDummyPlayer mp = new MIDIDummyPlayer(0, 1);

        System.out.println("TRANSMITTER: START");

        // TASK 3

        // Write Notes into shared memory, you can use example from previous exercises.
        try {
            mp.buffer.put(new Note(53,500,300));
            mp.buffer.put(new Note(53,500,300));
            mp.buffer.put(new Note(53,500,300));
            mp.buffer.put(new Note(48,500,300));
            mp.buffer.put(new Note(50,500,300));
            mp.buffer.put(new Note(50,500,300));
            mp.buffer.put(new Note(48,500,300));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("TRANSMITTER: END");

        mp.start();
    }
}