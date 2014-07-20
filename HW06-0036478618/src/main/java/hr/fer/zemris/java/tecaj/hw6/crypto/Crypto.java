package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Ivan Krpelnik
 *
 */
public class Crypto {

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		if(args.length == 2 && args[0].equalsIgnoreCase("checksha")) {
			System.out.println("Please provide expected sha signature for " + args[1] + ": ");
			System.out.print(">");
			String line = reader.readLine();
			checksha(Paths.get(args[1]), hextobyte(line));
		} else if(args.length == 3 && 
				(args[0].equalsIgnoreCase("encrypt") || args[0].equalsIgnoreCase("decrypt"))
				&& Files.isRegularFile(Paths.get(args[1]))) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			String keyText = reader.readLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			String ivText = reader.readLine();
			try {
				crypt(keyText, ivText, Paths.get(args[1]), Paths.get(args[2]), args[0].equalsIgnoreCase("encrypt"));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unkown arguments.");
		}
		reader.close();
	}
	
	public static void checksha(Path path, byte[] shasig) throws IOException {
		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}
		FileInputStream reader = new FileInputStream(path.toFile());
		byte[] input = new byte[1024];
		int len;
		while((len = reader.read(input)) != -1) {
			digester.update(input, 0, len);
		}
		byte[] hash = digester.digest();
		reader.close();
		boolean equal = true;;
		for(int i = 0; i < hash.length; ++i) {
			equal &= hash[i] == shasig[i]; 
		}
		
		if(equal) {
			System.out.println("Digesting completed. Digest of " + path.getFileName() + " matches expected digest.");
		} else {
			System.out.println("Digesting completed. "
					+ "Digest of " + path.getFileName() + " does not match the expected digest. Digest was:" + 
					new String(hash, "UTF-8"));
		}
	}
	
	public static void crypt(String keyText, String ivText, Path input, Path output, boolean encrypt) 
			throws InvalidKeyException, IOException {
		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return;
		}
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidAlgorithmParameterException e1) {
			e1.printStackTrace();
			return;
		}
		
		FileInputStream reader = new FileInputStream(input.toFile());
		FileOutputStream writer = new FileOutputStream(output.toFile(), true);
		byte[] bytes = new byte[1024];
		int len;
		while((len = reader.read(bytes)) != -1) {
			writer.write(cipher.update(bytes, 0, len));
			writer.flush();
		}
		try {
			writer.write(cipher.doFinal());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			reader.close();
			writer.close();
			return;
		} catch (BadPaddingException e) {
			e.printStackTrace();
			reader.close();
			writer.close();
			return;
		}
		reader.close();
		writer.close();
		System.out.printf("Decryption completed. Generated file %s based on file %s.", 
				input.toFile().getName(), output.toFile().getName());
		System.out.println();
	}
	
	public static byte[] hextobyte(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
}
