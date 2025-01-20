package com.practice.conveyor.service;

import com.practice.conveyor.model.*;
import com.practice.conveyor.model.enums.EmploymentStatus;
import com.practice.conveyor.model.enums.Gender;
import com.practice.conveyor.model.enums.MaritalStatus;
import com.practice.conveyor.model.enums.Position;
import com.practice.conveyor.service.impl.CreditServiceImpl;
import com.practice.conveyor.service.utils.LoanCalcUtil;
import com.practice.conveyor.web.exception.RejectApplicationException;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private LoanPropertiesService loanPropertiesService;
    @Spy
    private LoanCalcUtil loanCalcUtil;
    @InjectMocks
    private CreditServiceImpl creditService;

    private ScoringData scoringData;
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
    void setScoringData() {
        Employment employment = Employment.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("1234567890")
                .salary(new BigDecimal(50000))
                .position(Position.WORKER)
                .workExperienceTotal(10)
                .workExperienceCurrent(5)
                .build();

        scoringData =  ScoringData.builder()
                .amount(new BigDecimal(500000))
                .term(24)
                .name("Name")
                .surname("Surname")
                .middleName("MiddleName")
                .birthday(LocalDate.of(1985, Month.DECEMBER, 15))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.of(2005, Month.DECEMBER, 21))
                .passportIssueBranch("Passport office")
                .dependentAmount(1)
                .bankAccount("12345678901234567890")
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .gender(Gender.MALE)
                .employment(employment)
                .maritalStatus(MaritalStatus.SINGLE)
                .build();
    }

    @Test
    void scoringLoanIsRight() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);

        Credit credit = creditService.scoringLoan(scoringData);

        assertThat(credit).isNotNull();
        assertThat(credit.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(500000));
        assertThat(credit.getTerm()).isEqualTo(24);
        assertThat(credit.getMonthlyPayment()).isEqualByComparingTo(BigDecimal.valueOf(24480.00));
        assertThat(credit.getRate()).isEqualByComparingTo(BigDecimal.valueOf(16));
        assertThat(credit.getPsk()).isEqualByComparingTo(BigDecimal.valueOf(8.715));
        assertThat(credit.getIsInsuranceEnabled()).isTrue();
        assertThat(credit.getIsSalaryClient()).isFalse();
        assertThat(credit.getPaymentSchedule()).isNotNull();
        assertThat(credit.getPaymentSchedule().size()).isEqualTo(24);

        verify(loanPropertiesService, times(1)).getActualProperties();
    }

    @Test
    void WhenUnemployedThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.getEmployment().setEmploymentStatus(EmploymentStatus.UNEMPLOYED);

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenEmployerINNisNullThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.getEmployment().setEmployerINN(null);

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenEmploymentPositionIsNullThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.getEmployment().setPosition(null);

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenLoanAmountIsMoreThanSalaryTimesLimitThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.setAmount(new BigDecimal(1050000));

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenClientAgeIsTooYoungThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.setBirthday(LocalDate.of(2005, Month.DECEMBER, 15));

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenClientAgeIsTooOldThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.setBirthday(LocalDate.of(1955, Month.DECEMBER, 15));

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenNotEnoughTotalWorkExperienceThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.getEmployment().setWorkExperienceTotal(3);

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void WhenNotEnoughCurrentWorkExperienceThenThrowException() {
        when(loanPropertiesService.getActualProperties()).thenReturn(loanProperties);
        scoringData.getEmployment().setWorkExperienceTotal(3);

        assertThrows(RejectApplicationException.class, () -> creditService.scoringLoan(scoringData));
    }

    @Test
    void calcRate() {
        BigDecimal rate = creditService.calcRate(scoringData, loanProperties);

        assertThat(rate).isEqualByComparingTo(BigDecimal.valueOf(16));
    }

    @Test
    void calcPaymentSchedules() {
        List<LoanPaymentSchedule> payments = creditService.calcPaymentSchedules(
                24, BigDecimal.valueOf(500000), BigDecimal.valueOf(16), BigDecimal.valueOf(24480.00), loanProperties);

        BigDecimal totalAmount = creditService.calcTotalAmount(payments);
        LoanPaymentSchedule firstPayment = payments.get(0);
        LoanPaymentSchedule lastPayment = payments.get(payments.size() - 1);

        assertThat(payments.size()).isEqualTo(24);
        assertThat(totalAmount).isEqualByComparingTo(BigDecimal.valueOf(587145.47));

        assertThat(firstPayment).isNotNull();
        assertThat(firstPayment.getPayment()).isEqualByComparingTo(BigDecimal.valueOf(24480.00));
        assertThat(firstPayment.getInterestPayment()).isEqualByComparingTo(BigDecimal.valueOf(6136.99));
        assertThat(firstPayment.getDebtBalance()).isEqualByComparingTo(BigDecimal.valueOf(18343.01));
        assertThat(firstPayment.getDebtRemain()).isEqualByComparingTo(BigDecimal.valueOf(481656.99));

        assertThat(lastPayment).isNotNull();
        assertThat(lastPayment.getPayment()).isEqualByComparingTo(BigDecimal.valueOf(24105.47));
        assertThat(lastPayment.getInterestPayment()).isEqualByComparingTo(BigDecimal.valueOf(323.18));
        assertThat(lastPayment.getDebtBalance()).isEqualByComparingTo(BigDecimal.valueOf(23782.29));
        assertThat(lastPayment.getDebtRemain()).isEqualByComparingTo(BigDecimal.valueOf(0.00));
    }

    @Test
    void calculatePsk() {
        BigDecimal psk = creditService.calculatePsk(BigDecimal.valueOf(587145.47), BigDecimal.valueOf(500000), 24);

        assertThat(psk).isEqualByComparingTo(BigDecimal.valueOf(8.715));
    }
}