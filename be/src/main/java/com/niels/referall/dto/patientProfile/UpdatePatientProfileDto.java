package com.niels.referall.dto.patientProfile;

public class UpdatePatientProfileDto {

    private String allergies;

    private String chronicConditions;

    public UpdatePatientProfileDto() {
    }

    public UpdatePatientProfileDto(String allergies, String chronicConditions) {
        this.allergies = allergies;
        this.chronicConditions = chronicConditions;
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
