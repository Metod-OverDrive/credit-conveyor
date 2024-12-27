package com.practice.techwork.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class TechWorkMessage implements Serializable {

    private UUID id;
    private boolean status;
    private LocalDateTime time;

}
