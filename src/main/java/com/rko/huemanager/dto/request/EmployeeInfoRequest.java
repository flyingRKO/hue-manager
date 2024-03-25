package com.rko.huemanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
@Schema(title = "사원 정보 변경 요청")
public record EmployeeInfoRequest(
        @Schema(description = "이름")
        String name,
        @Schema(description = "전화번호", example = "010-0000-0000")
        @Pattern(regexp = "^01(?:0|1|6)-(?:\\d{3}|\\d{4})-\\d{4}$", message = "유효하지 않은 전화번호입니다.")
        String phoneNumber,
        @Schema(description = "직급")
        String position,
        @Schema(description = "부서")
        String department
) {

}
