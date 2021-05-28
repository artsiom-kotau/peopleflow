package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.service.state.StateProcessor;
import com.peopleflow.lib.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@AllArgsConstructor
public class EmployeeEventListener {

    private StateProcessor stateProcessor;

    @KafkaListener(topics = "${kafka.employee.topic}", containerFactory = "employeeKafkaListenerContainerFactory")
    public void consumeEmployeeEvent(EmployeeDto employee) {
        log.info("Start process event for employee '{}'", employee.getId());
        stateProcessor.process(employee);
    }
}
