package com.practice.deal.service.util;

import com.practice.deal.model.Application;
import com.practice.deal.model.ScoringData;
import com.practice.deal.model.UserExtendDetails;
import org.springframework.stereotype.Component;

@Component
public class ScoringDataFactory {

    public ScoringData getScoringData(Application application, UserExtendDetails details) {

        return ScoringData.builder()
                .amount(application.getLoanOffer().getRequestedAmount())
                .term(application.getLoanOffer().getTerm())
                .name(application.getClient().getName())
                .surname(application.getClient().getSurname())
                .middleName(application.getClient().getMiddleName())
                .gender(application.getClient().getGender())
                .birthday(application.getClient().getBirthday())
                .passportSeries(application.getClient().getPassport().getSeries())
                .passportNumber(application.getClient().getPassport().getNumber())
                .passportIssueDate(application.getClient().getPassport().getIssueDate())
                .passportIssueBranch(application.getClient().getPassport().getIssueBranch())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(details.getDependentAmount())
                .employment(details.getEmployment())
                .bankAccount(details.getAccount())
                .isInsuranceEnabled(application.getLoanOffer().getIsInsuranceEnabled())
                .isSalaryClient(application.getLoanOffer().getIsSalaryClient())
                .build();
    }

}
