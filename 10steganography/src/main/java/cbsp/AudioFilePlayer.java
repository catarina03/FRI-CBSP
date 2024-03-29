package cbsp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioFilePlayer {
    
    public void play(String fileName) {
    	
    	// TASK 1
        // Initialize File object.
        File file = new File(fileName);
        // Declare AudioInputStream object.
        AudioInputStream ais = null;
        // Declare Clip object
        Clip clip = null;
        try {
            // Get AudioInputStream from AudioSystem.
            ais = AudioSystem.getAudioInputStream(file);
            // Get DataLine.Info.
            DataLine.Info dInfo = new DataLine.Info(Clip.class, ais.getFormat());
            // Get current Clip Object and open it.
            clip = (Clip) AudioSystem.getLine(dInfo);
            clip.open(ais);
            // Start the playing of the Clip object.
            clip.start();
            // Wait for the end of the recording.
            Thread.sleep(3000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
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
        
        AudioFilePlayer afp = new AudioFilePlayer();
        System.out.println("Playing the original sound recording.");
        afp.play(orgFilename);
    }
    
}
