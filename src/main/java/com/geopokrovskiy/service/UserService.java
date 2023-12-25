package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.Status;
import com.geopokrovskiy.entity.UserEntity;
import com.geopokrovskiy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiService apiService;
    public Mono<UserEntity> registerBasicUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .status(Status.BASIC)
                        .apiKey(apiService.generateBasicApiKey(user.getUsername()))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - BASIC user: {} created", u);
        });
    }

    public Mono<UserEntity> registerSilverUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .apiKey(apiService.generateSilverApiKey(user.getUsername()))
                        .status(Status.SILVER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - SILVER user: {} created", u);
        });
    }

    public Mono<UserEntity> registerGoldUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .apiKey(apiService.generateGoldApiKey(user.getUsername()))
                        .status(Status.GOLD)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - GOLD user: {} created", u);
        });
    }
    public Mono<UserEntity> getUserById(Long id){
        return this.userRepository.findById(id);
    }
    public Mono<UserEntity> getUserByUsername(String username){
        return this.userRepository.findByUsername(username);
    }





}
