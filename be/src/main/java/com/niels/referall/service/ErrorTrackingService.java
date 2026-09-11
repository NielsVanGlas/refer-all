package com.niels.referall.service;

import com.niels.referall.dto.error.ShowErrorTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ErrorTrackingService {

    Page<ShowErrorTracking> getErrors(Pageable pagination);

}
