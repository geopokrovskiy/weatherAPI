package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class ApiService implements IApiService {
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
    public String generateBasicApiKey(String username) {
        String rawKey = username + BASIC + apiKeySecret;
        return Base64.getEncoder().encodeToString(rawKey.getBytes());
    }

    @Override
    public String generateBasicApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    @Override
    public String generateSilverApiKey(String username) {
        String rawKey = username + SILVER + apiKeySecret;
        return Base64.getEncoder().encodeToString(rawKey.getBytes());
    }

    @Override
    public String generateSilverApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    @Override
    public String generateGoldApiKey(String username) {
        String rawKey = username + GOLD + apiKeySecret;
        return Base64.getEncoder().encodeToString(rawKey.getBytes());
    }

    @Override
    public String generateGoldApiKey(String username, LocalDateTime generatedAt) {
        return null;
    }

    @Override
    public boolean basicMatches(String username, String apiKey) {
        return this.generateBasicApiKey(username).equals(apiKey);
    }

    @Override
    public boolean silverMatches(String username, String apiKey) {
        return false;
    }

    @Override
    public boolean goldMatches(String username, String apiKey) {
        return false;
    }

    @Override
    public Object[] restoreUsernameAndSubscription(String apiKey) {
        byte[] decodedKey = Base64.getDecoder().decode(apiKey);
        String rawKey = new String(decodedKey);
        String usernameAndSubscription = rawKey.substring(0, rawKey.length() - apiKeySecret.length());
        if (usernameAndSubscription.endsWith("BASIC")) {
            return new Object[]{usernameAndSubscription.substring(0, usernameAndSubscription.length() - BASIC.length()), Status.BASIC};
        } else if (usernameAndSubscription.endsWith("SILVER")) {
            return new Object[]{usernameAndSubscription.substring(0, usernameAndSubscription.length() - SILVER.length()), Status.SILVER};
        } else if (usernameAndSubscription.endsWith("GOLD")) {
            return new Object[]{usernameAndSubscription.substring(0, usernameAndSubscription.length() - GOLD.length()), Status.GOLD};
        } else return new Object[] {null, null};
    }

}
