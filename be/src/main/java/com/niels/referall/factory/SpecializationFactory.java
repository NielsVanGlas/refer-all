package com.niels.referall.factory;

import com.niels.referall.dto.specialization.CreateSpecializationDto;
import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.entity.Specialization;
import com.niels.referall.repository.SpecializationRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SpecializationFactory {

    public static Specialization createSpecialization(CreateSpecializationDto dto, SpecializationRepository specializationRepository) {
        return specializationRepository.saveAndFlush(new Specialization(
                dto.getSpecialization()
        ));
    }

    public static List<ShowSpecializationDto> showSpecializationsDto(List<Specialization> entities) {
        return entities.stream()
                .map(entity -> new ShowSpecializationDto(
                        entity.getSpecialization()
                )).collect(Collectors.toList());
    }

}
