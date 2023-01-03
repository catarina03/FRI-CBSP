package cbsp;


public class AudioSpyFilePlayer extends AudioFilePlayer {
    
    public byte[] steganoPlay(String spyFilename) {
    	
    	// TASK 4
    	
    	// Initialize File object.
    	
    	// Declare AudioInputStream.
    	
    	// Get AudioInputStream from AudioSystem.
    	
    	// Initialize SteganoDecode object, that we will create in next task.
    	
    	// Decode the input stream.
        
        byte[] secret=null;
                
        return secret;
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        String spyFilename="src/main/media/tadaSpy.wav";
        //String spyFilename="src/main/media/pmc16/pcm stereo 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm16/pcm mono 16 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/pcm8/pcm mono 8 bit 48kHzSpy.wav";
        //String spyFilename="src/main/media/noise/Brown_NoiseSpy.wav";
        
        AudioSpyFilePlayer asfp = new AudioSpyFilePlayer();
        System.out.println("Playing of the encoded recording.");
        asfp.play(spyFilename);
        byte[] received_secret=asfp.steganoPlay(spyFilename); 
        
        // TASK 4
        
        // Deserialize the secret message into String.
        
    }
    
}
