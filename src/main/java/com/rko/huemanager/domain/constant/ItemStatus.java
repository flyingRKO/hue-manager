package com.rko.huemanager.domain.constant;

public enum ItemStatus {
    AVAILABLE("대여 가능"),
    RENTED("대여 중"),;

    private final String description;

    ItemStatus(String description){
        this.description = description;
    }
}
