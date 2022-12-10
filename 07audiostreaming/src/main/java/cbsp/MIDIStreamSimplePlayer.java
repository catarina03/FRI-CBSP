package cbsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

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
        for(int i = 0; i<7;i++){
            try {
                // Create Datagram packet.
                packet = new DatagramPacket(rcvBuffer,rcvBuffer.length, InetAddress.getByName("127.0.0.1"),receiverPort);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            // Wait for new packet.
            try {
                rsocket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Extract the byte[] buffer.
            byte[] data = packet.getData();
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 ObjectInputStream in = new ObjectInputStream(bis)) {
                // Deserialize the note object.
                Note note = (Note) in.readObject();
                // Write current Note into buffer.
                buffer.put(note);
            } catch (ClassNotFoundException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }

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

        Note[] notes = new Note[]{new Note(53,500,300),
                new Note(53,500,300),
                new Note(53,500,300),
                new Note(48,500,300),
                new Note(50,500,300),
                new Note(50,500,300),
                new Note(48,500,300)};

        for(int i = 0;i<notes.length;i++){
            try {
                for (Note note : notes){
                    // 	Create ByteArrayOutputStream.
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    // 	Create ObjectOutputStream.
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    // 	Write Note to ObjectOutputStream.
                    oos.writeObject(note);
                    oos.flush();
                    // 	Serialize the object into byte[].
                    byte [] data = bos.toByteArray();
                    // 	Create the DatagramPacket.
                    DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName(DEFAULT_RECEIVER_ADDRESS),DEFAULT_RECEIVER_PORT);
                    // 	Send the DatagramPacket over the tsocket.
                    tsocket.send(packet);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("TRANSMITTER: END");

        mssp.start();	// Start MIDIStreamSimplePlayer.
    }
}

