package com.rko.huemanager.dto.response;

import com.rko.huemanager.domain.Employee;

public record SignUpResponse(
        String email,
        String name
) {
    public static SignUpResponse fromEntity(Employee employee){
        return new SignUpResponse(
                employee.getEmail(),
                employee.getName()
        );
    }
}
