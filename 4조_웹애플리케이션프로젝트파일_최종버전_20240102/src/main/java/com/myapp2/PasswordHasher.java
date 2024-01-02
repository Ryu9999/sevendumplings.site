package com.myapp2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {
	private static final String HASH_ALGORITHM = "SHA-256";

	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			String data = password + salt;
			byte[] hashedBytes = digest.digest(data.getBytes());
			return bytesToHex(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		return bytesToHex(saltBytes);
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}
