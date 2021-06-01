package com.peopleflow.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ThreadLocalRequestServiceTest {

    @MockBean
    private IdGenerator idGenerator;

    private ThreadLocalRequestService requestService;

    @BeforeEach
    public void init() {
        this.requestService = new ThreadLocalRequestService(idGenerator);
    }

    @Test
    public void generateNewId() {
        String id = "1234";
        when(idGenerator.newId()).thenReturn(id);
        assertEquals(id, requestService.generateNewRequestId());
        assertEquals(id, requestService.getCurrentRequestId());
    }

    @Test
    public void unsetCallReturnId() {
        String id = "1234";
        when(idGenerator.newId()).thenReturn(id);
        assertEquals(id, requestService.generateNewRequestId());
        assertEquals(id, requestService.unsetRequestId());
    }

    @Test
    public void getIdWithoutGenerateCall() {
        String id = "1234";
        when(idGenerator.newId()).thenReturn(id);
        assertThrows(IllegalStateException.class, () -> requestService.getCurrentRequestId());
    }

    @Test
    public void getIdAfterUnsetCall() {
        String id = "1234";
        when(idGenerator.newId()).thenReturn(id);
        assertEquals(id, requestService.generateNewRequestId());
        assertEquals(id, requestService.unsetRequestId());
        assertThrows(IllegalStateException.class, () -> requestService.getCurrentRequestId());
    }
}