package com.niels.referall.enumerate.converter;

import com.niels.referall.enumerate.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role val) {
        return val==null?null:val.getValue();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.fromValue(dbData);
    }

}
