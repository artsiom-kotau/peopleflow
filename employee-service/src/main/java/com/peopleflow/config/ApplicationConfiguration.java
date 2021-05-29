package com.peopleflow.config;

import com.peopleflow.employee.dao.EmployeeRepository;
import com.peopleflow.employee.service.employee.EmployeeEventListener;
import com.peopleflow.employee.service.employee.EmployeeMapper;
import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.employee.EmployeeServiceImpl;
import com.peopleflow.employee.service.state.*;
import com.peopleflow.lib.EmployeeState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public StateProcessor stateProcessor(StateMachineStorage stateMachineStorage) {
        return new EmployeeStateMachineProcessor(stateMachineStorage);
    }

    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        return new EmployeeServiceImpl(employeeRepository, employeeMapper);
    }

    @Bean
    public EmployeeEventListener employeeEventListener(StateProcessor stateProcessor) {
        return new EmployeeEventListener(stateProcessor);
    }

    @Bean
    public RecordMessageConverter messageConverter() {  return new StringJsonMessageConverter();  }

    @Bean
    public ActionFactory actionFactory(EmployeeService employeeService) {
        return new ActionFactory(employeeService);
    }

    @Bean
    public StateMachineStorage stateMachineStorage(StateMachinePersister<EmployeeState, StateEvent, String> persister,
                                                   StateMachineFactory<EmployeeState, StateEvent> stateMachineFactory) {
        return new EmployeeStateMachineStorage(persister, stateMachineFactory);
    }

    @Bean
    public StateMachinePersister<EmployeeState, StateEvent, String> persister() {
        return new DefaultStateMachinePersister<>(new InMemoryStateMachinePersister());
    }

}
