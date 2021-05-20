package com.peopleflow.config;

import com.peopleflow.model.request.User;
import com.peopleflow.service.RequestService;
import com.peopleflow.service.UserService;
import com.peopleflow.service.impl.IdGenerator;
import com.peopleflow.service.impl.ThreadLocalRequestService;
import com.peopleflow.service.impl.UUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public IdGenerator idGenerator() {
        return new UUIDGenerator();
    }

    @Bean
    public RequestService requestService(IdGenerator idGenerator) {
        return new ThreadLocalRequestService(idGenerator);
    }

    @Bean
    public UserService userService() {
        return user -> user;
    }
}
