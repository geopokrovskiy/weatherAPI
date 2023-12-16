package com.geopokrovskiy.service;

import java.time.LocalDateTime;

public interface IApiService {
    String generateBasicApiKey(String username);
    String generateBasicApiKey(String username, LocalDateTime generatedAt);
    String generateSilverApiKey(String username);
    String generateSilverApiKey(String username, LocalDateTime generatedAt);
    String generateGoldApiKey(String username);
    String generateGoldApiKey(String username, LocalDateTime generatedAt);
}
