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

public class MIDIStreamSimplePlayer extends MIDIPlayer {

    protected int receiverPort;
    protected DatagramSocket rsocket;
    protected byte[] rcvBuffer;
    protected DatagramPacket packet;

    protected ByteArrayInputStream bis;		// ByteArrayInputStream instead of FileInputStream.
    protected ObjectInputStream in;

    public MIDIStreamSimplePlayer(int playerChannel, int playerProgram, int receiverPort) {

        super(playerChannel, playerProgram);

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

        System.out.println("RECEIVER ST: START");

        // TASK 5

        // Create Datagram packet.

        // Wait for new packet.

        // Extract the byte[] buffer.

        // Deserialize the note object.

        // Write current Note into buffer.

        System.out.println("RECEIVER ST: END");
    }

    public static void main(String[] args) {

        int DEFAULT_TRANSMITTER_PORT = 3334;
        int DEFAULT_RECEIVER_PORT = 3333;
        String DEFAULT_RECEIVER_ADDRESS = "127.0.0.1";

        MIDIStreamSimplePlayer mssp = new MIDIStreamSimplePlayer(0, 1, DEFAULT_RECEIVER_PORT);

        System.out.println("TRANSMITTER: START");
        DatagramSocket tsocket = null;
        try {
            tsocket = new DatagramSocket(DEFAULT_TRANSMITTER_PORT);		// Open the socket on chosen IP and port.
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        // TASK 5

        // For each Note repeat:

        // 	Create ByteArrayOutputStream.

        // 	Create ObjectOutputStream.

        // 	Write Note to ObjectOutputStream.

        // 	Serialize the object into byte[].

        // 	Create the DatagramPacket.

        // 	Send the DatagramPacket over the tsocket.

        System.out.println("TRANSMITTER: END");

        mssp.start();	// Start MIDIStreamSimplePlayer.
    }
}

