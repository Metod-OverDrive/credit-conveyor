package com.practice.deal.web.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PassportDto {

    private Long id;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
