package com.practice.conveyor.service;

import com.practice.conveyor.model.LoanApplication;
import com.practice.conveyor.model.LoanOffer;
import com.practice.conveyor.model.LoanProperties;
import com.practice.conveyor.service.impl.LoanOfferServiceImpl;
import com.practice.conveyor.service.utils.LoanCalcUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanOfferServiceTest {

    @Mock
    private LoanPropertiesService loanPropertiesService;
    @Spy
    private LoanCalcUtil loanCalcUtil;
    @InjectMocks
    private LoanOfferServiceImpl loanService;
    private LoanApplication application;
    private static LoanProperties loanProperties;

    @BeforeAll
    static void setLoanProperties() {
        loanProperties = LoanProperties.builder()
                .updateTime(LocalDateTime.of(2025, 1, 8, 10, 30, 0))
                .globalRate(21)
                .insurancePrice(new BigDecimal("150.50"))
                .insuranceRate(10)
                .salaryRate(5)
                .minClientAge(20L)
                .maxClientAge(60L)
                .minTotalWorkExperience(6)
                .minCurrentWorkExperience(3)
                .salaryQuantity(20)
                .minManAgeForSpecialRate(30L)
                .maxManAgeForSpecialRate(55L)
                .minWomanAgeForSpecialRate(35L)
                .maxWomanAgeForSpecialRate(60L)
                .monthlyPaymentStartDay(22)
                .build();
    }

    @BeforeEach
    void setLoanApplication() {
        application = LoanApplication.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(500000))
                .term(24)
                .name("Name")
                .surname("Surname")
                .middleName("Middlename")
                .email("email@example.com")
                .birthday(LocalDate.of(1985, Month.DECEMBER, 15))
                .passportSeries("1234")
                .passportNumber("123456")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
    }

    @Test
    void preScoringLoanIsRight() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);

        LoanOffer offer = loanService.preScoringLoan(application);

        assertThat(offer).isNotNull();
        assertThat(offer.getRequestedAmount()).isEqualByComparingTo(BigDecimal.valueOf(500000));
        assertThat(offer.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(559488.24));
        assertThat(offer.getMonthlyPayment()).isEqualByComparingTo(BigDecimal.valueOf(23312.01));
        assertThat(offer.getRate()).isEqualByComparingTo(BigDecimal.valueOf(11));
    }

    @Test
    void calcRate() {
        BigDecimal rate = loanService.calcRate(true, false, loanProperties);

        assertThat(rate).isEqualByComparingTo(BigDecimal.valueOf(11));
    }
}