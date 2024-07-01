package com.modsen.practice.controller;

import com.modsen.practice.auth.UserVODetailsService;
import com.modsen.practice.auth.jwt.JwtService;
import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.modsen.practice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserVODetailsService userVODetailsService;

    @MockBean
    private UserService userService;

    private static final String CONTROLLER_PATH = "/api/users";

    @Test
    @WithMockUser
    void testGetByIdUserById() throws Exception {
        UserResponse userMock = new UserResponse();
        userMock.setId(12L);
        userMock.setFirstname("TestName");
        userMock.setEmail("check@mail.ru");
        userMock.setGender("asd");
        userMock.setLastname("adads");
        userMock.setLogin("sad");

        when(userService.getById(12L)).thenReturn(userMock);


        mockMvc.perform(get(CONTROLLER_PATH + "/{id}", 12).with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(userMock.getId().intValue())))
                .andExpect(jsonPath("$.email", is(userMock.getEmail())))
                .andExpect(jsonPath("$.firstname", is(userMock.getFirstname())));
    }

    @Test
    @WithMockUser
    void testGetByIdUsers() throws Exception {
        List<UserResponse> responseTos = new ArrayList<>();
        UserResponse userMock = new UserResponse();
        userMock.setId(12L);
        userMock.setFirstname("TestName");
        userMock.setEmail("check@mail.ru");
        responseTos.add(userMock);

        when(userService.getAll(2, 2, "price", "desc")).thenReturn(responseTos);


        mockMvc.perform(get(CONTROLLER_PATH)
                        .with(csrf())
                        .param("pageNumber", "2")
                        .param("pageSize", "2")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(userMock.getId().intValue())))
                .andExpect(jsonPath("$[0].email", is(userMock.getEmail())))
                .andExpect(jsonPath("$[0].firstname", is(userMock.getFirstname())));
    }

    @Test
    @WithMockUser
    void testDeleteById() throws Exception {
        mockMvc.perform(delete(CONTROLLER_PATH + "/{id}", 12).with(csrf()))
                .andExpect(status().is(204));
        Mockito.verify(userService,Mockito.times(1)).delete(12L);
    }

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {

        UserRequest requestTo = new UserRequest();
        requestTo.setId(12L);
        requestTo.setFirstname("Updated Name");
        requestTo.setFirstname("TestName");
        requestTo.setEmail("check@mail.ru");
        requestTo.setGender("asd");
        requestTo.setLastname("adads");
        requestTo.setLogin("sad");
        requestTo.setRole("sada");
        requestTo.setBirthDate(new Date(12L));
        requestTo.setMiddleName("sada");
        requestTo.setPasswordHash("asdas");
        requestTo.setPhoneNumber("sadsad");
        UserResponse responseTo = new UserResponse();
        responseTo.setId(12L);
        responseTo.setFirstname("Updated Name");
        responseTo.setEmail("updated@email.com");


        when(userService.update(any())).thenReturn(responseTo);


        mockMvc.perform(put(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestTo)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(responseTo.getId().intValue())))
                .andExpect(jsonPath("$.email", is(responseTo.getEmail())))
                .andExpect(jsonPath("$.firstname", is(responseTo.getFirstname())));
    }
}
