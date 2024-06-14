package com.example.practice_modsen_shop;

import com.example.practice_modsen_shop.controllers.CategoryController;
import com.example.practice_modsen_shop.dto.CategoryRequestTo;
import com.example.practice_modsen_shop.dto.CategoryResponseTo;
import com.example.practice_modsen_shop.services.ICrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    private ICrudService<CategoryRequestTo, CategoryResponseTo> categoryService;

    private static final List<CategoryResponseTo> categoryResponseToList = new ArrayList<>();

    @BeforeAll
    public static void init() {
        CategoryResponseTo categoryResponseTo1 = new CategoryResponseTo(1L, "2");
        CategoryResponseTo categoryResponseTo2 = new CategoryResponseTo(7L, "6");

        categoryResponseToList.add(categoryResponseTo1);
        categoryResponseToList.add(categoryResponseTo2);
    }

    @Test
    @WithMockUser
    public void testGetCategories() throws Exception {
        when(categoryService.getAll(1, 2, "id", "desc")).thenReturn(categoryResponseToList);

        mvc.perform(get("/categories").with(csrf())
                        .param("pageNumber", "1")
                        .param("pageSize", "2")
                        .param("sortBy", "id")
                        .param("sortOrder", "desc"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$", hasSize(categoryResponseToList.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("2"));
    }

    @Test
    @WithMockUser
    public void testGetCategoryById() throws Exception {
        when(categoryService.getById(1L)).thenReturn(categoryResponseToList.get(0));

        mvc.perform(get("/categories/{id}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("2"));
    }

    @Test
    @WithMockUser
    public void testCreateCategory() throws Exception {
        CategoryRequestTo request = new CategoryRequestTo(1L, "2");
        when(categoryService.save(any())).thenReturn(categoryResponseToList.get(0));

        mvc.perform(post("/categories").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("2"));
    }

    @Test
    @WithMockUser
    public void testUpdateCategory() throws Exception {
        CategoryRequestTo request = new CategoryRequestTo(1L, "2");
        when(categoryService.update(any())).thenReturn(categoryResponseToList.get(0));

        mvc.perform(put("/categories").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("2"));
    }

    @Test
    @WithMockUser
    public void testDeleteCategory() throws Exception {
        when(categoryService.delete(1L)).thenReturn(categoryResponseToList.get(0));

        mvc.perform(delete("/categories/{id}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("2"));
    }
}
