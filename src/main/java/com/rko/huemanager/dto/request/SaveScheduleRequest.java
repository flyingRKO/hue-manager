package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SaveScheduleRequest(
        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,
        @NotNull
        ScheduleType type
) {
}
