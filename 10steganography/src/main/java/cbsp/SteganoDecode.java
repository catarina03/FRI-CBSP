package cbsp;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;

public class SteganoDecode {
    
    private ByteBuffer decodedSecret;
    private byte bitBuffer;
    private int DECODED_SECRET_BITSIZE;
    private int cnt;
    
    public byte[] decodeStream(AudioInputStream inp) throws Exception {
        
        AudioFormat format=inp.getFormat();											// Get the AudioFormat and check if it PCM_SIGNED or PCM_UNSIGNED if not throw an exception.
        if(format.getEncoding()==Encoding.ALAW || format.getEncoding()==Encoding.ULAW)
            throw new Exception();
        
        inp.getFrameLength();
        int nFrames = (int) inp.getFrameLength();									// Number of frames.
        int nChannels = format.getChannels();										// Number of channels (mono/stereo).
        int nSamples = nFrames * nChannels;											// Number of samples.
        int nSampleSizeInBytes = format.getSampleSizeInBits()/Byte.SIZE;			// Size of the single sample in bytes.
        int nBytes = nSamples * nSampleSizeInBytes;									// Number of bytes.
        
        byte[] currentFrame = new byte[nSampleSizeInBytes*nChannels];				// Create a variable for current frame, the size has to be exact to the size of the frame.
        this.decodedSecret = ByteBuffer.allocate(nBytes); 							// Create the output ByteBuffer.
        this.DECODED_SECRET_BITSIZE = Integer.SIZE;									// Minimal size of the frame (4*8 bits, where an Integer is saved).
        this.cnt = 0;
        
        int i;
        for(i=0; i<nFrames; i++) {													// Iterate over all frames.
            inp.read(currentFrame); 												// Read the current frame.
            for(int j=0; j<currentFrame.length; j+=nSampleSizeInBytes) {
                try {
                    byte secretByte=this.decodeByte(currentFrame[j], i*nChannels+j);	// Decode the part of secret messaged in the recording.
                    decodedSecret.put(secretByte);									// Save the read secret into the ByteBuffer.
                } catch(Exception ex) {
                	// This exception is thrown, when we have already read the entire secret.
                }
            }
        }
        
        int decodedSecret_MESSAGE_OFFSET = Integer.SIZE/Byte.SIZE;					// Start of the secret message starts after the Integer that tells as the entire secret message length (4 Bytes).
        int decodedSecret_MESSAGE_LENGTH = this.DECODED_SECRET_BITSIZE/Byte.SIZE;	// End of the secret message.
        byte[] retVal = new byte[decodedSecret_MESSAGE_LENGTH-decodedSecret_MESSAGE_OFFSET];	// Result.
        this.decodedSecret.position(decodedSecret_MESSAGE_OFFSET);					// Set the current pointer in ByteBuffer to the start of the secret message.
        this.decodedSecret.get(retVal);												// Save the result in byte[] array.
        return retVal;																// Return the result.
    }
    
    private byte decodeByte(byte currentByte, int streamPosition) throws Exception {
        
        if(this.cnt == Integer.SIZE) {												// Detect the size of the frame.
            this.DECODED_SECRET_BITSIZE=this.decodedSecret.getInt(0);
            assert(this.DECODED_SECRET_BITSIZE>=32);
        }
        
        if(this.cnt == this.DECODED_SECRET_BITSIZE)									// If we already read all the bits of the secret message, we throw an exception.
            throw new Exception("EOF");
        
        // TASK 5
        
        // Read the current byte and extract the secret bit from LSB and store it.
        
        // Use bit shifting or BitSet objects.
        
        if(this.cnt !=0 && this.cnt % Byte.SIZE == 0)								// We return only bytes, if bit count == n*8.
            return this.bitBuffer;
        else
            throw new Exception("BYTE NOT FULL");
        
    }
}
