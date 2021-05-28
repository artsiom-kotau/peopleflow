package com.peopleflow.exception;

public class EmployeeServiceException extends RuntimeException {

    public EmployeeServiceException() {
    }

    public EmployeeServiceException(Throwable cause) {
        super(cause);
    }

    public EmployeeServiceException(String message) {
        super(message);
    }
}
