package com.example.practice_modsen_shop;

import com.example.practice_modsen_shop.controllers.UserController;
import com.example.practice_modsen_shop.dto.UserRequestTo;
import com.example.practice_modsen_shop.dto.UserResponseTo;
import com.example.practice_modsen_shop.services.ICrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICrudService<UserRequestTo, UserResponseTo> userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testGetUserById() throws Exception {
        UserResponseTo userMock = new UserResponseTo();
        userMock.setId(12L);
        userMock.setName("TestName");
        userMock.setEmail("check@mail.ru");

        when(userService.getById(12L)).thenReturn(userMock);


        mockMvc.perform(get("/users/{id}", 12))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(userMock.getId().intValue())))
                .andExpect(jsonPath("$.email", is(userMock.getEmail())))
                .andExpect(jsonPath("$.name", is(userMock.getName())));
    }

    @Test
    @WithMockUser
    public void testGetUsers() throws Exception {
        List<UserResponseTo> responseTos = new ArrayList<>();
        UserResponseTo userMock = new UserResponseTo();
        userMock.setId(12L);
        userMock.setName("TestName");
        userMock.setEmail("check@mail.ru");
        responseTos.add(userMock);

        when(userService.getAll(2, 2, "", "")).thenReturn(responseTos);


        mockMvc.perform(get("/users")
                        .param("pageNumber", "2")
                        .param("pageSize", "2")
                        .param("sortBy", "")
                        .param("sortOrder", "")
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(userMock.getId().intValue())))
                .andExpect(jsonPath("$[0].email", is(userMock.getEmail())))
                .andExpect(jsonPath("$[0].name", is(userMock.getName())));
    }

    @Test
    @WithMockUser
    public void testDeleteById() throws Exception {
        UserResponseTo userMock = new UserResponseTo();
        userMock.setId(12L);
        userMock.setName("TestName");
        userMock.setEmail("check@mail.ru");

        when(userService.delete(12L)).thenReturn(userMock);


        mockMvc.perform(delete("/users/{id}", 12).with(csrf()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$.id", is(userMock.getId().intValue())))
                .andExpect(jsonPath("$.email", is(userMock.getEmail())))
                .andExpect(jsonPath("$.name", is(userMock.getName())));
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {

        UserRequestTo requestTo = new UserRequestTo();
        requestTo.setId(12L);
        requestTo.setName("Updated Name");
        requestTo.setEmail("updated@email.com");

        UserResponseTo responseTo = new UserResponseTo();
        responseTo.setId(12L);
        responseTo.setName("Updated Name");
        responseTo.setEmail("updated@email.com");


        when(userService.update(any())).thenReturn(responseTo);


        mockMvc.perform(put("/users").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestTo)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(responseTo.getId().intValue())))
                .andExpect(jsonPath("$.email", is(responseTo.getEmail())))
                .andExpect(jsonPath("$.name", is(responseTo.getName())));
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {

        UserRequestTo requestTo = new UserRequestTo();
        requestTo.setId(12L);
        requestTo.setName("Updated Name");
        requestTo.setEmail("updated@email.com");

        UserResponseTo responseTo = new UserResponseTo();
        responseTo.setId(12L);
        responseTo.setName("Updated Name");
        responseTo.setEmail("updated@email.com");


        when(userService.save(any())).thenReturn(responseTo);

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestTo)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(responseTo.getId().intValue())))
                .andExpect(jsonPath("$.email", is(responseTo.getEmail())))
                .andExpect(jsonPath("$.name", is(responseTo.getName())));
    }

}
