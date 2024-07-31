package com.akichou.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * JwtKey Generator Utility
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public class JwtKeyGenerator {
    public static void main(String[] args) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("Generated JWT Key: " + base64Key);
    }
}