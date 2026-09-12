package com.niels.referall.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niels.referall.dto.log.ShowReportAccessLogDto;

import java.util.List;

public class PageShowReportAccessLogDto extends PageDto {

    private List<ShowReportAccessLogDto> item;

    public PageShowReportAccessLogDto(List<ShowReportAccessLogDto> showReportAccessLogDto, int currentPage, long totalItem, int totalPage) {
        super(currentPage, totalItem, totalPage);
        this.item = showReportAccessLogDto;
    }

    @JsonProperty("item")
    public List<ShowReportAccessLogDto> getItem() {
        return item;
    }

    public void setItem(List<ShowReportAccessLogDto> item) {
        this.item = item;
    }


}
