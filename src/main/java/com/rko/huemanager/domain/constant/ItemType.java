package com.rko.huemanager.domain.constant;

public enum ItemType {
    CAR("차량"),
    LAPTOP("노트북");

    private final String description;

    ItemType(String description){
        this.description = description;
    }
}

