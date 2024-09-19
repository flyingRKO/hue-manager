package com.rko.huemanager.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardRequest(
        @NotNull(message = "제목을 작성해주세요.")
        String title,

        @NotNull(message = "내용을 작성해주세요.")
        String content
) {
}
