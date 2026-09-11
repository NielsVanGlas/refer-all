package com.niels.referall.dto.doctorProfile;

import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.entity.Specialization;
import com.niels.referall.factory.SpecializationFactory;

import java.util.List;

public class ShowDoctorProfileDto {

    private String licenseNumber;

    private List<ShowSpecializationDto> specializations;

    public ShowDoctorProfileDto() {
    }

    public ShowDoctorProfileDto(String licenseNumber, List<Specialization> specializations) {
        this.licenseNumber = licenseNumber;
        this.specializations = SpecializationFactory.showSpecializationsDto(specializations);
    }

    public String getlicenseNumber() {
        return licenseNumber;
    }

    public void setlicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<ShowSpecializationDto> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<ShowSpecializationDto> specializations) {
        this.specializations = specializations;
    }

}
