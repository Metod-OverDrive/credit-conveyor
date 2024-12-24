package com.practice.conveyor.service;

import com.practice.conveyor.model.LoanApplication;
import com.practice.conveyor.model.LoanOffer;

import java.util.List;

public interface LoanOfferService {

    LoanOffer preScoringLoan(LoanApplication application);
}
