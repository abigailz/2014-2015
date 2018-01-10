package encrypt;

import java.security.MessageDigest;

/**
 * 
 * @author ellison
 */
public class EncryptUtil {


	// private static String tokenKey="L8AC48r2";
	// private static String pwdKey="^#&#(*#&&#";

	public static byte[] MD5(byte[] data) {
		byte[] ret = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// This replaces the existing hash, it does not add to it
			digest.reset();

			ret = digest.digest(data);

		} catch (Exception ex) {

			ret = null;
		}

		return ret;
	}

	public static byte[] SHA1(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] ret = md.digest(data);
			
			//md.update(data);
			//return md.digest();
			
			return ret;
		} catch (Exception ex) {
			return null;
		}
	}

//	public static String byteToHexString(byte[] bytes) {
//		return String.valueOf(Hex.encodeHex(bytes));
//	}
	
	public static byte[] getUTF8Bytes(String s) {
		try {
			byte[] ret = s.getBytes("UTF-8");
			return ret;
		} catch (Exception ex) {
		}
		return s.getBytes();
	}

	private static String getTokenKey() {
		return "L8AC48r2";//Environment.getInstance().getProperty("auth.token.key");
	}

	public static String decrypt(String value) {
		return decrypt(getTokenKey(), value);
	}

	public static String decrypt(String key, String value) {
		PassportCoder crypt = new PassportCoder(key);
		return crypt.decrypt(value);
	}

	public static String encrypt(String value) {
		return encrypt(getTokenKey(), value);
	}

	public static String encrypt(String key, String value) {
		PassportCoder crypt = new PassportCoder(key);
		return crypt.encrypt(value);
	}
}
