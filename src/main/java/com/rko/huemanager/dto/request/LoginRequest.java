package com.rko.huemanager.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
