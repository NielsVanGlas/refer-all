package com.niels.referall.service;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface AuthenticationService {

    UUID getAuthenticatedUser(Authentication authentication);

}
