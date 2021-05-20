package com.peopleflow.service.impl;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
    @Override
    public String newId() {
        return UUID.randomUUID().toString();
    }
}
