package com.niels.referall.dto.specialization;

public class UpdateSpecializationDto {

    private String specialization;

    public UpdateSpecializationDto() {
    }

    public UpdateSpecializationDto(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
