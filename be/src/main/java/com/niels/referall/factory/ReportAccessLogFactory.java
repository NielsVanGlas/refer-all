package com.niels.referall.factory;

import com.niels.referall.entity.ReportAccessLog;
import com.niels.referall.entity.UserAccount;
import com.niels.referall.enumerate.ActionType;
import com.niels.referall.repository.ReportAccessLogRepository;

import java.util.UUID;

public class ReportAccessLogFactory {

    public static void createReportAccessLog(UUID medicalReportId, UserAccount loggedAccount, String clientIp, ActionType actionType, ReportAccessLogRepository reportAccessLogRepository) {
        reportAccessLogRepository.saveAndFlush(new ReportAccessLog(
                loggedAccount,
                actionType,
                clientIp,
                medicalReportId,
                actionType.equals(ActionType.DELETE)
        ));
    }

}
