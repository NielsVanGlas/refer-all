package com.niels.referall.entity;

import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.enumerate.ReportStatus;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class MedicalReport extends CommonEntity {

    private UserAccount patient;

    private UserAccount doctor;

    private ReportStatus status;

    private String title;

    private LocalDateTime receivedAt;

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private byte[] fileContent;

    private String sha256;

    private String notes;

    public MedicalReport() {
    }

    public MedicalReport(UserAccount patient, UserAccount doctor, ReportStatus status, String title, String fileName, String mimeType, Long fileSize, byte[] fileContent, String sha256, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.title = title;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
        this.sha256 = sha256;
        this.notes = notes;
    }

    public MedicalReport(UserAccount patient, UserAccount doctor, ReportStatus status, String title, LocalDateTime receivedAt, String fileName, String mimeType, Long fileSize, byte[] fileContent, String sha256, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.title = title;
        this.receivedAt = receivedAt;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
        this.sha256 = sha256;
        this.notes = notes;
    }

    public MedicalReport(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, UserAccount patient, UserAccount doctor, ReportStatus status, String title, LocalDateTime receivedAt, String fileName, String mimeType, Long fileSize, byte[] fileContent, String sha256, String notes) {
        super(id, createdAt, updatedAt);
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.title = title;
        this.receivedAt = receivedAt;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
        this.sha256 = sha256;
        this.notes = notes;
    }

    public UserAccount getPatient() {
        return patient;
    }

    public void setPatient(UserAccount patient) {
        this.patient = patient;
    }

    public UserAccount getDoctor() {
        return doctor;
    }

    public void setDoctor(UserAccount doctor) {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
