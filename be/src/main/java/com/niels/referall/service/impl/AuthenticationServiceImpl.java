package com.niels.referall.service.impl;

import com.niels.referall.entity.UserAccount;
import com.niels.referall.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UUID getAuthenticatedUser(Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        return user.getId();
    }

}
