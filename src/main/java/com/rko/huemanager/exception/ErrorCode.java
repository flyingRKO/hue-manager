package com.rko.huemanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    PASSWORD_SAME_OLD(HttpStatus.BAD_REQUEST, "이전 비밀번호와 같습니다. 다른 비밀번호를 작성해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생했습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
