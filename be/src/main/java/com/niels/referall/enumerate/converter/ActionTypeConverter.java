package com.niels.referall.enumerate.converter;

import com.niels.referall.enumerate.ActionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActionTypeConverter implements AttributeConverter<ActionType, String> {

    @Override
    public String convertToDatabaseColumn(ActionType val) {
        return val == null ? null : val.getValue();
    }

    @Override
    public ActionType convertToEntityAttribute(String dbData) {
        return ActionType.fromValue(dbData);
    }


}
