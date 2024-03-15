package com.rko.huemanager.dto.request;

import com.rko.huemanager.domain.constant.Role;

public record SignUpRequest(
        String email,
        String password,
        String name,
        String phoneNumber,
        Role role,
        String position,
        String department
) {

}
