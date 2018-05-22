package crypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class CryptoUtils {
	
    // Pentru Caesar's Cipher----------------------------------------------------------------------	
    public static final String decryptCaesar(String s, int key) {
		return CryptoFactory.caesarsCipher(s, -key);
    }
	
    public static final String encryptCaesar(String s, int key) {
		return CryptoFactory.caesarsCipher(s, key);
    }
    
    public static final int generateRandomKey() {
    	Random rand = new Random();
    	return rand.nextInt(26);
    }
	
    // Pentru RSA----------------------------------------------------------------------------------
    /**
     * Decriptare cu algoritmul RSA
     * @param cipherText
     * @param privateKey
     * @return plainText
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static final byte[] decryptRsa(byte[] cipherText, PrivateKey privateKey)
            throws IllegalBlockSizeException, BadPaddingException {
        return CryptoFactory.getRsaDecrypt(privateKey).doFinal(cipherText);
    }
    
    /**
     * Criptare cu algoritmul RSA
     * @param plainText
     * @param publicKey
     * @return cipherText
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static final byte[] encryptRsa(byte[] plainText, PublicKey publicKey)
            throws IllegalBlockSizeException, BadPaddingException {
        return CryptoFactory.getRsaEncrypt(publicKey).doFinal(plainText);
    }

}
