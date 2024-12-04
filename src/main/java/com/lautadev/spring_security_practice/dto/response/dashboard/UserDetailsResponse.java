package com.lautadev.spring_security_practice.dto.response.dashboard;

public record UserDetailsResponse(String name,
                                  String email,
                                  String address,
                                  String phoneNumber,
                                  String hashedPassword) {
}
