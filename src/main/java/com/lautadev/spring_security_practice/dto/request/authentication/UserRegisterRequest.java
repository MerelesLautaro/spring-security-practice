package com.lautadev.spring_security_practice.dto.request.authentication;

import com.lautadev.spring_security_practice.utils.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegisterRequest(@NotBlank String name,
                                  @Password String password,
                                  @NotBlank @Email String email,
                                  @NotBlank String address,
                                  @NotBlank @Pattern(regexp = "\\d+") String phoneNumber) {
}

