package com.niels.referall.service;

import com.niels.referall.dto.patientProfile.CreatePatientProfileDto;
import com.niels.referall.dto.patientProfile.UpdatePatientProfileDto;
import com.niels.referall.entity.PatientProfile;

import java.util.UUID;

public interface PatientProfileService {
    PatientProfile createPatientProfile(CreatePatientProfileDto dto);

    PatientProfile updatePatientProfile(PatientProfile patientProfile, UpdatePatientProfileDto dto);

    void deletePatientProfile(UUID id);
}
