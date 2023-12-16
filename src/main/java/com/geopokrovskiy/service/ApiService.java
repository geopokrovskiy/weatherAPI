package com.geopokrovskiy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class ApiService implements IApiService{
    @Value("${api.secret}")
    private String apiKeySecret;
    @Value("${api.iterations}")
    private int iterations;
    @Value("${api.key-length}")
    private int keyLength;
    private static final String BASIC = "BASIC";
    private static final String SILVER = "SILVER";
    private static final String GOLD = "GOLD";
    private static final String SECRET_KEY_INSTANCE = "PBKDF2WithHmacSHA512";

    @Override
    public String generateBasicApiKey(String username){
        byte[] result;
        String rawKey = username + BASIC;
        try {
            result = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE)
                    .generateSecret(new PBEKeySpec(rawKey.toCharArray(),
                            apiKeySecret.getBytes(), iterations, keyLength))
                    .getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder()
                .encodeToString(result);
    }

    @Override
    public String generateBasicApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    @Override
    public String generateSilverApiKey(String username) {
        byte[] result;
        String rawKey = username + SILVER;
        try {
            result = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE)
                    .generateSecret(new PBEKeySpec(rawKey.toCharArray(),
                            apiKeySecret.getBytes(), iterations, keyLength))
                    .getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder()
                .encodeToString(result);
    }

    @Override
    public String generateSilverApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    @Override
    public String generateGoldApiKey(String username) {
        byte[] result;
        String rawKey = username + GOLD;
        try {
            result = SecretKeyFactory.getInstance(SECRET_KEY_INSTANCE)
                    .generateSecret(new PBEKeySpec(rawKey.toCharArray(),
                            apiKeySecret.getBytes(), iterations, keyLength))
                    .getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder()
                .encodeToString(result);
    }

    @Override
    public String generateGoldApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    public boolean basicMatches(String username, String apiKey){
        return this.generateBasicApiKey(username).equals(apiKey);
    }

}
