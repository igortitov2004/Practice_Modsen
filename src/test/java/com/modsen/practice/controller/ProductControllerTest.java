package com.modsen.practice.controller;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.practice.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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
    private ProductService productService;

    private static final String CONTROLLER_PATH = "/api/products";

    private static final List<ProductResponse> productResponseList = new ArrayList<>();

    @BeforeAll
    static void init() {
        CategoryResponse categoryResponse1 = new CategoryResponse();
        categoryResponse1.setId(2L);
        CategoryResponse categoryResponse2 = new CategoryResponse();
        categoryResponse2.setId(6L);
        ProductResponse productResponse1 = new ProductResponse(1L, categoryResponse1, "3sad", "3", BigDecimal.valueOf(12.0), "5", (short)6, (short)7);
        ProductResponse productResponse2 = new ProductResponse(7L, categoryResponse2, "5sad", "5", BigDecimal.valueOf(12.0), "3", (short)2, (short)1);

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
                .andExpect(jsonPath("$[0].category.id").value(2))
                .andExpect(jsonPath("$[0].ingredients").value("3"))
                .andExpect(jsonPath("$[0].name").value("3sad"))
                .andExpect(jsonPath("$[0].price").value(12.0))
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
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3sad"))
                .andExpect(jsonPath("$.price").value(12.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest(1L, 2L, "3sad", "3asdf", new BigDecimal(12), "5", (short)6, (short)7);
        when(productService.save(any())).thenReturn(productResponseList.get(0));

        mvc.perform(post(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3sad"))
                .andExpect(jsonPath("$.price").value(12.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }

    @Test
    @WithMockUser
    void testUpdateProduct() throws Exception {
        ProductRequest request = new ProductRequest(1L, 2L, "3sad", "3asdf", new BigDecimal(12), "5", (short)6, (short)7);
        when(productService.update(any())).thenReturn(productResponseList.get(0));

        mvc.perform(put(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.name").value("3sad"))
                .andExpect(jsonPath("$.price").value(12.0))
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
                .andExpect(jsonPath("$.category.id").value(2))
                .andExpect(jsonPath("$.ingredients").value("3"))
                .andExpect(jsonPath("$.price").value(12.0))
                .andExpect(jsonPath("$.description").value("5"))
                .andExpect(jsonPath("$.weight").value(6))
                .andExpect(jsonPath("$.caloric_value").value(7));
    }
}

