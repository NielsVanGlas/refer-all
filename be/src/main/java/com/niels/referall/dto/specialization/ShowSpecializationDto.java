package com.niels.referall.dto.specialization;

public class ShowSpecializationDto {

    private String specialization;

    public ShowSpecializationDto() {
    }

    public ShowSpecializationDto(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
