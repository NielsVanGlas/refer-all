package com.niels.referall.service;

import com.niels.referall.dto.log.ShowReportAccessLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReportAccessLogService {
    Page<ShowReportAccessLogDto> getReportAccessLogs(UUID authenticatedUser, Pageable pagination);
}

