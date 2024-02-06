package com.geopokrovskiy.service;

import com.geopokrovskiy.entity.UserEntity;
import com.geopokrovskiy.repository.UserRedisRepository;
import com.geopokrovskiy.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiService apiService;

    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .status(user.getStatus())
                        .apiKey(apiService.generateApiKey(user.getUsername()))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).flatMap(savedUser ->
                userRedisRepository.addUserToRedis(savedUser).thenReturn(savedUser).
                        doOnSuccess(u -> log.info("IN registerUser - {} user: {} has been created", u.getStatus(), u))
        );
    }

    public Mono<UserEntity> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return this.userRedisRepository.getUserFromRedis(username)
                .switchIfEmpty(this.userRepository.findByUsername(username).flatMap(this::writeUserToRedis));
    }

    private Mono<UserEntity> writeUserToRedis(UserEntity user) {
        return userRedisRepository.addUserToRedis(user);
    }


}
