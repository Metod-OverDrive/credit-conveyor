package com.practice.loanproperties.service;

import com.practice.loanproperties.model.LoanProperties;
import com.practice.loanproperties.repository.LoanPropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanPropertiesService {

    private final LoanPropertiesRepository loanRepository;

    public LoanProperties getActualProperties() {
        return loanRepository.findFirstByOrderByUpdateTime();
    }
}
