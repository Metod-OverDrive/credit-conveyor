package com.practice.deal.model;

import com.practice.deal.model.enums.CreditStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    @Column(name = "insurance_enable")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    private Boolean isSalaryClient;

    @Type(JsonType.class)
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    @Column(name = "payment_schedule")
    private List<LoanPaymentSchedule> paymentSchedule;

    @Column(name = "credit_status", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus = CreditStatus.CALCULATED;
}
