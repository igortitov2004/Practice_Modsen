package com.modsen.practice.controller;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.practice.service.IProductService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IProductService productService;

    private static final String CONTROLLER_PATH = "/api/products";

    private static final List<ProductResponse> productResponseList = new ArrayList<>();

    @BeforeAll
    static void init() {
        ProductResponse productResponse1 = new ProductResponse(1L, 2L, "3", "3", 4.0, "5", 6, 7);
        ProductResponse productResponse2 = new ProductResponse(7L, 6L, "5", "5", 4.0, "3", 2, 1);

        productResponseList.add(productResponse1);
        productResponseList.add(productResponse2);
    }

    @Test
    @WithMockUser
    void testGetProducts() throws Exception {
        when(productService.getAll(1, 2, "id", "desc")).thenReturn(productResponseList);

        mvc.perform(get(CONTROLLER_PATH).with(csrf())
                        .param("pageNumber", "1")
                        .param("pageSize", "2")
                        .param("sortBy", "id")
                        .param("sortOrder", "desc"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$", hasSize(productResponseList.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].category").value(2))
                .andExpect(jsonPath("$[0].ingredients").value("3"))
                .andExpect(jsonPath("$[0].name").value("3"))
                .andExpect(jsonPath("$[0].price").value(4.0))
                .andExpect(jsonPath("$[0].description").value("5"))
                .andExpect(jsonPath("$[0].weight").value(6))
                .andExpect(jsonPath("$[0].caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testGetProductById() throws Exception {
        when(productService.getById(1L)).thenReturn(productResponseList.get(0));

        mvc.perform(get(CONTROLLER_PATH + "/{id}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3"))
                .andExpect(jsonPath("$.price").value(4.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest(1L, 2L, "3", "3", 4.0, "5", 6, 7);
        when(productService.save(any())).thenReturn(productResponseList.get(0));

        mvc.perform(post(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3"))
                .andExpect(jsonPath("$.price").value(4.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testUpdateProduct() throws Exception {
        ProductRequest request = new ProductRequest(1L, 2L, "3", "3", 4.0, "5", 6, 7);
        when(productService.update(any())).thenReturn(productResponseList.get(0));

        mvc.perform(put(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3"))
                .andExpect(jsonPath("$.price").value(4.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testDeleteProduct() throws Exception {
        when(productService.delete(1L)).thenReturn(productResponseList.get(0));

        mvc.perform(delete(CONTROLLER_PATH + "/{id}", 1).with(csrf()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.price").value(4.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }
}

