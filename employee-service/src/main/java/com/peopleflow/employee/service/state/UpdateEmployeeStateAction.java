package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
@AllArgsConstructor
public class UpdateEmployeeStateAction implements Action<EmployeeState, StateEvent> {

    private EmployeeService employeeService;

    @Override
    public void execute(StateContext<EmployeeState, StateEvent> stateContext) {
        String employeeId = StateMachineUtils.getEmployeeId(stateContext.getStateMachine());
        EmployeeState state = stateContext.getTarget().getId();
        EmployeeDto employee = new EmployeeDto();
        employee.setState(state);
        employee.setId(employeeId);
        log.info("Update state of employee '{}' to '{}'", employeeId, state);
        employeeService.updateStatus(employee);
    }
}
