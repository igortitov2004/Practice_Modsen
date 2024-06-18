package com.modsen.practice.controller;


import com.modsen.practice.dto.OrderItemRequestTo;
import com.modsen.practice.dto.OrderItemResponseTo;
import com.modsen.practice.service.ICrudService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ICrudService<OrderItemRequestTo, OrderItemResponseTo> orderItemService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testGetOrderItemById() throws Exception {
        OrderItemResponseTo orderItemMock = new OrderItemResponseTo();
        orderItemMock.setId(2L);
        orderItemMock.setCount(5);
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
    public void testGetOrderItems() throws Exception {
        List<OrderItemResponseTo> responseTos = new ArrayList<>();
        OrderItemResponseTo orderItemMock = new OrderItemResponseTo();
        orderItemMock.setId(2L);
        orderItemMock.setCount(5);
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
    public void testDeleteById() throws Exception {
        OrderItemResponseTo orderItemMock = new OrderItemResponseTo();
        orderItemMock.setId(2L);
        orderItemMock.setCount(5);
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
    public void testUpdateOrderItem() throws Exception {

        OrderItemResponseTo orderItemMock = new OrderItemResponseTo();
        orderItemMock.setId(2L);
        orderItemMock.setCount(5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        OrderItemRequestTo orderItemRequestMock = new OrderItemRequestTo();
        orderItemRequestMock.setId(2L);
        orderItemRequestMock.setCount(5);
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
    public void testCreateOrderItem() throws Exception {

        OrderItemResponseTo orderItemMock = new OrderItemResponseTo();
        orderItemMock.setId(2L);
        orderItemMock.setCount(5);
        orderItemMock.setOrderId(12L);
        orderItemMock.setProductId(45L);

        OrderItemRequestTo orderItemRequestMock = new OrderItemRequestTo();
        orderItemRequestMock.setId(2L);
        orderItemRequestMock.setCount(5);
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
