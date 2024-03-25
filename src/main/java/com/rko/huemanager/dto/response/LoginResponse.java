package com.rko.huemanager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "jwt 로그인 토큰")
public record LoginResponse(
        String token
) {
}
