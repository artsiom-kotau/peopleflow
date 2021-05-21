package com.peopleflow.exception;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private String employeeId;

    public EmployeeNotFoundException(String employeeId) {
        super(String.format("User with id '%s' not found", employeeId));
        this.employeeId = employeeId;
    }


}
