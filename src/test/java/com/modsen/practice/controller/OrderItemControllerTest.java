package com.modsen.practice.controller;


import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.practice.service.IOrderItemService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderItemService orderItemService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testGetOrderItemById() throws Exception {
        OrderItemResponse orderItemMock = new OrderItemResponse();
        orderItemMock.setId(2L);
        orderItemMock.setCount((short)5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        when(orderItemService.getById(2L)).thenReturn(orderItemMock);


        mockMvc.perform(get("/order_items/{id}", 2).with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderItemMock.getId().intValue())))
                .andExpect(jsonPath("$.count", is(orderItemMock.getCount())))
                .andExpect(jsonPath("$.orderId", is(orderItemMock.getOrderId().intValue())))
                .andExpect(jsonPath("$.productId", is(orderItemMock.getProductId().intValue())));
    }

    @Test
    @WithMockUser
    void testGetOrderItems() throws Exception {
        List<OrderItemResponse> responseTos = new ArrayList<>();
        OrderItemResponse orderItemMock = new OrderItemResponse();
        orderItemMock.setId(2L);
        orderItemMock.setCount((short)5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);
        responseTos.add(orderItemMock);

        when(orderItemService.getAll(2, 2, "count", "desc")).thenReturn(responseTos);


        mockMvc.perform(get("/order_items")
                        .param("pageNumber", "2")
                        .param("pageSize", "2")
                        .param("sortBy", "count")
                        .param("sortOrder", "desc")
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(orderItemMock.getId().intValue())))
                .andExpect(jsonPath("$[0].count", is(orderItemMock.getCount())))
                .andExpect(jsonPath("$[0].orderId", is(orderItemMock.getOrderId().intValue())))
                .andExpect(jsonPath("$[0].productId", is(orderItemMock.getProductId().intValue())));
    }

    @Test
    @WithMockUser
    void testDeleteById() throws Exception {
        OrderItemResponse orderItemMock = new OrderItemResponse();
        orderItemMock.setId(2L);
        orderItemMock.setCount((short)5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        when(orderItemService.delete(2L)).thenReturn(orderItemMock);


        mockMvc.perform(delete("/order_items/{id}", 2).with(csrf()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$.id", is(orderItemMock.getId().intValue())))
                .andExpect(jsonPath("$.count", is(orderItemMock.getCount())))
                .andExpect(jsonPath("$.orderId", is(orderItemMock.getOrderId().intValue())))
                .andExpect(jsonPath("$.productId", is(orderItemMock.getProductId().intValue())));
    }

    @Test
    @WithMockUser
    void testUpdateOrderItem() throws Exception {

        OrderItemResponse orderItemMock = new OrderItemResponse();
        orderItemMock.setId(2L);
        orderItemMock.setCount((short) 5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        OrderItemRequest orderItemRequestMock = new OrderItemRequest();
        orderItemRequestMock.setId(2L);
        orderItemRequestMock.setCount((short)5);
        orderItemRequestMock.setOrderId(12L);
        orderItemRequestMock.setProductId(45L);

        when(orderItemService.update(any())).thenReturn(orderItemMock);

        mockMvc.perform(put("/order_items").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderItemRequestMock)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderItemMock.getId().intValue())))
                .andExpect(jsonPath("$.count", is(orderItemMock.getCount())))
                .andExpect(jsonPath("$.orderId", is(orderItemMock.getOrderId().intValue())))
                .andExpect(jsonPath("$.productId", is(orderItemMock.getProductId().intValue())));
    }

    @Test
    @WithMockUser
    void testCreateOrderItem() throws Exception {

        OrderItemResponse orderItemMock = new OrderItemResponse();
        orderItemMock.setId(2L);
        orderItemMock.setCount((short)5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        OrderItemRequest orderItemRequestMock = new OrderItemRequest();
        orderItemRequestMock.setId(2L);
        orderItemRequestMock.setCount((short)5);
        orderItemRequestMock.setOrderId(12L);
        orderItemRequestMock.setProductId(45L);

        when(orderItemService.save(any())).thenReturn(orderItemMock);

        mockMvc.perform(post("/order_items").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderItemRequestMock)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(orderItemMock.getId().intValue())))
                .andExpect(jsonPath("$.count", is(orderItemMock.getCount())))
                .andExpect(jsonPath("$.orderId", is(orderItemMock.getOrderId().intValue())))
                .andExpect(jsonPath("$.productId", is(orderItemMock.getProductId().intValue())));
    }
}
