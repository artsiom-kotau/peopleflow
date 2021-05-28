package com.peopleflow.model.response;

import com.peopleflow.lib.EmployeeState;
import lombok.Getter;

@Getter
public class EmployeeStateResponse extends Response {

    public EmployeeStateResponse(String id, EmployeeState state) {
        super(id);
        this.state = state;
    }

    EmployeeState state;
}
