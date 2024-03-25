package com.rko.huemanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
@Schema(title = "이메일 변경 요청")
public record EmailUpdateRequest(
        @Schema(description = "이메일", example = "test123@gmail.com")
        @NotNull(message = "이메일을 작성해주세요.")
        @Email(message = "유효하지 않은 이메일입니다.")
        String email
) {
}
