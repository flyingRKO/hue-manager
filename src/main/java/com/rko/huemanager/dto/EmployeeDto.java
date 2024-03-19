package com.rko.huemanager.dto;

public record EmployeeDto(
        String email,
        String name,
        String position,
        String department
) {
    public static EmployeeDto of(String email, String name, String position, String department){
        return new EmployeeDto(email, name, position, department);
    }
}
