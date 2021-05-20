package com.peopleflow.api;

import com.peopleflow.model.request.User;
import com.peopleflow.service.RequestService;
import com.peopleflow.service.UserService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RequestService requestService;

    @Test
    public void postNewUserShouldReturn201() throws Exception {

        String requestId = "1111";
        when(requestService.getCurrentRequestId()).thenReturn(requestId);

        mvc.perform(post("/user").content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(requestId)));

        verify(userService, times(1)).addUser(any(User.class));

    }

}