package com.icefire.assignment.encryptation;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhUTB30ayT0Mqb+HpNoa64Q7noq5/OgwBvLes934GcaFefxgDbtVpoQ3XZ5DnmjB0pd1A4sFOkGdCerHYqCCaY40glLQdHXGN2YYYnK8kcFkD0EVmm9bkXuFoO4EIDZN8yuXWUlFSerH9jFWYo1L7CrKQSyc3eINyjjM0g+P4nGwIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJjJr8Pl8+9FNaDfREqLRUXW18DDMaJuEp4SYmLJkjOe5sWPtzvLKTNol6Aty1IGGnXkSDxSDU3WMJ3e3UxpdFQA1ZWFNBEPZSbJCAWK+k60Clpl+T3YPj0Cj/vE3yOouD7aBkGe8fD0JMiQrlh6ecF6VO1MieZJXvsvbF+Fh9UvAgMBAAECgYBwScHwgj7duYqpWY0SD/rwUe4s0AfZbXYnUDGYkrXtVDocNfwfaLc1/gd5hE5qUOv/eq+QDWmTq/f8/n67msEPmVQTs4v0AUo1Tjn96lMcmNIjGDcrKlJ22blrfr1oR43T2f5syAbXEiH0gNOH8pAlEE3joWCcMsyodxFlhoRYwQJBAOfDNrFlbZpnfFb7z8fYUgTJlDFbLYFht2BP9Q93UGd8vvXjWtPM4uwHmLLBMTO4MiMvD0yKObYTpFcyfKKrw08CQQCoxCYC5+HSmUAe5FdJuHvEobHiWXiN8CTw8Cvpx9zsUYMtQ8rW1wNiwcfMk+zj0i6eraQMZx49ulUQx3SDl9ghAkARMZxXIM02zUKeOWQ60UOUc1f5KWiA1r0B+iyWDyX553lLSKiuWd7j39RmPorl6V5e2djqtr59UYu5pFc2wkNlAkEApK3yIXe/xc7A4OQTwN4B99eISsTffDFO+sSBNThPfVH03KCAN6/Ln/xTSG84Av6/exsZLvkYa0d9Zkgai6YJwQJAWfBKpKcWKvxTtawA8M4UKustAPZJ1UnB9Gq1UytuAPpmjj1Gb6OQVcTZUZrTfaXrISxWxxG25Zs0Cpuep3X6yA==";

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("Encrypted info 2", publicKey));
            System.out.println(encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}
