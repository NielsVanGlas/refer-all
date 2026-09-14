package com.niels.referall.service.impl;

import com.niels.referall.config.exception.BaseException;
import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.doctorProfile.CreateDoctorProfileDto;
import com.niels.referall.dto.userAccount.CreateUserAccountDto;
import com.niels.referall.dto.userAccount.ShowUserAccountDto;
import com.niels.referall.dto.userAccount.UpdateUserAccountDto;
import com.niels.referall.entity.Address;
import com.niels.referall.entity.DoctorProfile;
import com.niels.referall.entity.PatientProfile;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.factory.UserAccountFactory;
import com.niels.referall.repository.UserAccountRepository;
import com.niels.referall.service.AddressService;
import com.niels.referall.service.DoctorProfileService;
import com.niels.referall.service.PatientProfileService;
import com.niels.referall.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.niels.referall.util.Constant.*;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DoctorProfileService doctorProfileService;

    @Autowired
    private PatientProfileService patientProfileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UUID createUserAccount(CreateUserAccountDto createUserAccountDto) throws ValidationException {

        if (userAccountRepository.findByTaxCode(createUserAccountDto.getTaxCode()).isPresent()) {
            throw new ValidationException(ERR_400_01, HttpStatus.BAD_REQUEST);
        }

        if (userAccountRepository.findByEmail(createUserAccountDto.getEmail()).isPresent()) {
            throw new ValidationException(ERR_400_02, HttpStatus.BAD_REQUEST);
        }

        if (userAccountRepository.findByDocumentTypeAndDocumentId(createUserAccountDto.getDocumentType(), createUserAccountDto.getDocumentId()).isPresent()) {
            throw new ValidationException(ERR_400_03, HttpStatus.BAD_REQUEST);
        }

        Address residence = addressService.getOrCreateAddress(createUserAccountDto.getResidence());
        Address home = addressService.getOrCreateAddress(createUserAccountDto.getHome());

        CreateDoctorProfileDto doctorProfileDto = createUserAccountDto.getDoctorProfile();

        PatientProfile patientProfile = patientProfileService.createPatientProfile(createUserAccountDto.getPatientProfile());

        UserAccount userAccount;
        if (doctorProfileDto != null) {
            DoctorProfile doctorProfile = doctorProfileService.getOrCreateDoctorProfile(createUserAccountDto.getDoctorProfile());
            userAccount = userAccountRepository.saveAndFlush(UserAccountFactory.createDoctorAccount(createUserAccountDto, residence, home, doctorProfile, patientProfile, passwordEncoder));
        } else {
            userAccount = userAccountRepository.saveAndFlush(UserAccountFactory.createPatientAccount(createUserAccountDto, residence, home, patientProfile, passwordEncoder));
        }
        return userAccount.getId();

    }

    @Override
    public ShowUserAccountDto getUserAccount(UUID authenticatedUser) throws BaseException {
        return UserAccountFactory.showUserAccountDto(userAccountRepository.findById(authenticatedUser).orElseThrow(() -> new BaseException(ERR_404_01, HttpStatus.NOT_FOUND)));
    }

    @Override
    public UUID updateUserAccount(UUID authenticatedUser, UpdateUserAccountDto updateUserAccountDto) throws BaseException, ValidationException {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(authenticatedUser);
        if (optionalUserAccount.isEmpty()) {
            throw new BaseException(ERR_404_01, HttpStatus.NOT_FOUND);
        }
        Optional<UserAccount> userAccountEmail = userAccountRepository.findByEmail(updateUserAccountDto.getEmail());

        if (userAccountEmail.isPresent() && !userAccountEmail.get().getId().equals(authenticatedUser)) {
            throw new ValidationException(ERR_400_02, HttpStatus.BAD_REQUEST);
        }

        Optional<UserAccount> userAccountDocument = userAccountRepository.findByDocumentTypeAndDocumentId(updateUserAccountDto.getDocumentType(), updateUserAccountDto.getDocumentId());
        if (userAccountDocument.isPresent() && !userAccountDocument.get().getId().equals(authenticatedUser)) {
            throw new ValidationException(ERR_400_03, HttpStatus.BAD_REQUEST);
        }

        Address residence = addressService.getOrUpdateAddress(updateUserAccountDto.getResidence());
        Address home = updateUserAccountDto.getHome() != null
                ? addressService.getOrUpdateAddress(updateUserAccountDto.getHome())
                : residence;

        UserAccount userAccount = optionalUserAccount.get();
        DoctorProfile doctorProfile = userAccount.getDoctorProfile();
        PatientProfile patientProfile = userAccount.getPatientProfile();

        if (doctorProfile != null) {
            doctorProfile = doctorProfileService.updateDoctorSpecializations(doctorProfile.getLicenseNumber(), updateUserAccountDto.getSpecializations());
        }

        patientProfile = patientProfileService.updatePatientProfile(patientProfile, updateUserAccountDto.getPatientProfile());

        return userAccountRepository.saveAndFlush(UserAccountFactory.updateUserAccount(optionalUserAccount.get(), updateUserAccountDto, residence, home, patientProfile, doctorProfile, passwordEncoder)).getId();
    }

    @Override
    public void deleteUserAccount(UUID authenticatedUser) throws BaseException {
        UserAccount userAccount = userAccountRepository.findById(authenticatedUser).orElseThrow(() -> new BaseException(ERR_404_01, HttpStatus.NOT_FOUND));
        userAccountRepository.deleteById(userAccount.getId());
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserAccount> optionalUserAccount = UUID_REGEX.matcher(name).matches()
                ? userAccountRepository.findById(UUID.fromString(name))
                : userAccountRepository.findByEmail(name);
        return optionalUserAccount.orElseThrow(() -> new UsernameNotFoundException(name + ": User not found"));
    }

    @Override
    public void enableAccount(UserAccount userAccount) {
        userAccount.setEnabled(true);
        userAccountRepository.saveAndFlush(userAccount);
    }

}
