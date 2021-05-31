package com.peopleflow.employee.service.state.action;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
@Setter
@AllArgsConstructor
public class CreateEmployeeAction implements Action<EmployeeState, StateEvent> {

    private EmployeeService employeeService;

    private StateMachineUtils stateMachineUtils;


    @Override
    public void execute(StateContext<EmployeeState, StateEvent> stateContext) {
        EmployeeDto employee = stateMachineUtils.getEmployee(stateContext.getStateMachine());
        employee.setState(EmployeeState.ADDED);
        log.info("Create employee '{}'", employee.getId());
        employeeService.create(employee);
    }

}
