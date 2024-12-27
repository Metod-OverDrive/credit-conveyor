package com.practice.conveyor.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "loan")
@Getter
@Setter
public class LoanProperties {

    private Integer globalRate;
    private BigDecimal insurancePrice;
    private Integer insuranceRate;
    private Integer salaryRate;
}
