package com.practice.conveyor.controller;

import com.practice.conveyor.model.Credit;
import com.practice.conveyor.model.LoanOffer;
import com.practice.conveyor.service.CreditService;
import com.practice.conveyor.service.LoanOfferService;
import com.practice.conveyor.web.dto.CreditDto;
import com.practice.conveyor.web.dto.LoanApplicationDto;
import com.practice.conveyor.web.dto.LoanOfferDto;
import com.practice.conveyor.web.dto.ScoringDataDto;
import com.practice.conveyor.web.mapper.CreditMapper;
import com.practice.conveyor.web.mapper.LoanApplicationMapper;
import com.practice.conveyor.web.mapper.LoanOfferMapper;
import com.practice.conveyor.web.mapper.ScoringDataMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("conveyor")
@Slf4j
public class CreditConveyorController {

    private final LoanOfferService loanOfferService;
    private final CreditService creditService;
    private final LoanApplicationMapper loanApplicationMapper;
    private final CreditMapper creditMapper;
    private final LoanOfferMapper loanOfferMapper;
    private final ScoringDataMapper scoringDataMapper;

    @PostMapping("offers")
    public LoanOfferDto preScoringLoan(@Valid @RequestBody LoanApplicationDto dto) {
        LoanOffer offer = loanOfferService.preScoringLoan(loanApplicationMapper.toEntity(dto));
        return loanOfferMapper.toDto(offer);
    }

    @PostMapping("calculation")
    public CreditDto scoringLoan(@Valid @RequestBody ScoringDataDto dto) {
        Credit credit = creditService.scoringLoan(scoringDataMapper.toEntity(dto));
        return creditMapper.toDto(credit);
    }

    @GetMapping("rofl")
    public void rofl() {
      log.warn("Rofl is here.");
    }
}
