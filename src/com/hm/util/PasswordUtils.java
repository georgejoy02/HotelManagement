package com.hm.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

//	public static void main(String[] args) {
//		PasswordUtils testObj=new PasswordUtils();
//		
//		try {
//			System.out.println(testObj.generatePasswordHash("PassAdm@123"));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//1000:5b42403230393634343264:590f1ebe12e6244e895afd8176353c0be3aeb96d2f3502ce46879fe9079438515626d4260ed4984b8124169893b5212f7754a909def508e320e5e675400c540a
//	}

	public boolean validatePassword(String originalPassword, String storedPassword) {
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt;
		byte[] hash = null;
		byte[] testHash = null;

		try {
			salt = fromHex(parts[1]);
			hash = fromHex(parts[2]);

			PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			testHash = skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int diff = hash.length ^ testHash.length;

		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}

		return diff == 0;
	}

	public String generatePasswordHash(String password) {
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = null;
		byte[] hash = null;
		try {
			salt = getSalt().getBytes();
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return iterations + ":" + toHex(salt) + ":" + toHex(hash);

	}

	private String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

	private String toHex(byte[] array) {

		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
