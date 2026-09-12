package com.niels.referall.dto.medicalReport;

import com.niels.referall.entity.MedicalReport;

public class ShowMedicalReportFileDto {

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private byte[] fileContent;

    public ShowMedicalReportFileDto() {
    }

    public ShowMedicalReportFileDto(String fileName, String mimeType, Long fileSize, byte[] fileContent) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
    }

    public ShowMedicalReportFileDto(MedicalReport medicalReport) {
        this.fileName = medicalReport.getFileName();
        this.mimeType = medicalReport.getMimeType();
        this.fileSize = medicalReport.getFileSize();
        this.fileContent = medicalReport.getFileContent();
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
}
