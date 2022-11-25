package cbsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class MIDIStreamPlayer extends MIDIPlayer implements Callable {

    protected int playerChannel;
    protected int playerProgram;

    protected int receiverPort;
    protected DatagramSocket rsocket;
    protected byte[] rcvBuffer;
    protected DatagramPacket packet;

    protected ByteArrayInputStream bis;
    protected ObjectInputStream in;

    public MIDIStreamPlayer(int playerChannel, int playerProgram, int receiverPort) {

        super(playerChannel, playerProgram);

        this.playerChannel = playerChannel;
        this.playerProgram = playerProgram;
        this.receiverPort=receiverPort;
        try {
            this.rsocket = new DatagramSocket(receiverPort);	// Create a Datagram socket.
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        this.rcvBuffer=new byte[1024];		// Create buffer.
    }

    @Override
    public synchronized void rcv() {
        new WorkerThread(this).start();
    }

    public synchronized void callback() {
        System.out.println("RECEIVER ST: START");
        // TASK 6

        // Create Datagram packet.

        // Wait for new packet.

        // Extract the byte[] buffer.

        // Deserialize the note object.

        // Write current Note into buffer.

        System.out.println("RECEIVER ST: END");
    }

    public static void main(String[] args) {

        int DEFAULT_TRANSMITTER_PORT = 3400;
        int DEFAULT_RECEIVER_PORT = 3300;
        String DEFAULT_RECEIVER_ADDRESS = "127.0.0.1";

        MIDIStreamPlayer msp = new MIDIStreamPlayer(0, 1, DEFAULT_RECEIVER_PORT);
        msp.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("TRANSMITTER: START");
        DatagramSocket tsocket = null;
        try {
            tsocket = new DatagramSocket(DEFAULT_TRANSMITTER_PORT);		// Open the socket on chosen IP and port.
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        // TASK 6

        // For each Note repeat:

        // 	Create ByteArrayOutputStream.

        // 	Create ObjectOutputStream.

        // 	Write Note to ObjectOutputStream.

        // 	Serialize the object into byte[].

        // 	Create the DatagramPacket.

        // 	Send the DatagramPacket over the tsocket.

        System.out.println("TRANSMITTER: END");

    }
}

