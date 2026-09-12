package com.niels.referall.factory;

import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.entity.Specialization;

import java.util.List;
import java.util.stream.Collectors;

public class SpecializationFactory {

    public static List<ShowSpecializationDto> showSpecializationsDto(List<Specialization> entities) {
        return entities.stream()
                .map(entity -> new ShowSpecializationDto(
                        entity.getSpecialization()
                )).collect(Collectors.toList());
    }

}
