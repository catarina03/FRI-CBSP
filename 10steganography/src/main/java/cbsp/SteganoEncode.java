package cbsp;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;

public class SteganoEncode {
    
    private byte[] encodedSecret;
    private int ENCODED_SECRET_BITSIZE;
    private int cnt;
    
    public AudioInputStream encodeStream(AudioInputStream inp, byte[] secret) throws Exception {
        
        assert(inp != null);														// Throw an exception if inp is null. 
        
        int nFrameBytes = Integer.SIZE/Byte.SIZE+((secret!=null)?secret.length:0);	// Get the number of needed frames for the length of secret and the secret.
        int nFrameBits = nFrameBytes*Byte.SIZE;										// Get the number of bits from the number of frames.
        ByteBuffer tmp=ByteBuffer.allocate(nFrameBytes); 							// Create and ByteBuffer with the length of nFrameBytes.
        tmp.putInt(nFrameBits);														// Write the length secret message in bits.
        
        if(secret != null)
            tmp.put(secret); 														// Write the secret.
        
        this.encodedSecret = tmp.array();
        this.ENCODED_SECRET_BITSIZE = this.encodedSecret.length*Byte.SIZE + Integer.SIZE;	// Encoded secret length in bits
        this.cnt = 0;

        AudioFormat format = inp.getFormat();										// Get the AudioFormat and check if it PCM_SIGNED or PCM_UNSIGNED if not throw an exception.
        if(format.getEncoding() == Encoding.ALAW || format.getEncoding() == Encoding.ULAW)
            throw new Exception();
        
        inp.getFrameLength();
        int nFrames = (int) inp.getFrameLength();									// Number of frames.
        int nChannels = format.getChannels();										// Number of channels (mono/stereo).
        int nSamples = nFrames * nChannels;											// Number of samples.
        int nSampleSizeInBytes = format.getSampleSizeInBits()/Byte.SIZE;			// Size of the single sample in bytes.
        int nBytes = nSamples * nSampleSizeInBytes;									// Number of bytes.
        
        byte[] currentFrame = new byte[nSampleSizeInBytes*nChannels]; 				// Create a variable for current frame, the size has to be exact to the size of the frame.
        ByteBuffer outdata = ByteBuffer.allocate(nBytes); 							// Create the output ByteBuffer.
        
        for(int i=0; i<nFrames; i++) { 												// Iterate over all frames.
            inp.read(currentFrame);													// Read the current frame.
            for(int j=0; j<currentFrame.length; j+=nSampleSizeInBytes) {
                try {
                    currentFrame[j] = this.encodeByte(currentFrame[j], i*nChannels+j);	// Encode the part of secret messaged in the recording.
                } catch(Exception ex) {
                    // This exception is thrown, when we have already write the entire secret.
                }
            }
            outdata.put(currentFrame);												// Write the entire frame.
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(outdata.array());		// Create new input stream.
        AudioInputStream out = new AudioInputStream(bis, format, nSamples); 		// Create new audio output stream.
        
        return out;
    }
    
    private byte encodeByte(byte currentByte, int streamPosition) throws Exception {   	

        if(this.cnt == this.ENCODED_SECRET_BITSIZE)									// Check if last bit has been already written.
            throw new Exception("EOF");												// Throw an exception.
        
        // TASK 3
        
        // Take the current byte and store the secret bit into LSB and return it.
        
        // Use bit shifting or BitSet objects.
        
        return currentByte;															// Return the encoded byte.
    }

}
