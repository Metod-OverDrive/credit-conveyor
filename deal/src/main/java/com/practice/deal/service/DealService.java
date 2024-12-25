package com.practice.deal.service;

import com.practice.deal.model.LoanApplication;
import com.practice.deal.model.LoanOffer;
import com.practice.deal.model.UserExtendDetails;

public interface DealService {

    LoanOffer calculateOffers(LoanApplication application);
    void chooseOffer(LoanOffer offer);
    void calculateCredit(UserExtendDetails details, Long applicationId);

}
