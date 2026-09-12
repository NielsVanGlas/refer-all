package com.niels.referall.factory;

import com.niels.referall.dto.userAccount.CreateUserAccountDto;
import com.niels.referall.dto.userAccount.ShowUserAccountDto;
import com.niels.referall.dto.userAccount.UpdateUserAccountDto;
import com.niels.referall.entity.Address;
import com.niels.referall.entity.DoctorProfile;
import com.niels.referall.entity.PatientProfile;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAccountFactory {

    public static UserAccount createPatientAccount(CreateUserAccountDto dto, Address residence, Address home, PatientProfile patientProfile, PasswordEncoder passwordEncoder) {

        return new UserAccount(
                true,
                passwordEncoder.encode(dto.getPassword()),
                Role.USER,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getBornDate(),
                dto.getBirthCity(),
                dto.getBirthProvinceCode(),
                dto.getBirthZipCode(),
                dto.getTaxCode(),
                dto.getEmail(),
                dto.getMobile(),
                residence,
                home,
                dto.isMarketingConsensus(),
                dto.isServiceTermsAndConditions(),
                dto.getDocumentType(),
                dto.getDocumentId(),
                patientProfile
        );

    }

    public static UserAccount createDoctorAccount(CreateUserAccountDto dto, Address residence, Address home, DoctorProfile doctorProfile, PatientProfile patientProfile, PasswordEncoder passwordEncoder) {

        return new UserAccount(
                true,
                passwordEncoder.encode(dto.getPassword()),
                Role.ADMIN,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getBornDate(),
                dto.getBirthCity(),
                dto.getBirthProvinceCode(),
                dto.getBirthZipCode(),
                dto.getTaxCode(),
                dto.getEmail(),
                dto.getMobile(),
                residence,
                home,
                dto.isMarketingConsensus(),
                dto.isServiceTermsAndConditions(),
                dto.getDocumentType(),
                dto.getDocumentId(),
                doctorProfile,
                patientProfile
        );

    }

    public static ShowUserAccountDto showUserAccountDto(UserAccount entity) {
        ShowUserAccountDto showUserAccount = new ShowUserAccountDto(
                entity.isEnabled(),
                entity.getRole(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getBornDate(),
                entity.getBirthCity(),
                entity.getBirthProvinceCode(),
                entity.getBirthZipCode(),
                entity.getTaxCode(),
                entity.getEmail(),
                entity.getMobile(),
                AddressFactory.showAddressDto(entity.getResidence()),
                AddressFactory.showAddressDto(entity.getHome()),
                entity.isMarketingConsensus(),
                entity.isServiceTermsAndConditions(),
                entity.getDocumentType(),
                entity.getDocumentId(),
                PatientProfileFactory.showPatientProfileDto(entity.getPatientProfile())
        );
        if (entity.getDoctorProfile() != null) {
            showUserAccount.setDoctorProfile(DoctorProfileFactory.showDoctorProfileDto(entity.getDoctorProfile()));
        }
        return showUserAccount;

    }

    public static UserAccount updateUserAccount(UserAccount entity, UpdateUserAccountDto dto, Address residence, Address home, PatientProfile patientProfile, DoctorProfile doctorProfile, PasswordEncoder passwordEncoder) {
        if (dto.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        entity.setResidence(residence);
        entity.setHome(home);
        entity.setMarketingConsensus(dto.isMarketingConsensus());
        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentId(dto.getDocumentId());
        if (doctorProfile != null) {
            entity.setDoctorProfile(doctorProfile);
        }
        entity.setPatientProfile(patientProfile);
        return entity;
    }
}
