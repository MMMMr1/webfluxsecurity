package com.michalenok.webfluxsecurity.service;

import com.michalenok.webfluxsecurity.entity.UserEntity;
import com.michalenok.webfluxsecurity.entity.UserRole;
import com.michalenok.webfluxsecurity.repository.UserRepository;
import com.michalenok.webfluxsecurity.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user){
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(UserRole.USER)
                        .enabled(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    public Mono<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
