package com.niels.referall.factory;

import com.niels.referall.dto.userAccount.CreateUserAccountDto;
import com.niels.referall.dto.userAccount.ShowUserAccountDto;
import com.niels.referall.dto.userAccount.UpdateUserAccountDto;
import com.niels.referall.entity.Address;
import com.niels.referall.entity.DoctorProfile;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAccountFactory {

    public static UserAccount createUserAccount(CreateUserAccountDto dto, Address residence, Address home, DoctorProfile doctorProfile, PasswordEncoder passwordEncoder) {

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
                doctorProfile
        );

    }

    public static ShowUserAccountDto showUserAccountDto(UserAccount entity) {

        return new ShowUserAccountDto(
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
                DoctorProfileFactory.showDoctorProfileDto(entity.getDoctorProfile())
        );

    }

    public static UserAccount updateUserAccount(UserAccount entity, UpdateUserAccountDto dto, Address residence, Address home, DoctorProfile doctorProfile, PasswordEncoder passwordEncoder) {
        if (dto.getPassword() != null ){
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        entity.setResidence(residence);
        entity.setHome(home);
        entity.setMarketingConsensus(dto.isMarketingConsensus());
        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentId(dto.getDocumentId());
        entity.setDoctorProfile(doctorProfile);
        return entity;
    }
}
