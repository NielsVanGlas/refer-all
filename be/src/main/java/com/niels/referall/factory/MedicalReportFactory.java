package com.niels.referall.factory;

import com.niels.referall.dto.medicalReport.CreateMedicalReportDto;
import com.niels.referall.entity.MedicalReport;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.ReportStatus;
import com.niels.referall.repository.MedicalReportRepository;
import com.niels.referall.util.Common;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public class MedicalReportFactory {

    public static MedicalReport createMedicalReport(CreateMedicalReportDto dto, MultipartFile file, UserAccount loggedAccount, UserAccount patientAccount, MedicalReportRepository medicalReportRepository) throws IOException {
        byte[] content = file.getBytes();
        return medicalReportRepository.saveAndFlush(new MedicalReport(
                patientAccount,
                loggedAccount,
                ReportStatus.NEW,
                dto.getTitle(),
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())),
                file.getContentType(),
                file.getSize(),
                content,
                Common.calculateSha256(content),
                dto.getNotes()
        ));
    }

}
