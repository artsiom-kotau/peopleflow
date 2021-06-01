package com.peopleflow.model.request;

import lombok.Data;

@Data
public class AddEmployee {

    String name;

    Integer age;

    String position;

    String jobDescription;
}
