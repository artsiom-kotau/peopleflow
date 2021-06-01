package com.peopleflow.employee.service.state.action;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ActionFactoryTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private StateMachineUtils stateMachineUtils;

    private ActionFactory actionFactory;

    @BeforeEach
    public void init() {
        this.actionFactory = new ActionFactory(employeeService, stateMachineUtils);
    }

    @Test
    public void verifyAllBeansNotNull() {
        assertNotNull(actionFactory.getCreateAction());
        assertNotNull(actionFactory.getGuard());
        assertNotNull(actionFactory.getUpdateStateAction());
    }

}