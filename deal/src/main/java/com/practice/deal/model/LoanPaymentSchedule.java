package com.practice.deal.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanPaymentSchedule)) return false;
        return paymentIndex != null && paymentIndex.equals(((LoanPaymentSchedule) o).getPaymentIndex());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
