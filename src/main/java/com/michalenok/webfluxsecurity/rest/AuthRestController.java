package com.michalenok.webfluxsecurity.rest;

import com.michalenok.webfluxsecurity.dto.AuthRequestDto;
import com.michalenok.webfluxsecurity.dto.AuthResponseDto;
import com.michalenok.webfluxsecurity.dto.UserDto;
import com.michalenok.webfluxsecurity.entity.UserEntity;
import com.michalenok.webfluxsecurity.mapper.UserMapper;
import com.michalenok.webfluxsecurity.security.CustomPrincipal;
import com.michalenok.webfluxsecurity.security.SecurityService;
import com.michalenok.webfluxsecurity.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto){
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @GetMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
        return securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }
    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId())
                .map(userMapper::map);
    }
}
