package com.geopokrovskiy.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface IApiService {
    String generateBasicApiKey(String username);
    String generateBasicApiKey(String username, LocalDateTime generatedAt);
    String generateSilverApiKey(String username);
    String generateSilverApiKey(String username, LocalDateTime generatedAt);
    String generateGoldApiKey(String username);
    String generateGoldApiKey(String username, LocalDateTime generatedAt);
    boolean basicMatches(String username, String apiKey);
    boolean silverMatches(String username, String apiKey);
    boolean goldMatches(String username, String apiKey);
    Object[] restoreUsernameAndSubscription(String apiKey);
}
