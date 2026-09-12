package com.niels.referall.enumerate.converter;

import com.niels.referall.enumerate.BloodType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BloodTypeConverter implements AttributeConverter<BloodType, String> {

    @Override
    public String convertToDatabaseColumn(BloodType val) {
        return val == null ? null : val.getValue();
    }

    @Override
    public BloodType convertToEntityAttribute(String dbData) {
        return BloodType.fromValue(dbData);
    }

}
