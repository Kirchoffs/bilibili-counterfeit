package com.syh.bilibili.service.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

public class RSAUtil {
    static {
        try {
            File file = new File(RSAUtil.class.getClassLoader().getResource("key.properties").toURI());
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));

            PUBLIC_KEY = properties.getProperty("PUBLIC_KEY");
            PRIVATE_KEY = properties.getProperty("PRIVATE_KEY");
        } catch (Exception e) {
            System.out.println("RSAUtil initialization failed");
        }
    }

    private static String PUBLIC_KEY;

    private static String PRIVATE_KEY;

    public static String getPublicKeyStr(){
        return PUBLIC_KEY;
    }

    public static RSAPublicKey getPublicKey() throws Exception {
        byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
        return (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }

    public static RSAPrivateKey getPrivateKey() throws Exception {
        byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public static RSAKey generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));
        return new RSAKey(privateKey, privateKeyString, publicKey, publicKeyString);
    }

    public static String encrypt(String source) throws Exception {
        byte[] decoded = Base64.decodeBase64(PUBLIC_KEY);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory
                .getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, rsaPublicKey);
        return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
    }

    public static Cipher getCipher() throws Exception {
        byte[] decoded = Base64.decodeBase64(PRIVATE_KEY);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, rsaPrivateKey);
        return cipher;
    }

    public static String decrypt(String text) throws Exception {
        Cipher cipher = getCipher();
        byte[] inputByte = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
        return new String(cipher.doFinal(inputByte));
    }

    public static class RSAKey {
        private RSAPrivateKey privateKey;
        private String privateKeyString;
        private RSAPublicKey publicKey;
        public String publicKeyString;

        public RSAKey(RSAPrivateKey privateKey, String privateKeyString, RSAPublicKey publicKey, String publicKeyString) {
            this.privateKey = privateKey;
            this.privateKeyString = privateKeyString;
            this.publicKey = publicKey;
            this.publicKeyString = publicKeyString;
        }

        public RSAPrivateKey getPrivateKey() {
            return this.privateKey;
        }

        public void setPrivateKey(RSAPrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public String getPrivateKeyString() {
            return this.privateKeyString;
        }

        public void setPrivateKeyString(String privateKeyString) {
            this.privateKeyString = privateKeyString;
        }

        public RSAPublicKey getPublicKey() {
            return this.publicKey;
        }

        public void setPublicKey(RSAPublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public String getPublicKeyString() {
            return this.publicKeyString;
        }

        public void setPublicKeyString(String publicKeyString) {
            this.publicKeyString = publicKeyString;
        }
    }
}
