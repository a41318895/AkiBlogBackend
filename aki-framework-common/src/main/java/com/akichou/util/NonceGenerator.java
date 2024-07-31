package com.akichou.util;

import java.security.SecureRandom;

/**
 * Nonce Generator
 *
 * @author Aki Chou
 * @date 2024/07/26 Fri.
 */
public class NonceGenerator {

    public static String generateNonce() {

        SecureRandom secureRandom = new SecureRandom();
        byte[] nonceBytes = new byte[32];
        secureRandom.nextBytes(nonceBytes);
        return bytesToHex(nonceBytes);
    }

    private static String bytesToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
