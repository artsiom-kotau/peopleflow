package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class StateMachineGuard implements Guard<EmployeeState, StateEvent> {

    private EmployeeService employeeService;

    @Override
    public boolean evaluate(StateContext<EmployeeState, StateEvent> context) {
        String id = StateMachineUtils.getEmployeeId(context.getStateMachine());
        log.info("Check if employee '{}' exists", id);
        boolean exist = Objects.nonNull(id) && Objects.nonNull(employeeService.get(id));
        log.info("employee '{}' exists: ", exist);
        return exist;
    }
}
