package com.nevermind.client.util;

import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@UtilityClass
public class CryptUtil {

    public static String encode(PublicKey publicKey, String message) {
        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] secretMessageBytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error during encode");
        }
    }

    public static String decode(PrivateKey privateKey, String message) {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] base64DecodedMessage = Base64.getDecoder().decode(message);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(base64DecodedMessage);
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error during decode");
        }
    }

    public static String encodePublicKey(PublicKey publicKey) {
        byte[] byteKey = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(byteKey);
    }

    public static PublicKey decodePublicKey(String encodedKey) {
        byte[] byteKey = Base64.getDecoder().decode(encodedKey);
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(new X509EncodedKeySpec(byteKey));
        } catch (Exception e) {
            throw new RuntimeException("Error during decode key");
        }
    }
}
