package com.niels.referall.dto.medicalReport;

public class CreateMedicalReportDto {

    private String patient;

    private String title;

    private String notes;

    public CreateMedicalReportDto() {
    }

    public CreateMedicalReportDto(String patient, String title, String notes) {
        this.patient = patient;
        this.title = title;
        this.notes = notes;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
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
