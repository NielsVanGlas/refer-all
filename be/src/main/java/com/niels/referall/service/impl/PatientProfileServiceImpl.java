package com.niels.referall.service.impl;

import com.niels.referall.dto.patientProfile.CreatePatientProfileDto;
import com.niels.referall.dto.patientProfile.UpdatePatientProfileDto;
import com.niels.referall.entity.PatientProfile;
import com.niels.referall.factory.PatientProfileFactory;
import com.niels.referall.repository.PatientProfileRepository;
import com.niels.referall.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientProfileServiceImpl implements PatientProfileService {

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    @Override
    public PatientProfile createPatientProfile(CreatePatientProfileDto dto) {
        return patientProfileRepository.saveAndFlush(PatientProfileFactory.createPatientProfile(dto));
    }

    @Override
    public PatientProfile updatePatientProfile(PatientProfile patientProfile, UpdatePatientProfileDto dto) {
        patientProfile.setAllergies(dto.getAllergies());
        patientProfile.setChronicConditions(dto.getChronicConditions());
        return patientProfileRepository.saveAndFlush(patientProfile);
    }

    @Override
    public void deletePatientProfile(UUID id) {
        patientProfileRepository.deleteById(id);
    }

}
