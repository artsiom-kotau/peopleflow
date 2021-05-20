package com.peopleflow.api;

import com.peopleflow.model.response.ErrorResponse;
import com.peopleflow.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @Autowired
    private RequestService requestService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onCommonException(Exception exc) {
        log.error(exc.getMessage(), exc);
        ErrorResponse errorResponse = new ErrorResponse(requestService.getCurrentRequestId(), exc.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
