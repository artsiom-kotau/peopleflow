package com.peopleflow.lib;

import lombok.Getter;

@Getter
public enum EmployeeState {

    ADDED(1, "Added"),
    IN_CHECK(2, "In check"),
    APPROVED(3, "Approved"),
    ACTIVE(4, "Active");

    EmployeeState(int id, String label) {
        this.id = id;
        this.label = label;
    }

    private int id;
    private String label;

}
