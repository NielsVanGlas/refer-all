package com.niels.referall.service.impl;

import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.medicalReport.CreateMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFileDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFullDto;
import com.niels.referall.entity.DeletedMedicalReportArchive;
import com.niels.referall.entity.MedicalReport;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.ActionType;
import com.niels.referall.enumerate.ReportStatus;
import com.niels.referall.factory.MedicalReportFactory;
import com.niels.referall.factory.ReportAccessLogFactory;
import com.niels.referall.repository.DeletedMedicalReportArchiveRepository;
import com.niels.referall.repository.MedicalReportRepository;
import com.niels.referall.repository.ReportAccessLogRepository;
import com.niels.referall.repository.UserAccountRepository;
import com.niels.referall.service.MedicalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.niels.referall.util.Constant.*;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Autowired
    private ReportAccessLogRepository reportAccessLogRepository;

    @Autowired
    private DeletedMedicalReportArchiveRepository deletedMedicalReportArchiveRepository;

    @Override
    public UUID createMedicalReport(CreateMedicalReportDto createMedicalReportDto, MultipartFile file, UUID authenticatedUser, String clientIp) throws Exception {

        Optional<UserAccount> optionalLoggedAccount = userAccountRepository.findById(authenticatedUser);

        if (optionalLoggedAccount.isPresent()) {
            UserAccount loggedAccount = optionalLoggedAccount.get();
            if (loggedAccount.getDoctorProfile() != null) {
                if (file == null || file.isEmpty()) {
                    throw new ValidationException(ERR_400_05, HttpStatus.BAD_REQUEST);
                }
                // 5Mb
                if (file.getSize() > 5 * 1024 * 1024) {
                    throw new ValidationException(ERR_400_06, HttpStatus.BAD_REQUEST);
                }
                Optional<UserAccount> optionalPatientAccount = userAccountRepository.findByTaxCode(createMedicalReportDto.getPatient());
                if (optionalPatientAccount.isPresent()) {
                    UUID medicalReportId = MedicalReportFactory.createMedicalReport(createMedicalReportDto, file, loggedAccount, optionalPatientAccount.get(), medicalReportRepository).getId();
                    ReportAccessLogFactory.createReportAccessLog(medicalReportId, loggedAccount, clientIp, ActionType.UPLOAD, reportAccessLogRepository);
                    return medicalReportId;
                } else {
                    throw new ValidationException(ERR_400_07, HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new ValidationException(ERR_401_01, HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ValidationException(ERR_401_01, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public Page<ShowMedicalReportDto> getMedicalReports(UUID authenticatedUser, String clientIp, Pageable pagination) throws ValidationException {
        Optional<UserAccount> optionalLoggedAccount = userAccountRepository.findById(authenticatedUser);
        if (optionalLoggedAccount.isPresent()) {
            UserAccount loggedAccount = optionalLoggedAccount.get();
            if (loggedAccount.getDoctorProfile() != null) {
                return medicalReportRepository.getAsDoctor(authenticatedUser, pagination);
            } else {
                return medicalReportRepository.getAsPatient(authenticatedUser, pagination);
            }
        } else {
            throw new ValidationException(ERR_401_01, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ShowMedicalReportFullDto getMedicalReport(UUID medicalReportId, UUID authenticatedUser, String clientIp) throws ValidationException {
        Optional<UserAccount> optionalLoggedAccount = userAccountRepository.findById(authenticatedUser);
        if (optionalLoggedAccount.isPresent()) {
            UserAccount loggedAccount = optionalLoggedAccount.get();
            MedicalReport medicalReport;
            if (loggedAccount.getDoctorProfile() != null) {
                medicalReport = medicalReportRepository.findByDoctorId(medicalReportId, authenticatedUser);
                ReportAccessLogFactory.createReportAccessLog(medicalReport.getId(), loggedAccount, clientIp, ActionType.INSPECT, reportAccessLogRepository);
            } else {
                medicalReport = medicalReportRepository.findByPatientId(medicalReportId, authenticatedUser);
                ReportAccessLogFactory.createReportAccessLog(medicalReport.getId(), loggedAccount, clientIp, ActionType.INSPECT, reportAccessLogRepository);
            }
            return new ShowMedicalReportFullDto(medicalReport);
        } else {
            throw new ValidationException(ERR_401_01, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void deleteMedicalReport(UUID authenticatedUser, UUID medicalReportId, String clientIp) {
        Optional<UserAccount> optionalLoggedAccount = userAccountRepository.findById(authenticatedUser);
        if (optionalLoggedAccount.isPresent()) {
            Optional<MedicalReport> optionalMedicalReport = medicalReportRepository.findById(medicalReportId);
            if (optionalMedicalReport.isPresent() && optionalMedicalReport.get().getDoctor().getId().equals(authenticatedUser)) {
                UUID archivedMedicalReportId = deletedMedicalReportArchiveRepository.saveAndFlush(new DeletedMedicalReportArchive(optionalMedicalReport.get())).getId();
                ReportAccessLogFactory.createReportAccessLog(archivedMedicalReportId, optionalLoggedAccount.get(), clientIp, ActionType.DELETE, reportAccessLogRepository);
                if (medicalReportRepository.selectByIdAndDoctor(medicalReportId, authenticatedUser).isPresent()) {
                    medicalReportRepository.deleteById(medicalReportId);
                }
            }
        }
    }

    @Override
    public ShowMedicalReportFileDto getMedicalReportForDownload(UUID medicalReportId, UUID authenticatedUser, String clientIp) throws ValidationException {
        Optional<UserAccount> optionalLoggedAccount = userAccountRepository.findById(authenticatedUser);
        if (optionalLoggedAccount.isPresent()) {
            UserAccount loggedAccount = optionalLoggedAccount.get();
            MedicalReport medicalReport;
            if (loggedAccount.getDoctorProfile() != null) {
                medicalReport = medicalReportRepository.findByDoctorId(medicalReportId, authenticatedUser);
                ReportAccessLogFactory.createReportAccessLog(medicalReport.getId(), loggedAccount, clientIp, ActionType.DOWNLOAD, reportAccessLogRepository);
            } else {
                medicalReport = medicalReportRepository.findByPatientId(medicalReportId, authenticatedUser);
                ReportAccessLogFactory.createReportAccessLog(loggedAccount.getId(), loggedAccount, clientIp, ActionType.DOWNLOAD, reportAccessLogRepository);
            }
            if (medicalReport.getPatient().getId().equals(authenticatedUser) || medicalReport.getPatient().getId().equals(medicalReport.getDoctor().getId())) {
                medicalReport.setReceivedAt(LocalDateTime.now());
                medicalReport.setStatus(ReportStatus.READ);
                medicalReportRepository.saveAndFlush(medicalReport);
            }
            return new ShowMedicalReportFileDto(medicalReport);
        } else {
            throw new ValidationException(ERR_401_01, HttpStatus.UNAUTHORIZED);
        }
    }
}

