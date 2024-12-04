package com.lautadev.spring_security_practice.security;

import com.lautadev.spring_security_practice.services.authentication.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JWTBlacklistManager {

    private final ITokenService tokenService;
    private final Set<String> tokenBlacklist;

    public JWTBlacklistManager(ITokenService tokenService) {
        this.tokenService = tokenService;
        tokenBlacklist = ConcurrentHashMap.newKeySet();
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void cleanBlackList() {
        log.info("Deleting expired tokens from black list");

        tokenBlacklist.stream()
                .filter(tokenService::isTokenExpired)
                .forEach(tokenBlacklist::remove);
    }

    public void addTokenToBlackList(String token) {
        tokenBlacklist.add(token);
    }

    public boolean isBlackListed(String token) {
        return tokenBlacklist.contains(token);
    }
}
