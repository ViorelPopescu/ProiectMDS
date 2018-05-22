package crypto;

public class SecurityParams {

	// Caesar Cipher Diffie-Hellman
	public static final int G = 8;
	public static final int P = 3;
	public static final int sKey = 5;
	
    // RSA
    public static final int RSA_KEY_SIZE_BYTES = 512;
    public static final String RSA_OEAP = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    public static final String RSA_KEY_TYPE = "RSA";
    public static final String RSA_SIGN = "SHA256withRSA";

}
