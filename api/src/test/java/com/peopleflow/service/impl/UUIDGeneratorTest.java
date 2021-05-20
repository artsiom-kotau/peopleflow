package com.peopleflow.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDGeneratorTest {

    private UUIDGenerator uuidGenerator;

    @BeforeEach
    public void init() {
        uuidGenerator = new UUIDGenerator();
    }

    @Test
    public void newIdNotNull() {
        assertNotNull(uuidGenerator.newId());
    }

    @Test
    public void sequenceCallsProduceNewId() {
        assertNotEquals(uuidGenerator.newId(), uuidGenerator.newId());
    }

    @Test
    public void idHasUUIDFormat() {
        String id = uuidGenerator.newId();
        assertEquals(id, UUID.fromString(id).toString());
    }

}