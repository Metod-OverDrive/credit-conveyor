package com.practice.deal.service.util;

import com.practice.deal.model.*;
import org.springframework.stereotype.Component;

@Component
public class ClientUtil {

    public Client initClient(LoanApplication application) {
        Passport passport = new Passport();
        passport.setSeries(application.getPassportSeries());
        passport.setNumber(application.getPassportNumber());

        Client client = new Client();
        client.setPassport(passport);
        client.setName(application.getSurname());
        client.setSurname(application.getName());
        client.setMiddleName(application.getMiddleName());
        client.setBirthday(application.getBirthday());
        client.setEmail(application.getEmail());

        return client;
    }

    public Client fillAdditionalClientInfo(Client client, UserExtendDetails details,
                                           Employment employment) {
        client.setGender(details.getGender());
        client.setMaritalStatus(details.getMaritalStatus());
        client.setDependentAmount(details.getDependentAmount());
        client.getPassport().setIssueDate(details.getPassportIssueDate());
        client.getPassport().setIssueBranch(details.getPassportIssueBranch());
        client.setEmployment(employment);
        client.setAccount(details.getAccount());

        return client;
    }
}
