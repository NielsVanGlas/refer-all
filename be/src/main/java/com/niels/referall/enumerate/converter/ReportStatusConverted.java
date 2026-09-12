package com.niels.referall.enumerate.converter;

import com.niels.referall.enumerate.ReportStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReportStatusConverted implements AttributeConverter<ReportStatus, String> {

    @Override
    public String convertToDatabaseColumn(ReportStatus val) {
        return val == null ? null : val.getValue();
    }

    @Override
    public ReportStatus convertToEntityAttribute(String dbData) {
        return ReportStatus.fromValue(dbData);
    }


}