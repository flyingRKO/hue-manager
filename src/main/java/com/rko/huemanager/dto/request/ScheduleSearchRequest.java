package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;

import java.time.LocalDate;

public record ScheduleSearchRequest(
        LocalDate startDate,
        LocalDate endDate,
        ScheduleType type,
        ScheduleStatus status
) {
}
