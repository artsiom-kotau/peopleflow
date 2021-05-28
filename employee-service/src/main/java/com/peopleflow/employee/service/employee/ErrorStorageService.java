package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.ErrorRepository;
import com.peopleflow.employee.entity.EmployeeError;
import com.peopleflow.lib.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Slf4j
@Setter
@AllArgsConstructor
public class ErrorStorageService implements EmployeeErrorProcessor {

    private ErrorRepository errorRepository;

    @Override
    @Transactional
    public void process(EmployeeDto employee, Exception exc) {
        log.info("Save error '{}' for employee '{}'", exc.getMessage(), employee.getId());
        EmployeeError error = EmployeeError.builder().
                employeeId(employee.getId()).
                date(OffsetDateTime.now()).
                errorMessage(exc.getMessage()).build();
        errorRepository.save(error);
    }
}
