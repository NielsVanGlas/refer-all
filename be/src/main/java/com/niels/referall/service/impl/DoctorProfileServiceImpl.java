package com.niels.referall.service.impl;

import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.doctorProfile.CreateDoctorProfileDto;
import com.niels.referall.dto.specialization.UpdateSpecializationDto;
import com.niels.referall.entity.DoctorProfile;
import com.niels.referall.entity.Specialization;
import com.niels.referall.factory.DoctorProfileFactory;
import com.niels.referall.repository.DoctorProfileRepository;
import com.niels.referall.service.DoctorProfileService;
import com.niels.referall.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.niels.referall.util.Constant.ERR_400_04;

@Service
public class DoctorProfileServiceImpl implements DoctorProfileService {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private SpecializationService specializationService;

    @Override
    public DoctorProfile getOrCreateDoctorProfile(CreateDoctorProfileDto dto) throws ValidationException {
        Optional<DoctorProfile> optionalDoctorProfile = doctorProfileRepository.findIfExist(dto.getLicenseNumber());
        List<Specialization> specializations = specializationService.getOrCreateSpecializations(dto.getSpecializations());
        if (optionalDoctorProfile.isPresent()) {
            throw new ValidationException(ERR_400_04, HttpStatus.BAD_REQUEST);
        }
        return DoctorProfileFactory.createDoctorProfile(dto, specializations, doctorProfileRepository);
    }

    @Override
    public void deleteDoctorProfile(UUID id) {
        doctorProfileRepository.deleteById(id);
    }

    @Override
    public DoctorProfile updateDoctorSpecializations(String licenseNumber, List<UpdateSpecializationDto> specializations) {
        DoctorProfile doctorProfile = doctorProfileRepository.findByLicenseNumber(licenseNumber);
        doctorProfile.setSpecializations(specializationService.updateSpecialization(specializations));
        return doctorProfileRepository.saveAndFlush(doctorProfile);
    }

}
