package com.practice.loanproperties.controller;

import com.practice.loanproperties.model.LoanProperties;
import com.practice.loanproperties.service.LoanPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("loan")
public class LoanController {

    private final LoanPropertiesService loanService;

    @GetMapping("actual")
    public LoanProperties getActualProperties() {
        return loanService.getActualProperties();
    }

}
