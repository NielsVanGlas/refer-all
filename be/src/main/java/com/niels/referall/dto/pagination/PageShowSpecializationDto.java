package com.niels.referall.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.referall.dto.specialization.ShowSpecializationDto;

import java.util.List;

public class PageShowSpecializationDto extends PageDto {

    private List<ShowSpecializationDto> item;

    public PageShowSpecializationDto(List<ShowSpecializationDto> showSpecializationDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showSpecializationDto;
    }

    @JsonProperty("item")
    public List<ShowSpecializationDto> getItem() {
        return item;
    }

    public void setItem(List<ShowSpecializationDto> item) {
        this.item = item;
    }


}
