package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @NotNull(message = "이메일을 작성해주세요.")
        @Email(message = "유효하지 않은 이메일 형식입니다.")
        String email,
        @NotNull(message = "비밀번호를 작성해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", message = "비밀번호는 최소 8자에서 최대 15자 사이이며, 알파벳, 숫자, 특수 문자를 각각 최소 한 개 이상 포함해야 합니다.")
        String password,
        @NotNull(message = "이름을 작성해주세요.")
        String name,
        @NotNull(message = "전화번호를 작성해주세요.")
        @Pattern(regexp = "^01(?:0|1|6)-(?:\\d{3}|\\d{4})-\\d{4}$", message = "유효하지 않은 전화번호입니다.")
        String phoneNumber,
        @NotNull(message = "사원 또는 관리자를 선택해주세요")
        Role role,
        @NotNull(message = "직급을 작성해주세요.")
        String position,
        @NotNull(message = "부서를 작성해주세요.")
        String department
) {

}
