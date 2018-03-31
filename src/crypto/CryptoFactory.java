package crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;



public class CryptoFactory {
	
   // Pentru Caesar's Cipher----------------------------------------------------------------------
	
   /**
	* Shiftare caracter
	* @param caracter
	* @param key
	* @return
	*/
    public static char caesar(char caracter, int key) {
        // Constante
        final int alphaLength = 26;
        final char asciiShift = Character.isUpperCase(caracter) ? 'A' : 'a';
        final int cipherShift = key % alphaLength;

        // a...z -> 0...25
        char shifted = (char) (caracter - asciiShift);
        
        // Shifteaza literele
        shifted = (char) ((shifted + cipherShift + alphaLength) % alphaLength);
        
        // Shifteaza inapoi in caractere
        return (char) (shifted + asciiShift);
    }
    
    /**
     * Aplica Caesar's Cipher pe un string
     * @param s
     * @param key
     * @return
     */
    public static String caesarsCipher(String s, int key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(caesar(s.charAt(i), key));
        }
        return sb.toString();
    }	
    
	// Pentru RSA---------------------------------------------------------------------------------- 
	
	/**
     * Criptare RSA
     * @param publicKey
     * @return
     */
    public static final Cipher getRsaEncrypt(PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(SecurityParams.RSA_OEAP);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Cifrul nu a putut fii initializat", e);
        }
    }

    /**
     * Decriptare RSA
     * @param privateKey
     * @return
     */
    public static final Cipher getRsaDecrypt(PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(SecurityParams.RSA_OEAP);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException("Cifrul nu a putut fii initializat", e);
        }
    }

}
