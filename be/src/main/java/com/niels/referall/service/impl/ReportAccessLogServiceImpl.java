package com.niels.referall.service.impl;

import com.niels.referall.dto.log.ShowReportAccessLogDto;
import com.niels.referall.repository.ReportAccessLogRepository;
import com.niels.referall.service.ReportAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportAccessLogServiceImpl implements ReportAccessLogService {

    @Autowired
    private ReportAccessLogRepository reportAccessLogRepository;

    @Override
    public Page<ShowReportAccessLogDto> getReportAccessLogs(UUID authenticatedUser, Pageable pagination) {
        return reportAccessLogRepository.findAllLogs(pagination);
    }

}
