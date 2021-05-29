package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@AllArgsConstructor
public class CreateEmployeeAction implements Action<EmployeeState, StateEvent> {

    private EmployeeService employeeService;

    @Override
    public void execute(StateContext<EmployeeState, StateEvent> stateContext) {
        EmployeeDto employee = StateMachineUtils.getEmployee(stateContext.getStateMachine());
        employee.setState(EmployeeState.ADDED);
        employeeService.create(employee);
    }
}
