package com.niels.referall.enumerate.converter;

import com.niels.referall.enumerate.DocumentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {

    @Override
    public String convertToDatabaseColumn(DocumentType val) {
        return val == null ? null : val.getValue();
    }

    @Override
    public DocumentType convertToEntityAttribute(String dbData) {
        return DocumentType.fromValue(dbData);
    }

}
