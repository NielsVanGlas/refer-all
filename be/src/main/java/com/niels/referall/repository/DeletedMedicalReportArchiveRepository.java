package com.niels.referall.repository;

import com.niels.referall.entity.DeletedMedicalReportArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeletedMedicalReportArchiveRepository extends JpaRepository<DeletedMedicalReportArchive, UUID> {

}
