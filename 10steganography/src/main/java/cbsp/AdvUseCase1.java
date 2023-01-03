package cbsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.security.*;
import javax.crypto.*;

public class AdvUseCase1 {
    
    /**
     * BLOCK CIPHER
     */
    public static String[] ALG1 = {"DES","DES/ECB/PKCS5Padding"};
    public static String[] ALG2 = {"DESede","DESede/ECB/PKCS5Padding"};
    public static String[] ALG3 = {"AES","AES/ECB/PKCS5Padding"};
    public static String[] ALG4 = {"AES","AES/CBC/PKCS5Padding"};

    /**
     * STREAM CIPHER
     */
    public static String[] ALG5 = {"RC4","RC4"};
    
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        String orgFilename="src/main/media/tada.wav";
        //String orgFilename="src/main/media/pcm16/pcm stereo 16 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm16/pcm mono 16 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHz.wav";
        //String orgFilename="src/main/media/pcm8/pcm mono 8 bit 48kHz.wav";
        //String orgFilename="sound/noise/Brown_Noise.wav";
        
        String spyFilename="src/main/media/tadaSteganoCrypt.wav";
        //String spyFilename="src/main/media/pcm16/pcm stereo 16 bit 48kHzSteganoCrypt.wav";
        //String spyFilename="src/main/media/pcm16/pcm mono 16 bit 48kHzSteganoCrypt.wav";
        //String spyFilename="src/main/media/pcm8/pcm stereo 8 bit 48kHzSteganoCrypt.wav";
        //String spyFilename="src/main/media/pcm8/pcm mono 8 bit 48kHzSteganoCrypt.wav";
        //String spyFilename="src/main/media/noise/Brown_NoiseSteganoCrypt.wav";
        
        /**
         * SPY and CENTRAL share a shared secret key.
         */
        Key symkey = KeyGenerator.getInstance(ALG5[0]).generateKey();
        
        /**
         * =======
         *  SPY
         * =======
         */
        
        AudioSpyFileRecorder asfr = new AudioSpyFileRecorder();
        System.out.println("SPY: Playing of the original recording.");
        asfr.play(orgFilename);
        
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
        
        System.out.println("SPY: Secret message is:\n" + secretMessage);
        
        // Serialize the secret message into byte[] array.
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream out;   
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(secretMessage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        byte[] secret=bos.toByteArray(); 
        
        /**
         * SPY Uses cryptography
         */
        
        // TASK 6
        
        // Create the Cipher object with the chosen algorithm from the ALG*[1].
        
        // Initialize the Cipher with ENCRYPT_MODE and shared key.
        
        // Encode the byte[] array of the secret message.
        byte[] cipher_TEXT = new byte[]{};
        
        /**
         * SPY Uses steganography
         */
        
        // Use staganoRecord method that we already implemented for the given cipher_TEXT.
        
        // Play the final encoded recording.
        
        /**
         * ==========
         *  CENTRAL
         * ==========
         */
        
        /**
         * CENTRAL Uses steganography
         */
        AudioSpyFilePlayer asfp = new AudioSpyFilePlayer();
        System.out.println("CENTRAL: Playing of the encoded recording.");
        asfp.play(spyFilename);
        
        // Use staganoPlay method that we already implemented to decode the secret message.
        byte[] received_secret = new byte[]{};
        
        /**
         * CENTRAL Uses cryptography
         */
        // Create the Cipher object with the chosen algorithm from the ALG*[1].
        
        // Initialize the Cipher with DECRYPT_MODE and shared key.
        
        // Decode the byte[] array of the secret message.
        byte[] received_clear_TEXT = new byte[]{};
        
        // Deserialize the secret message into String.
        ByteArrayInputStream bis=new ByteArrayInputStream(received_clear_TEXT);
        try {
            ObjectInputStream in=new ObjectInputStream(bis);
            String message= (String) in.readObject();
            System.out.println("CENTRAL: Secret message is:\n"+message);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            
        }
        
    }
}
