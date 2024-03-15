package com.rko.huemanager.domain.constant;

public enum Role {
    ROLE_EMP("사원"),
    ROLE_ADMIN("관리자");

    private final String description;

    Role(String description){
        this.description = description;
    }
}
