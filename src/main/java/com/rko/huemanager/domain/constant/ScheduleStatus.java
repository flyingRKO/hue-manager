package com.rko.huemanager.domain.constant;

public enum ScheduleStatus {
    PENDING("보류 중"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String description;

    ScheduleStatus(String description){
        this.description = description;
    }
}
