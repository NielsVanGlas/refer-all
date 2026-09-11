package com.niels.referall.dto.doctorProfile;

import com.niels.referall.dto.specialization.CreateSpecializationDto;

import java.util.List;

public class CreateDoctorProfileDto {

    private String licenseNumber;

    private List<CreateSpecializationDto> specializations;

    public CreateDoctorProfileDto() {
    }

    public CreateDoctorProfileDto(String licenseNumber, List<CreateSpecializationDto> specializations) {
        this.licenseNumber = licenseNumber;
        this.specializations = specializations;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<CreateSpecializationDto> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<CreateSpecializationDto> specializations) {
        this.specializations = specializations;
    }

}
