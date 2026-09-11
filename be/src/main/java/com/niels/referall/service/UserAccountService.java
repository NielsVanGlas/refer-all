package com.niels.referall.service;

import com.niels.referall.config.exception.BaseException;
import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.userAccount.CreateUserAccountDto;
import com.niels.referall.dto.userAccount.ShowUserAccountDto;
import com.niels.referall.dto.userAccount.UpdateUserAccountDto;
import com.niels.referall.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountService extends UserDetailsService {

    UUID createUserAccount(CreateUserAccountDto createUserAccountDto) throws ValidationException;

    ShowUserAccountDto getUserAccount(UUID authenticatedUser) throws BaseException;

    UUID updateUserAccount(UUID authenticatedUser, UpdateUserAccountDto updateUserAccountDto) throws BaseException, ValidationException;

    void deleteUserAccount(UUID authenticatedUser) throws BaseException;

    Optional<UserAccount> findById(UUID id);

    void enableAccount(UserAccount userAccount);
}
