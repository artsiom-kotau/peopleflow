package com.peopleflow.model.request;

import com.peopleflow.lib.EmployeeState;
import lombok.Data;

@Data
public class Employee {

    String id;

    String name;

    Integer age;

    String position;

    String jobDescription;

    EmployeeState state;
}
