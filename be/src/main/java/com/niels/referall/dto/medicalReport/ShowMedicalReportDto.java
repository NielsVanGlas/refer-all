package com.niels.referall.dto.medicalReport;

import com.niels.referall.enumerate.ReportStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShowMedicalReportDto {

    private String patient;

    private String doctor;

    private ReportStatus status;

    private String title;

    private LocalDateTime receivedAt;

    private UUID fileId;

    private String notes;

    public ShowMedicalReportDto() {
    }

    public ShowMedicalReportDto(String patientFirstName, String patientLastName, String doctorFirstName, String doctorLastName, ReportStatus status, String title, LocalDateTime receivedAt, UUID fileId, String notes) {
        this.patient = patientFirstName + " " + patientLastName;
        this.doctor = doctorFirstName + " " + doctorLastName;
        this.status = status;
        this.title = title;
        this.receivedAt = receivedAt;
        this.fileId = fileId;
        this.notes = notes;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
