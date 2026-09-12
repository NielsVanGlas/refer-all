package com.niels.referall.repository;

import com.niels.referall.dto.medicalReport.ShowMedicalReportDto;
import com.niels.referall.entity.MedicalReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, UUID> {

    void deleteByPatientAndDoctor(UUID patientId, UUID doctorId);

    @Query(value = "SELECT new com.niels.referall.dto.medicalReport.ShowMedicalReportDto(mr.patient.firstName, mr.patient.lastName, mr.doctor.firstName, mr.doctor.lastName, mr.status, mr.title, mr.receivedAt, mr.id, mr.notes) FROM MedicalReport mr WHERE mr.doctor.id=?1")
    Page<ShowMedicalReportDto> getAsDoctor(UUID authenticatedUser, Pageable pagination);

    @Query(value = "SELECT new com.niels.referall.dto.medicalReport.ShowMedicalReportDto(mr.patient.firstName, mr.patient.lastName, mr.doctor.firstName, mr.doctor.lastName, mr.status, mr.title, mr.receivedAt, mr.id, mr.notes) FROM MedicalReport mr WHERE mr.patient.id=?1")
    Page<ShowMedicalReportDto> getAsPatient(UUID authenticatedUser, Pageable pagination);

    @Query(value = "SELECT mr FROM MedicalReport mr WHERE mr.id = ?1 AND mr.doctor.id=?2")
    MedicalReport findByDoctorId(UUID id, UUID authenticatedUser);

    @Query(value = "SELECT mr FROM MedicalReport mr WHERE mr.id = ?1 AND mr.patient.id=?2")
    MedicalReport findByPatientId(UUID id, UUID authenticatedUser);

}
