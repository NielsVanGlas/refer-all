package com.niels.referall.service.impl;

import com.niels.referall.dto.error.ShowErrorTracking;
import com.niels.referall.repository.ErrorTrackingRepository;
import com.niels.referall.service.ErrorTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ErrorTrackingServiceImpl implements ErrorTrackingService {

    @Autowired
    private ErrorTrackingRepository errorTrackingRepository;

    @Override
    public Page<ShowErrorTracking> getErrors(Pageable pagination) {
        return errorTrackingRepository.findAllErrors(pagination);
    }
}
