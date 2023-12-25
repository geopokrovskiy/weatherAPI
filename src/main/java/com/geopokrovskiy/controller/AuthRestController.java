package com.geopokrovskiy.controller;

import com.geopokrovskiy.dto.AuthRequestDto;
import com.geopokrovskiy.dto.AuthResponseDto;
import com.geopokrovskiy.dto.UserDto;
import com.geopokrovskiy.entity.UserEntity;
import com.geopokrovskiy.mapper.UserMapper;
import com.geopokrovskiy.security.CustomPrincipal;
import com.geopokrovskiy.security.SecurityService;
import com.geopokrovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/register")
public class AuthRestController {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/basic")
    public Mono<UserDto> registerBasicUser(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerBasicUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/silver")
    public Mono<UserDto> registerSilverUser(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerSilverUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/gold")
    public Mono<UserDto> registerGoldUser(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerGoldUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/get-api-key")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }
}
