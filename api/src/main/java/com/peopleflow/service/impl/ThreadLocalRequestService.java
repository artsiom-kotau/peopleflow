package com.peopleflow.service.impl;

import com.peopleflow.service.RequestService;

public class ThreadLocalRequestService implements RequestService {

    private IdGenerator idGenerator;

    private ThreadLocal<String> idStorage;

    public ThreadLocalRequestService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.idStorage = new ThreadLocal<>();
    }

    @Override
    public String generateNewRequestId() {
        String newId = idGenerator.newId();
        idStorage.set(newId);
        return newId;
    }

    @Override
    public String getCurrentRequestId() {
        return idStorage.get();
    }
}
