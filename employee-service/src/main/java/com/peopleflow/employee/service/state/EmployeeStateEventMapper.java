package com.peopleflow.employee.service.state;

import com.peopleflow.lib.EmployeeState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmployeeStateEventMapper {

    private static final Map<EmployeeState, StateEvent> mapping = new HashMap<>();

    static {
        mapping.put(EmployeeState.IN_CHECK, StateEvent.START_CHECK);
        mapping.put(EmployeeState.APPROVED, StateEvent.FINISH_CHECK);
        mapping.put(EmployeeState.ACTIVE, StateEvent.ACTIVATE);
    }

    public static StateEvent getEventForState(EmployeeState state) {
        return Objects.nonNull(state) ? mapping.get(state) : StateEvent.CREATE;
    }
}
