package com.practice.deal.service.util;

import com.practice.deal.model.Application;
import com.practice.deal.model.StatusHistory;
import com.practice.deal.model.enums.ApplicationStatus;
import com.practice.deal.model.enums.ChangeType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApplicationHistoryStatusUtil {

    public Application changeStatusHistory(Application application, ApplicationStatus applicationStatus) {

        application.setStatus(applicationStatus);

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setId(UUID.randomUUID());
        statusHistory.setStatus(applicationStatus);
        statusHistory.setChangeType(ChangeType.AUTOMATIC);

        application.getStatusHistory().add(statusHistory);
        return application;
    }
}
