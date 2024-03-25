package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.ScheduleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "일정 상태 변경 요청")
public record StatusUpdateRequest(
        @Schema(description = "일정 상태", example = "PENDING")
        ScheduleStatus status
) {
}
