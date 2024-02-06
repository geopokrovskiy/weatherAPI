package com.geopokrovskiy.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface IApiService {
    String generateApiKey(String username);
    String restoreUsername(String apiKey);
}
