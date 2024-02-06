package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.Status;
import com.geopokrovskiy.exception.ApiException;
import com.geopokrovskiy.exception.ApiKeyException;
import com.geopokrovskiy.exception.ErrorCodes;
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
    public String generateApiKey(String username) {
        String rawKey = username + apiKeySecret;
        return Base64.getEncoder().encodeToString(rawKey.getBytes());
    }
    @Override
    public String restoreUsername(String apiKey) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(apiKey);
            String rawKey = new String(decodedKey);
            return rawKey.substring(0, rawKey.length() - apiKeySecret.length());
        } catch (Throwable e){
            throw new ApiKeyException("Incorrect API Key", ErrorCodes.INCORRECT_API_KEY);
        }
    }

}
