package com.practice.deal.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class LoanPaymentScheduleDto {

    private Integer paymentIndex;
    private LocalDate date;
    private BigDecimal payment;
    // Сумма процентов
    private BigDecimal interestPayment;
    // Основной долг
    private BigDecimal debtBalance;
    // Остаток долга
    private BigDecimal debtRemain;

}
