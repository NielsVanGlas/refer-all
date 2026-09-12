package com.niels.referall.service;

import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.medicalReport.CreateMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFileDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFullDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MedicalReportService {
    UUID createMedicalReport(CreateMedicalReportDto createMedicalReportDto, MultipartFile file, UUID authenticatedUser, String clientIp) throws Exception;

    Page<ShowMedicalReportDto> getMedicalReports(UUID authenticatedUser, String clientIp, Pageable pagination) throws ValidationException;

    ShowMedicalReportFullDto getMedicalReport(UUID medicalReportId, UUID authenticatedUser, String clientIp) throws ValidationException;

    void deleteMedicalReport(UUID authenticatedUser, UUID medicalReportId, String clientIp);

    ShowMedicalReportFileDto getMedicalReportForDownload(UUID id, UUID authenticatedUser, String remoteAddr) throws ValidationException;
}
