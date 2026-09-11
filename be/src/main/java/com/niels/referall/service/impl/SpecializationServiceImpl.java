package com.niels.referall.service.impl;

import com.niels.referall.dto.specialization.CreateSpecializationDto;
import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.dto.specialization.UpdateSpecializationDto;
import com.niels.referall.entity.Specialization;
import com.niels.referall.factory.SpecializationFactory;
import com.niels.referall.repository.SpecializationRepository;
import com.niels.referall.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public List<Specialization> updateSpecialization(List<UpdateSpecializationDto> dtos) {
        HashMap<String, Specialization> specializationMap = new HashMap<>();
        specializationRepository.findAll().forEach(specialization -> {
            specializationMap.put(specialization.getSpecialization(), specialization);
        });
        List<Specialization> specializations = new ArrayList<>();
        dtos.forEach(specialization -> {
            String key = specialization.getSpecialization();
            specializations.add(specializationMap.containsKey(key) ? specializationMap.get(key) : specializationRepository.saveAndFlush(new Specialization(key)));
        });
        return specializations;
    }

    @Override
    public List<Specialization> getOrCreateSpecializations(List<CreateSpecializationDto> dtos) {
        HashMap<String, Specialization> specializationMap = new HashMap<>();
        specializationRepository.findAll().forEach(specialization -> {
            specializationMap.put(specialization.getSpecialization(), specialization);
        });
        List<Specialization> specializations = new ArrayList<>();
        dtos.forEach(specialization -> {
            String key = specialization.getSpecialization();
            specializations.add(specializationMap.containsKey(key) ? specializationMap.get(key) : specializationRepository.saveAndFlush(new Specialization(key)));
        });
        return specializations;
    }

    @Override
    public UUID createSpecialization(CreateSpecializationDto dto) {
        return SpecializationFactory.createSpecialization(dto, specializationRepository).getId();
    }

    @Override
    public Page<ShowSpecializationDto> getSpecializations(Pageable pagination) {
        return specializationRepository.findAllSpecializations(pagination);
    }

}
