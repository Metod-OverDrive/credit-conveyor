package com.practice.deal.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @PostConstruct
    public void initializeServiceName() {
        MDC.put("serviceName", applicationName);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
