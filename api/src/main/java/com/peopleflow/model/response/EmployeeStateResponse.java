package com.peopleflow.model.response;

import lombok.Getter;

@Getter
public class EmployeeStateResponse extends Response {

    public EmployeeStateResponse(String id, String state) {
        super(id);
        this.state = state;
    }

    String state;
}
