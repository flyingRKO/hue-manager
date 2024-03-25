package com.rko.huemanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@Schema(title = "비밀번호 변경 요청")
public record PasswordUpdateRequest(
        @Schema(description = "이전 비밀번호", example = "asdf123!")
        @NotNull(message = "비밀번호를 작성해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", message = "비밀번호는 최소 8자에서 최대 15자 사이이며, 알파벳, 숫자, 특수 문자를 각각 최소 한 개 이상 포함해야 합니다.")
        String currentPassword,
        @Schema(description = "새 비밀번호", example = "asdf123!")
        @NotNull(message = "새 비밀번호를 작성해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", message = "비밀번호는 최소 8자에서 최대 15자 사이이며, 알파벳, 숫자, 특수 문자를 각각 최소 한 개 이상 포함해야 합니다.")
        String newPassword
) {
}
