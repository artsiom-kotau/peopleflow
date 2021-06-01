package com.peopleflow.exception;

import com.peopleflow.lib.EmployeeState;

public class UpdateEmployeeStatusException extends RuntimeException {

    public UpdateEmployeeStatusException(String employeeId, EmployeeState state) {
        super(String.format("Unable updated employee '{}' with state '{}'", employeeId, state));
    }
}
