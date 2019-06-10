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

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/qqJFi6kmm7zMfD5AYEqi1wBqcJ75gTEeBAnEgK6xhP49a20Nb3SQ6TTvmGrvZQZPTkSfO78o9/5G8RXtM4Z2UOmS/Fg7fvKP2ydRCaXDB5XUq9tO+JNf1gsGH7i0GzBom2uIl40z997U9PRQwFjbOfeF8SN/OvS+P5pEyhOG1QIDAQAB";
    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL+qokWLqSabvMx8PkBgSqLXAGpwnvmBMR4ECcSArrGE/j1rbQ1vdJDpNO+Yau9lBk9ORJ87vyj3/kbxFe0zhnZQ6ZL8WDt+8o/bJ1EJpcMHldSr2074k1/WCwYfuLQbMGiba4iXjTP33tT09FDAWNs594XxI3869L4/mkTKE4bVAgMBAAECgYAGsz1W9wnjup//+fvnHjaduKxgDC3ShQylgvigcOsqc367wOygApxtkGl0UryyP7LXxOGrO1h0Yy71ZeohT6LMoor83YYzDSrctT2WijmYjuug3rQnJVNpg4MldJBEVkwveuXNsrdsYI8WTPK+8toT5hkkkDPfk1gPR7IQMBnDJQJBAORIw14zBCpmg58ZXmKAf2Vc6YMSeWzAv4Z9CDf+AKP+OrXZ6D1WwAKnsdj+e48TN5MR3/9SXpLGlNRivjcKYbcCQQDW78RGTyqpl5Izh299Oi8glMsqAthJRi9ZM9HpUyCvkS86dfWqNMQwuMXpq98SF9piuuOWXQ2GXQYGKgSZvuvTAkAjHI3wDyi03M+hyOUbgWgWzxObdVv3vi23IcQB2K+Aibm9/qYZyR3/SouwNHQMrYf+tuEdBC5HpQN/JzDahl5BAkA7xJCfvIPwhOVyzsaB3Bwew0F7fS8HsSyZcX99klAUghrP9t1JZ0LnCXp/b/un3Fot+iovNdD8/AEg1a5R0nVhAkBN25sVwo0HC2Ml1sr8fT5KzX1/FXlFYCe8C7ZOQicgrt8asuflx9gJ744YdnURDZj2CULBT7aA0NcjB4zgiUH/";

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
