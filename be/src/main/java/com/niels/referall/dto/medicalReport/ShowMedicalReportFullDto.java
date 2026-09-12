package com.niels.referall.dto.medicalReport;

import com.niels.referall.entity.MedicalReport;
import com.niels.referall.enumerate.ReportStatus;

import java.time.LocalDateTime;

public class ShowMedicalReportFullDto {

    private String patient;

    private String patienTaxCode;

    private String doctor;

    private String doctorLicenseNumber;

    private ReportStatus status;

    private String title;

    private LocalDateTime receivedAt;

    private String notes;

    public ShowMedicalReportFullDto(MedicalReport medicalReport) {
        this.patient = medicalReport.getPatient().getFullName();
        this.patienTaxCode = medicalReport.getPatient().getTaxCode();
        this.doctor = medicalReport.getDoctor().getFullName();
        this.doctorLicenseNumber = medicalReport.getDoctor().getDoctorProfile().getLicenseNumber();
        this.status = medicalReport.getStatus();
        this.title = medicalReport.getTitle();
        this.receivedAt = medicalReport.getReceivedAt();
        this.notes = medicalReport.getNotes();
    }

    public ShowMedicalReportFullDto() {
    }

    public ShowMedicalReportFullDto(String patient, String patienTaxCode, String doctor, String doctorLicenseNumber, ReportStatus status, String title, LocalDateTime receivedAt, String notes) {
        this.patient = patient;
        this.patienTaxCode = patienTaxCode;
        this.doctor = doctor;
        this.doctorLicenseNumber = doctorLicenseNumber;
        this.status = status;
        this.title = title;
        this.receivedAt = receivedAt;
        this.notes = notes;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPatienTaxCode() {
        return patienTaxCode;
    }

    public void setPatienTaxCode(String patienTaxCode) {
        this.patienTaxCode = patienTaxCode;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorLicenseNumber() {
        return doctorLicenseNumber;
    }

    public void setDoctorLicenseNumber(String doctorLicenseNumber) {
        this.doctorLicenseNumber = doctorLicenseNumber;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
