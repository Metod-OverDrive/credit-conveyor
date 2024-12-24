package com.practice.conveyor.service;

import com.practice.conveyor.model.Credit;
import com.practice.conveyor.model.ScoringData;

public interface CreditService {

    Credit scoringLoan(ScoringData data);
}
