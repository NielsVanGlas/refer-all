package com.niels.referall.factory;

import com.niels.referall.dto.doctorProfile.CreateDoctorProfileDto;
import com.niels.referall.dto.doctorProfile.ShowDoctorProfileDto;
import com.niels.referall.entity.DoctorProfile;
import com.niels.referall.entity.Specialization;
import com.niels.referall.repository.DoctorProfileRepository;

import java.util.List;

public class DoctorProfileFactory {

    public static DoctorProfile createDoctorProfile(CreateDoctorProfileDto dto, List<Specialization> specifications, DoctorProfileRepository doctorProfileRepository) {
        return doctorProfileRepository.saveAndFlush(new DoctorProfile(
                dto.getLicenseNumber(),
                specifications
        ));
    }

    public static ShowDoctorProfileDto showDoctorProfileDto(DoctorProfile entity) {
        return new ShowDoctorProfileDto(
                entity.getLicenseNumber(),
                entity.getSpecializations()
        );
    }

}
