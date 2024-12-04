package com.lautadev.spring_security_practice.controller.authentication;

import com.lautadev.spring_security_practice.dto.request.authentication.LoginRequest;
import com.lautadev.spring_security_practice.dto.request.authentication.UserRegisterRequest;
import com.lautadev.spring_security_practice.dto.response.authentication.Token;
import com.lautadev.spring_security_practice.dto.response.dashboard.UserDetailsResponse;
import com.lautadev.spring_security_practice.services.authentication.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsResponse> registerUser(@RequestBody
                                                            UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authenticationService.registerUser(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid
                                       LoginRequest loginRequest) {


        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                       String authorization) {

        authenticationService.logout(authorization);
        return ResponseEntity.ok().build();
    }
}
