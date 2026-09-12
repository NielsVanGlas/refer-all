package com.niels.referall.dto.medicalReport;

import com.niels.referall.enumerate.ReportStatus;

import java.util.UUID;

public class CreateMedicalReportDto {

    private UUID patient;

    private UUID doctor;

    private String type;

    private String title;

    private String notes;

    public CreateMedicalReportDto() {
    }

    public CreateMedicalReportDto(UUID patient, UUID doctor, String type, ReportStatus status, String title, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.type = type;
        this.title = title;
        this.notes = notes;
    }

    public UUID getPatient() {
        return patient;
    }

    public void setPatient(UUID patient) {
        this.patient = patient;
    }

    public UUID getDoctor() {
        return doctor;
    }

    public void setDoctor(UUID doctor) {
        this.doctor = doctor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
