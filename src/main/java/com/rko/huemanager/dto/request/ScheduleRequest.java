package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ScheduleRequest(
        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,
        @NotNull
        ScheduleType type
) {
        public static ScheduleRequest of(LocalDate startDate, LocalDate endDate, ScheduleType type){
                return new ScheduleRequest(startDate, endDate, type);
        }
}
