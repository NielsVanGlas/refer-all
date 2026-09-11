package com.niels.referall.service;

import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.doctorProfile.CreateDoctorProfileDto;
import com.niels.referall.dto.specialization.UpdateSpecializationDto;
import com.niels.referall.entity.DoctorProfile;

import java.util.List;
import java.util.UUID;

public interface DoctorProfileService {
    DoctorProfile getOrCreateDoctorProfile(CreateDoctorProfileDto dto) throws ValidationException;

    void deleteDoctorProfile(UUID id);

    DoctorProfile updateDoctorSpecializations(String licenseNumber, List<UpdateSpecializationDto> specializations);
}
