package com.peopleflow.employee.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "employee_error")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id")
    String employeeId;

    @Column(name = "error_message")
    String errorMessage;

    @Column
    private OffsetDateTime date;
}
