package com.lautadev.spring_security_practice.dto.request.authentication;

import com.lautadev.spring_security_practice.utils.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank @Email String identifier,
                           @Password String password) {
}
