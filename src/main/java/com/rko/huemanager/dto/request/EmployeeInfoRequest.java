package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmployeeInfoRequest(
        String name,
        @Pattern(regexp = "^01(?:0|1|6)-(?:\\d{3}|\\d{4})-\\d{4}$", message = "유효하지 않은 전화번호입니다.")
        String phoneNumber,
        String position,
        String department
) {

}
