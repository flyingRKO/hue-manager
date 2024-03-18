package com.rko.huemanager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailUpdateRequest(
        @NotNull(message = "이메일을 작성해주세요.")
        @Email(message = "유효하지 않은 이메일입니다.")
        String email
) {
}
