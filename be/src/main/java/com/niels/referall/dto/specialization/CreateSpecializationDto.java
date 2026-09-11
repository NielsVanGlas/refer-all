package com.niels.referall.dto.specialization;

public class CreateSpecializationDto {

    private String specialization;

    public CreateSpecializationDto() {
    }

    public CreateSpecializationDto(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
