package com.niels.referall.dto.log;

import com.niels.referall.enumerate.ActionType;
import com.niels.referall.enumerate.DocumentType;

public class ShowReportAccessLogDto {

    private String firstName;

    private String lastName;

    private DocumentType documentType;

    private String documentId;

    private ActionType action;

    private String ipAddress;

    public ShowReportAccessLogDto() {
    }

    public ShowReportAccessLogDto(String firstName, String lastName, DocumentType documentType, String documentId, ActionType action, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentType = documentType;
        this.documentId = documentId;
        this.action = action;
        this.ipAddress = ipAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
