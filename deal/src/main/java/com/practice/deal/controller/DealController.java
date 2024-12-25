package com.practice.deal.controller;

import com.practice.deal.model.LoanOffer;
import com.practice.deal.service.DealService;
import com.practice.deal.web.dto.LoanApplicationDto;
import com.practice.deal.web.dto.LoanOfferDto;
import com.practice.deal.web.dto.UserExtendDetailsDTO;
import com.practice.deal.web.mapper.LoanApplicationMapper;
import com.practice.deal.web.mapper.LoanOfferMapper;
import com.practice.deal.web.mapper.UserExtendDetailsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    private final LoanApplicationMapper loanApplicationMapper;
    private final LoanOfferMapper loanOfferMapper;
    private final UserExtendDetailsMapper userExtendDetailsMapper;

    @PostMapping("/application")
    public LoanOfferDto calculateOffers(@Valid @RequestBody LoanApplicationDto dto) {

        LoanOffer offer = dealService.calculateOffers(loanApplicationMapper.toEntity(dto));
        return loanOfferMapper.toDto(offer);
    }

    @PutMapping("/offer")
    public void chooseOffer(@Valid @RequestBody LoanOfferDto dto) {

        dealService.chooseOffer(loanOfferMapper.toEntity(dto));
    }

    @PostMapping("/calculate/{applicationId}")
    public void calculateCredit(@Valid @RequestBody UserExtendDetailsDTO dto,
                                @PathVariable("applicationId") Long applicationId) {

        dealService.calculateCredit(
                userExtendDetailsMapper.toEntity(dto),
                applicationId);
    }

}
