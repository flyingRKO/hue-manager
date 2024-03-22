package com.rko.huemanager.domain.constant;

public enum ScheduleType {
    LEAVE("연차"),
    NIGHT_SHIFT("당직");

    private final String description;

    ScheduleType(String description){
        this.description = description;
    }
}
