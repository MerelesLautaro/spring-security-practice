package com.lautadev.spring_security_practice.services.customer.impl;

import com.lautadev.spring_security_practice.exceptions.ApiException;
import com.lautadev.spring_security_practice.repositories.IUserRepository;
import com.lautadev.spring_security_practice.services.customer.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, IUserService {

    private final IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new ApiException("User not found for the given identifier: " + username,
                                new UsernameNotFoundException(username), HttpStatus.BAD_REQUEST));
    }
}
