package com.practice.conveyor.service.impl;

import com.practice.conveyor.model.Credit;
import com.practice.conveyor.model.Employment;
import com.practice.conveyor.model.LoanPaymentSchedule;
import com.practice.conveyor.model.ScoringData;
import com.practice.conveyor.model.enums.EmploymentStatus;
import com.practice.conveyor.service.CreditService;
import com.practice.conveyor.service.utils.LoanCalcUtil;
import com.practice.conveyor.web.exception.RejectApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {

    @Value("${loan.global-rate}")
    private Integer GLOBAL_RATE;

    private static final long MIN_CLIENT_AGE = 20L;
    private static final long MAX_CLIENT_AGE = 60L;
    private static final Integer MIN_TOTAL_WORK_EXPERIENCE = 6;
    private static final Integer MIN_CURRENT_WORK_EXPERIENCE = 3;
    private static final BigDecimal SALARY_QUANTITY = new BigDecimal("20");
    private static final long MIN_MAN_AGE_FOR_SPECIAL_RATE = 30L;
    private static final long MAX_MAN_AGE_FOR_SPECIAL_RATE = 55L;
    private static final long MIN_WOMAN_AGE_FOR_SPECIAL_RATE = 35L;
    private static final long MAX_WOMAN_AGE_FOR_SPECIAL_RATE = 60L;
    private static final int MONTHLY_PAYMENT_START_DAY = 22;
    private static final String REJECT_APPLICATION = "Отказ: ";

    private final LoanCalcUtil loanCalcUtil;

    @Override
    public Credit scoringLoan(ScoringData data) {
        checkRejectReasons(data);

        BigDecimal amount = data.getAmount();
        Integer term = data.getTerm();

        BigDecimal rate = calcRate(data);
        BigDecimal monthlyPayment = loanCalcUtil.calculateMonthlyPayment(term, amount, rate);
        List<LoanPaymentSchedule> paymentSchedules = calcPaymentSchedules(term, amount, rate, monthlyPayment);
        BigDecimal totalAmount = calcTotalAmount(paymentSchedules);
        BigDecimal psk = calculatePsk(totalAmount, amount, term);

        Credit credit = Credit.builder()
                .amount(amount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(data.getIsInsuranceEnabled())
                .isSalaryClient(data.getIsSalaryClient())
                .paymentSchedule(paymentSchedules)
                .build();

        log.info(credit.toString());

        return credit;
    }

    private void checkRejectReasons(ScoringData data) {
        Employment employment = data.getEmployment();
        LocalDate birthday = data.getBirthday();

        if (EmploymentStatus.UNEMPLOYED.equals(employment.getEmploymentStatus())) {
            rejectApplication("client's unemployed.");
        }

        if (employment.getEmployerINN() == null || employment.getPosition() == null) {
            rejectApplication("client employment INN or position is null.");
        }

        if (employment.getSalary().multiply(SALARY_QUANTITY).compareTo(data.getAmount()) < 0) {
            rejectApplication("Loan amount more than 20 times the client's salary.");
        }

        if (LocalDate.now().minusYears(MIN_CLIENT_AGE).isBefore(birthday)
                || LocalDate.now().minusYears(MAX_CLIENT_AGE).isAfter(birthday)
                || LocalDate.now().minusYears(MAX_CLIENT_AGE).equals(birthday)) {
            rejectApplication("Not correct age for this service.");
        }

        if (MIN_TOTAL_WORK_EXPERIENCE > employment.getWorkExperienceTotal()
            || MIN_CURRENT_WORK_EXPERIENCE > employment.getWorkExperienceCurrent()) {
            rejectApplication("Not correct work experience for this service.");
        }
    }

    private void rejectApplication(String reason) {
        String rejectReason = REJECT_APPLICATION + reason;
        log.info(rejectReason);
        throw new RejectApplicationException(rejectReason);
    }

    private BigDecimal calcRate(ScoringData data) {
        BigDecimal rate = new BigDecimal(GLOBAL_RATE);
        Employment employment = data.getEmployment();

        if (data.getIsInsuranceEnabled()) {
            rate = rate.subtract(new BigDecimal(2));
        }

        if (data.getIsSalaryClient()) {
            rate = rate.subtract(new BigDecimal(1));
        }

        if (data.getDependentAmount() > 1) {
            rate = rate.add(new BigDecimal(1));
        }

        switch (employment.getEmploymentStatus()) {
            case BUSINESS_OWNER -> rate = rate.add(new BigDecimal(3));
            case SELF_EMPLOYED -> rate = rate.add(new BigDecimal(1));
        }

        switch (employment.getPosition()) {
            case TOP_MANAGER -> rate = rate.subtract(new BigDecimal(4));
            case MID_MANAGER -> rate = rate.subtract(new BigDecimal(2));
        }

        switch (data.getMaritalStatus()) {
            case MARRIED -> rate = rate.subtract(new BigDecimal(3));
            case DIVORCED -> rate = rate.add(new BigDecimal(1));
        }

        switch (data.getGender()) {
            case MALE -> {
                if (userIsOlder(MIN_MAN_AGE_FOR_SPECIAL_RATE, data.getBirthday())
                    && userIsYounger(MAX_MAN_AGE_FOR_SPECIAL_RATE,data.getBirthday())) {
                    rate = rate.subtract(new BigDecimal(3));
                }
            }
            case FEMALE -> {
                if (userIsOlder(MIN_WOMAN_AGE_FOR_SPECIAL_RATE, data.getBirthday())
                        && userIsYounger(MAX_WOMAN_AGE_FOR_SPECIAL_RATE,data.getBirthday())) {
                    rate = rate.subtract(new BigDecimal(3));
                }
            }
        }

        return rate;
    }

    private List<LoanPaymentSchedule> calcPaymentSchedules(
            Integer term, BigDecimal amount, BigDecimal rate, BigDecimal monthlyPayment) {

        Integer id = 1;
        List<LoanPaymentSchedule> LoanPaymentScheduleList = new ArrayList<>();

        GregorianCalendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_MONTH, MONTHLY_PAYMENT_START_DAY);
        date.add(Calendar.MONTH, 1);

        BigDecimal remainingDebt = amount;

        BigDecimal interestPayment;
        BigDecimal debtBalance;
        BigDecimal debtRemain;
        BigDecimal payment;

        for (int i = 0; i < term; i++) {
            payment = monthlyPayment;
            LoanPaymentSchedule scheduleElement = LoanPaymentSchedule.builder()
                .paymentIndex(id++)
                .date(date.getTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .build();

            interestPayment = calculateInterestPayment(remainingDebt, rate,
                    new BigDecimal(date.getActualMaximum(Calendar.DAY_OF_MONTH)),
                    new BigDecimal(date.getActualMaximum(Calendar.DAY_OF_YEAR)));

            debtBalance = monthlyPayment.subtract(interestPayment);

            debtRemain = remainingDebt.subtract(debtBalance);

            scheduleElement.setInterestPayment(interestPayment);

            if (i == term - 1) {
                debtBalance = debtBalance.add(debtRemain);
                payment = payment.add(debtRemain);
                debtRemain = remainingDebt.subtract(debtBalance);
            }

            scheduleElement.setPayment(payment);
            scheduleElement.setDebtBalance(debtBalance);
            scheduleElement.setDebtRemain(debtRemain);

            LoanPaymentScheduleList.add(scheduleElement);
            remainingDebt = debtRemain;
            date.add(Calendar.MONTH, 1);
        }

        return LoanPaymentScheduleList.stream()
                .sorted(Comparator.comparing(LoanPaymentSchedule::getPaymentIndex))
            .toList();
    }

    private BigDecimal calcTotalAmount(List<LoanPaymentSchedule> LoanPaymentScheduleList) {
        return LoanPaymentScheduleList.stream()
                .map(LoanPaymentSchedule::getPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    private BigDecimal calculatePsk(BigDecimal totalAmount, BigDecimal amount, Integer term) {
        return ((totalAmount
                .divide(amount, 5, RoundingMode.HALF_UP))
                .subtract(new BigDecimal(1)))
                .divide((new BigDecimal(term))
                        .divide(new BigDecimal(12),
                                5, RoundingMode.HALF_UP), 5, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))
                .setScale(3, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateInterestPayment(
            BigDecimal remainingDebt, BigDecimal finalRate, BigDecimal daysOfMonth, BigDecimal daysOfYear) {
        return remainingDebt
                .multiply(finalRate)
                .multiply(new BigDecimal("0.01"))
                .multiply(daysOfMonth)
                .divide(daysOfYear, 2, RoundingMode.HALF_EVEN);
    }

    private boolean userIsOlder(Long minAge, LocalDate clientBirthday) {
        return LocalDate.now().minusYears(minAge).isAfter(clientBirthday)
                || LocalDate.now().minusYears(minAge).equals(clientBirthday);
    }

    private boolean userIsYounger(Long maxAge, LocalDate clientBirthday) {
        return LocalDate.now().minusYears(maxAge).isBefore(clientBirthday);
    }

}
