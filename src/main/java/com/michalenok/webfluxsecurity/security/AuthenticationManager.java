package com.michalenok.webfluxsecurity.security;

import com.michalenok.webfluxsecurity.entity.UserEntity;
import com.michalenok.webfluxsecurity.exception.UnauthorizedException;
import com.michalenok.webfluxsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final UserRepository userRepository;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userRepository.findById(principal.getId())
                .filter(UserEntity::isEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User disabled")))
                .map(userEntity -> authentication);
    }
}
