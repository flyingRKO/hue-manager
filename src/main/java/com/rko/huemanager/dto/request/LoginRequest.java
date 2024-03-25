package com.rko.huemanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@Schema(title = "로그인 요청")
public record LoginRequest(
        @Schema(description = "이메일", example = "test123@gmail.com")
        @NotNull(message = "이메일을 작성해주세요.")
        @Email(message = "유효하지 않은 이메일입니다.")
        String email,
        @Schema(description = "비밀번호", example = "asdf123!")
        @NotNull(message = "비밀번호를 작성해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", message = "비밀번호는 최소 8자에서 최대 15자 사이이며, 알파벳, 숫자, 특수 문자를 각각 최소 한 개 이상 포함해야 합니다.")
        String password
) {
}
