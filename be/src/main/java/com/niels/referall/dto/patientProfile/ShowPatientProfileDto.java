package com.niels.referall.dto.patientProfile;

import com.niels.referall.enumerate.BloodType;

public class ShowPatientProfileDto {

    private BloodType bloodType;

    private String allergies;

    private String chronicConditions;

    public ShowPatientProfileDto() {
    }

    public ShowPatientProfileDto(BloodType bloodType, String allergies, String chronicConditions) {
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }
}
