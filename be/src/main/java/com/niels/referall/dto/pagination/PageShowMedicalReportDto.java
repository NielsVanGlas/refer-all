package com.niels.referall.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.referall.dto.medicalReport.ShowMedicalReportDto;

import java.util.List;

public class PageShowMedicalReportDto extends PageDto {

    private List<ShowMedicalReportDto> item;

    public PageShowMedicalReportDto(List<ShowMedicalReportDto> showMedicalReportDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showMedicalReportDto;
    }

    @JsonProperty("item")
    public List<ShowMedicalReportDto> getItem() {
        return item;
    }

    public void setItem(List<ShowMedicalReportDto> item) {
        this.item = item;
    }


}
