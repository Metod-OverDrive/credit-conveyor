package com.practice.deal.model;

import com.practice.deal.model.enums.ApplicationStatus;
import com.practice.deal.model.enums.ChangeType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {

    private UUID id;
    private ApplicationStatus status;
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();
    private ChangeType changeType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusHistory)) return false;
        return id != null && id.equals(((StatusHistory) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
