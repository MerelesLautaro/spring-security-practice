package com.lautadev.spring_security_practice.services.authentication;

import com.lautadev.spring_security_practice.dto.request.authentication.LoginRequest;
import com.lautadev.spring_security_practice.dto.request.authentication.UserRegisterRequest;
import com.lautadev.spring_security_practice.dto.response.authentication.Token;
import com.lautadev.spring_security_practice.dto.response.dashboard.UserDetailsResponse;

public interface IAuthenticationService {
    UserDetailsResponse registerUser(UserRegisterRequest userRegisterRequest);

    Token login(LoginRequest loginRequest);

    void logout(String token);
}
