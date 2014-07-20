package hr.fer.zemris.java.aplikacija5.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

	private byte[] digest;
	private MessageDigest shaDigest;
	private String textToHash;

	public Crypto(String textToHash) {
		this.textToHash = textToHash;

		try {
			this.shaDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {}
	}

	public Crypto calculateDigest() {
		shaDigest.update(textToHash.getBytes());
		this.digest = shaDigest.digest();
		return this;
	}

	public byte[] getDigest() {
		return this.digest;
	}

	public boolean hashCompare(String hash) {
		return getDigestAsHexString().equals(hash);
	}

	public String getDigestAsHexString() {
		return bytesToHex(this.getDigest());
	}

	
	private String bytesToHex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
	    for (int i = 0; i < bytes.length; i++) {
	    	buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    
	    return buffer.toString();
	}
}