package com.rko.huemanager.domain.constant;

import lombok.Getter;

public enum Role {
    ROLE_EMP("사원"),
    ROLE_ADMIN("관리자");

    @Getter
    private final String description;

    Role(String description){
        this.description = description;
    }
}
