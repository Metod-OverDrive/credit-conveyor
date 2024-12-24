package com.practice.conveyor.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanPaymentSchedule {

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
