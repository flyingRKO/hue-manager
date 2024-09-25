package com.rko.huemanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    EMAIL_UNCHANGED(HttpStatus.BAD_REQUEST, "이전 이메일과 같습니다. 다른 이메일을 작성해주세요"),
    PASSWORD_SAME_OLD(HttpStatus.BAD_REQUEST, "이전 비밀번호와 같습니다. 다른 비밀번호를 작성해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    NOT_ENOUGH_DAYS(HttpStatus.BAD_REQUEST, "연차가 부족합니다."),
    INVALID_NIGHT_SHIFT_REQUEST(HttpStatus.BAD_REQUEST, "당직은 하루만 선택 가능합니다"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생했습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다."),
    NOT_AN_EMPLOYEE(HttpStatus.FORBIDDEN, "직원이 아닙니다."),
    NOT_AN_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 없습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다." ),
    UNAUTHORIZED_ACCESS(HttpStatus.CONFLICT, "당사자만 가능합니다."),
    SCHEDULE_NOT_PENDING(HttpStatus.CONFLICT, "일정 변경은 상태가 보류 중일 때 가능합니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    MEETING_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 회의실 사용 신청 이력이 없습니다."),
    MEETING_ROOM_ALREADY_BOOKED(HttpStatus.CONFLICT, "해당 날짜, 시간은 이미 예약된 회의실입니다."),
    INVALID_STATUS(HttpStatus.BAD_REQUEST, "PENDING 상태일 때만 가능합니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "물품을 찾을 수 없습니다."),
    NOT_FOUND_ITEM_REQUEST(HttpStatus.NOT_FOUND, "물품 대여 신청을 찾을 수 없습니다."),
    ALREADY_RENTED(HttpStatus.CONFLICT, "이미 대여된 물품입니다."),;

    private final HttpStatus status;
    private final String message;
}
