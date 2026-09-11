package com.niels.referall.factory;

import com.niels.referall.dto.patientProfile.CreatePatientProfileDto;
import com.niels.referall.dto.patientProfile.ShowPatientProfileDto;
import com.niels.referall.entity.PatientProfile;

public class PatientProfileFactory {
    public static PatientProfile createPatientProfile(CreatePatientProfileDto dto) {
        PatientProfile patient = new PatientProfile();
        patient.setBloodType(dto.getBloodType());
        patient.setAllergies(dto.getAllergies() != null ? dto.getAllergies() : "");
        patient.setChronicConditions(dto.getChronicConditions() != null ? dto.getChronicConditions() : "");
        return patient;
    }

    public static ShowPatientProfileDto showPatientProfileDto(PatientProfile entity) {
        return new ShowPatientProfileDto(
                entity.getBloodType(),
                entity.getAllergies(),
                entity.getChronicConditions()
        );
    }
}
