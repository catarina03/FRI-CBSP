package cbsp;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class AudioSpyFilePlayer extends AudioFilePlayer {
    
    public byte[] steganoPlay(String spyFilename) {
    	
    	// TASK 4

        // Initialize File object.
        File file = new File(spyFilename);
        // Declare AudioInputStream.
        AudioInputStream ais = null;
        try {
            // Get AudioInputStream from AudioSystem.
            ais = AudioSystem.getAudioInputStream(file);
            // Initialize SteganoDecode object, that we will create in next task.
            SteganoDecode sd = new SteganoDecode();
            // Decode the input stream.
            return sd.decodeStream(ais);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    public static void main(String[] args) throws InterruptedException {
        
        String spyFilename="src/main/media/tadaSpy.wav";
        //String spyFilename="src/main/media/pmc16/pcm stereo 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm16/pcm mono 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm mono 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/noise/Brown_NoiseSpy.wav";

        // TASK 4

        // Deserialize the secret message into String.
        AudioSpyFilePlayer asfp = new AudioSpyFilePlayer();
        System.out.println("Playing of the encoded recording.");
        asfp.play(spyFilename);
        byte[] received_secret=asfp.steganoPlay(spyFilename);
        String msg = new String(received_secret, StandardCharsets.UTF_8);
        System.out.println(msg);

    }
    
}
