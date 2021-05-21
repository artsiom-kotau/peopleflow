package com.peopleflow.api;

import com.peopleflow.service.RequestService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ApiTest {

    @MockBean
    protected RequestService requestService;

    @AfterEach
    public void postVerification() {
        verify(requestService, times(1)).generateNewRequestId();
    }
}
