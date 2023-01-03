package cbsp;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioSpyFileRecorder extends AudioSpyFilePlayer {
    
    public void record(String orgFilename, String spyFilename) {
    	
    	// TASK 2

    	// Copy the contents of original file into the spy file.
        // Initialize two File objects.
        File fileO = new File(orgFilename);
        File fileS = new File(spyFilename);
        // Get AudioInputStream from AudioSystem.
        AudioInputStream aisO = null;
        try {
            aisO = AudioSystem.getAudioInputStream(fileO);
            // Get AudioFileFormat from AudioSystem.
            AudioFileFormat affO = null;
            affO = AudioSystem.getAudioFileFormat(fileO);
            // Use write method of the AudioSystem to write data.
            AudioSystem.write(aisO, affO.getType(),fileS);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void steganoRecord(String orgFilename, String spyFilename, byte[] secret) {
    	
    	// TASK 2
        // Initialize two File objects.
        File fileO = new File(orgFilename);
        File fileS = new File(spyFilename);
        // Get two AudioInputStream from AudioSystem.
        AudioInputStream aisO = null;
        try {
            aisO = AudioSystem.getAudioInputStream(fileO);
            // Get AudioFileFormat from AudioSystem.
            AudioFileFormat affO = AudioSystem.getAudioFileFormat(fileO);
            // Create new SteganoEncode object, that we will create in next task.
            SteganoEncode steganoEncode = new SteganoEncode();
            // Encode the input stream with secret message.
            AudioInputStream aisS = steganoEncode.encodeStream(aisO,secret);
            // Use write method of the AudioSystem to write encoded data.
            AudioSystem.write(aisS, affO.getType(),fileS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    	
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        String orgFilename="src/main/media/tada.wav";
        //String orgFilename="src/main/media/pcm16/pcm stereo 16 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm16/pcm mono 16 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm8/pcm mono 8 bit 48kHz.wav";
        //String orgFilename="src/main/media/noise/Brown_Noise.wav";
        
        String spyFilename="src/main/media/tadaSpy.wav";
        //String spyFilename="src/main/media/pcm16/pcm stereo 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm16/pcm mono 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm mono 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/noise/Brown_NoiseSpy.wav";
        
        AudioSpyFileRecorder asfr = new AudioSpyFileRecorder();
        System.out.println("Playing the original sound recording.");
        asfr.play(orgFilename);		// Play the original recording.
        
        // Write a secret message.
        String secretMessage="Oak is strong and also gives shade. "
                           + "Cats and dogs each hate the other. "
                           + "The pipe began to rust while new. "
                           + "Open the crate but don't break the glass. "
                           + "Add the sum to the product of these three. "
                           + "Thieves who rob friends deserve jail. "
                           + "The ripe taste of cheese improves with age. "
                           + "Act on these orders with great speed. "
                           + "The hog crawled under the high fence. "
                           + "Move the vat over the hot fire. ";
                                 
        System.out.println("The secret message is:\n"+secretMessage);
        
        // TASK 2
        
        // Serialize the secret message into byte[] array.
        
        // Use the ByteArrayOutputStream and ObjectOutputStream objects.
                
        byte[] secret = new byte[]{};		// Get the byte[] array.
        
        asfr.steganoRecord(orgFilename, spyFilename, secret);	// Encode the secret message.
        System.out.println("Playing the encoded sound recording.");
        asfr.play(spyFilename);		// Play the encoded sound recording and listen for changes and artefacts in the recording.
        
    }
}
