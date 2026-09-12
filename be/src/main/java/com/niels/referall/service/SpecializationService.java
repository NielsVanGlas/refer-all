package com.niels.referall.service;

import com.niels.referall.dto.specialization.CreateSpecializationDto;
import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.dto.specialization.UpdateSpecializationDto;
import com.niels.referall.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpecializationService {

    List<Specialization> updateSpecialization(List<UpdateSpecializationDto> dtos);

    List<Specialization> getOrCreateSpecializations(List<CreateSpecializationDto> dtos);

    Page<ShowSpecializationDto> getSpecializations(Pageable pagination);
}
