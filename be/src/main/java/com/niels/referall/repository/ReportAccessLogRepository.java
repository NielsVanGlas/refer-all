package com.niels.referall.repository;

import com.niels.referall.dto.log.ShowReportAccessLogDto;
import com.niels.referall.entity.ReportAccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportAccessLogRepository extends JpaRepository<ReportAccessLog, UUID> {
    @Query(value = "SELECT new com.niels.referall.dto.log.ShowReportAccessLogDto(ral.userAccount.firstName, ral.userAccount.lastName, ral.userAccount.documentType, ral.userAccount.documentId, ral.action, ral.ipAddress) FROM ReportAccessLog ral")
    Page<ShowReportAccessLogDto> findAllLogs(Pageable pagination);
}
