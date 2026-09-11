package com.niels.referall.service;

import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.doctorProfile.CreateDoctorProfileDto;
import com.niels.referall.entity.DoctorProfile;

import java.util.UUID;

public interface DoctorProfileService {
    DoctorProfile getOrCreateDoctorProfile(CreateDoctorProfileDto dto) throws ValidationException;

    void deleteDoctorProfile(UUID id);
}
