package com.peopleflow.api;

import com.peopleflow.model.request.User;
import com.peopleflow.model.response.UserResponse;
import com.peopleflow.service.RequestService;
import com.peopleflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserResponse addUser(User user) {
        userService.addUser(user);
        return new UserResponse(requestService.getCurrentRequestId());
    }
}
