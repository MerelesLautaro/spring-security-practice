package com.lautadev.spring_security_practice.services.authentication.impl;

import com.lautadev.spring_security_practice.dto.request.authentication.LoginRequest;
import com.lautadev.spring_security_practice.dto.request.authentication.UserRegisterRequest;
import com.lautadev.spring_security_practice.dto.response.authentication.Token;
import com.lautadev.spring_security_practice.dto.response.dashboard.UserDetailsResponse;
import com.lautadev.spring_security_practice.entities.User;
import com.lautadev.spring_security_practice.exceptions.ApiException;
import com.lautadev.spring_security_practice.repositories.IUserRepository;
import com.lautadev.spring_security_practice.security.JWTBlacklistManager;
import com.lautadev.spring_security_practice.services.authentication.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JWTBlacklistManager jwtBlacklistManager;

    @Override
    @Transactional
    public UserDetailsResponse registerUser(UserRegisterRequest userRegisterRequest) {
        System.out.println("Iniciando el registro de usuario...");

        validateEmailAndPhoneUniqueness(userRegisterRequest);

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.password());

        User user = User.builder()
                .name(userRegisterRequest.name())
                .address(userRegisterRequest.address())
                .email(userRegisterRequest.email())
                .password(encodedPassword)
                .phoneNumber(userRegisterRequest.phoneNumber())
                .build();

        System.out.println("Guardando usuario...");
        userRepository.save(user);  // Verificar si aquí es donde falla

        return createUserResponse(userRegisterRequest, encodedPassword);
    }

    private void validateEmailAndPhoneUniqueness(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByEmailIgnoreCase(userRegisterRequest.email())) {
            throw new ApiException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByPhoneNumber(userRegisterRequest.phoneNumber())) {
            throw new ApiException("Phone number already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private UserDetailsResponse createUserResponse(UserRegisterRequest userRegisterRequest,
                                                   String encodedPassword) {

        return new UserDetailsResponse(userRegisterRequest.name(),
                userRegisterRequest.email(), userRegisterRequest.address(), userRegisterRequest.phoneNumber(), encodedPassword);
    }

    @Override
    public Token login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.identifier(), loginRequest.password()));

        User userDetails = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(userDetails);

        return new Token(token);
    }

    @Override
    public void logout(String token) {
        jwtBlacklistManager.addTokenToBlackList(token);
    }
}
