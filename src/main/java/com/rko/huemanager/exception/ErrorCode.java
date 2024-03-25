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
    NOT_ENOUGH_DAYS(HttpStatus.BAD_REQUEST, "연차가 부족합니다."),
    INVALID_NIGHT_SHIFT_REQUEST(HttpStatus.BAD_REQUEST, "당직은 하루만 선택 가능합니다"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생했습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다."),
    NOT_AN_EMPLOYEE(HttpStatus.FORBIDDEN, "직원이 아닙니다."),
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다." ),
    UNAUTHORIZED_SCHEDULE(HttpStatus.CONFLICT, "일정 수정/삭제는 당사자만 가능합니다."),
    SCHEDULE_NOT_PENDING(HttpStatus.CONFLICT, "일정 변경은 상태가 보류 중일 때 가능합니다.");

    private final HttpStatus status;
    private final String message;
}
