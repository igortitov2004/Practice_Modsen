package com.modsen.practice.controller;

import com.modsen.practice.auth.UserVODetailsService;
import com.modsen.practice.auth.jwt.JwtService;
import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.practice.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserVODetailsService userVODetailsService;

    @MockBean
    private CategoryService categoryService;

    private static final String CONTROLLER_PATH = "/api/categories";

    private static final List<CategoryResponse> categoryResponseList = new ArrayList<>();

    @BeforeAll
    static void init() {
        CategoryResponse categoryResponse1 = new CategoryResponse(1L, "22");
        CategoryResponse categoryResponse2 = new CategoryResponse(7L, "66");

        categoryResponseList.add(categoryResponse1);
        categoryResponseList.add(categoryResponse2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCategories() throws Exception {
        when(categoryService.getAll(1, 2, "id", "desc")).thenReturn(categoryResponseList);

        mvc.perform(get(CONTROLLER_PATH).with(csrf())
                        .param("pageNumber", "1")
                        .param("pageSize", "2")
                        .param("sortBy", "id")
                        .param("sortOrder", "desc"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$", hasSize(categoryResponseList.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("22"));
    }

    @Test
    @WithMockUser
    void testGetCategoryById() throws Exception {
        when(categoryService.getById(1L)).thenReturn(categoryResponseList.get(0));

        mvc.perform(get(CONTROLLER_PATH + "/{id}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("22"));
    }

    @Test
    @WithMockUser
    void testCreateCategory() throws Exception {
        CategoryRequest request = new CategoryRequest(1L, "22");
        when(categoryService.save(any())).thenReturn(categoryResponseList.get(0));

        mvc.perform(post(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("22"));
    }

    @Test
    @WithMockUser
    void testUpdateCategory() throws Exception {
        CategoryRequest request = new CategoryRequest(1L, "22");
        when(categoryService.update(any())).thenReturn(categoryResponseList.get(0));

        mvc.perform(put(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("22"));
    }

    @Test
    @WithMockUser
    void testDeleteCategory() throws Exception {
        mvc.perform(delete(CONTROLLER_PATH + "/{id}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        Mockito.verify(categoryService, Mockito.times(1)).delete(1L);
    }
}
