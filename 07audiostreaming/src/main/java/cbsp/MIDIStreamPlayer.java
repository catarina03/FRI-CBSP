package cbsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

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
        for(int i = 0; i<7;i++){
            // Create Datagram packet.
            DatagramPacket packet = null;
            try {
                packet = new DatagramPacket(rcvBuffer,rcvBuffer.length, InetAddress.getByName("127.0.0.1"),receiverPort);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            try {
                // Wait for new packet.
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
        Note[] notes = new Note[]{new Note(53,500,300),
                new Note(53,500,300),
                new Note(53,500,300),
                new Note(48,500,300),
                new Note(50,500,300),
                new Note(50,500,300),
                new Note(48,500,300)};

        // For each Note repeat:
        for (Note note : notes){
            try {
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("TRANSMITTER: END");

    }
}

