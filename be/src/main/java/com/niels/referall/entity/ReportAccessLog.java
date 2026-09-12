package com.niels.referall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.niels.referall.entity.extra.CommonEntity;
import com.niels.referall.enumerate.ActionType;
import com.niels.referall.enumerate.converter.ActionTypeConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ReportAccessLog extends CommonEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserAccount userAccount;

    @Column
    @Convert(converter = ActionTypeConverter.class)
    private ActionType action;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private UUID reportId;

    @Column(nullable = false)
    private Boolean isArchived = false;

    public ReportAccessLog() {
    }

    public ReportAccessLog(UserAccount userAccount, ActionType action, String ipAddress, UUID reportId) {
        this.userAccount = userAccount;
        this.action = action;
        this.ipAddress = ipAddress;
        this.reportId = reportId;
    }

    public ReportAccessLog(UserAccount userAccount, ActionType action, String ipAddress, UUID reportId, Boolean isArchived) {
        this.userAccount = userAccount;
        this.action = action;
        this.ipAddress = ipAddress;
        this.reportId = reportId;
        this.isArchived = isArchived;
    }

    public ReportAccessLog(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, UserAccount userAccount, ActionType action, String ipAddress, UUID reportId, Boolean isArchived) {
        super(id, createdAt, updatedAt);
        this.userAccount = userAccount;
        this.action = action;
        this.ipAddress = ipAddress;
        this.reportId = reportId;
        this.isArchived = isArchived;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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

    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }
}
