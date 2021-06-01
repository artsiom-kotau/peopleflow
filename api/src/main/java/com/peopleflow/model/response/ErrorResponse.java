package com.peopleflow.model.response;

import lombok.Getter;

@Getter
public class ErrorResponse extends Response {

    String error;

    public ErrorResponse(String id) {
        super(id);
    }

    public ErrorResponse(String id, String error) {
        super(id);
        this.error = error;
    }

}
