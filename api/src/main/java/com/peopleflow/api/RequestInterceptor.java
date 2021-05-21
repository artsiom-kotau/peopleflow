package com.peopleflow.api;

import com.peopleflow.service.RequestService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RequestService requestService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = requestService.generateNewRequestId();
        ThreadContext.put("requestId", requestId);
        if (log.isInfoEnabled()) {
            log.info("Start request with id '{}'. Request '{}'", requestId, request);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        String requestId = requestService.unsetRequestId();
        ThreadContext.remove("requestId");
        if (log.isInfoEnabled()) {
            log.info("Complete request with id '{}'. Request '{}'", requestId, request);
        }
    }
}
