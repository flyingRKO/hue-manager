package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleStatus;

public record StatusUpdateRequest(
        ScheduleStatus status
) {
}
