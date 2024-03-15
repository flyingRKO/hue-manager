package com.rko.huemanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");

    private final HttpStatus status;
    private final String message;
}
