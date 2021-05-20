package com.peopleflow.api;

import com.peopleflow.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RequestService requestService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestService.generateNewRequestId();
        return super.preHandle(request, response, handler);
    }
}
