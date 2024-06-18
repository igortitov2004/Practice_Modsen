package com.modsen.practice.controller;


import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.modsen.practice.enumeration.OrderStatus;
import com.modsen.practice.service.IOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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


@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    private static final String CONTROLLER_PATH = "/api/orders";

    @Test
    @WithMockUser
    void testGetByIdOrders() throws Exception {
        List<OrderResponse> responseTos = new ArrayList<>();
        OrderResponse orderMock = new OrderResponse();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(BigDecimal.valueOf(123.12));
        orderMock.setStatus(OrderStatus.COMPLETED);
        responseTos.add(orderMock);

        when(orderService.getAll(2, 2, "price", "desc")).thenReturn(responseTos);


        mockMvc.perform(get(CONTROLLER_PATH)
                        .param("pageNumber", "2")
                        .param("pageSize", "2")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc")
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$[0].price", is(orderMock.getPrice().doubleValue())))
                .andExpect(jsonPath("$[0].status", is(orderMock.getStatus().name())));
    }


    @Test
    @WithMockUser
    void testGetByIdOrderById() throws Exception {
        OrderResponse orderMock = new OrderResponse();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(BigDecimal.valueOf(123.12));
        orderMock.setStatus(OrderStatus.COMPLETED);


        when(orderService.getById(2L)).thenReturn(orderMock);


        mockMvc.perform(get(CONTROLLER_PATH + "/{id}", 2).with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice().doubleValue())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus().name())));
    }

    @Test
    @WithMockUser
    void testDeleteById() throws Exception {
        OrderResponse orderMock = new OrderResponse();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(BigDecimal.valueOf(123.12));
        orderMock.setStatus(OrderStatus.COMPLETED);


        when(orderService.delete(2L)).thenReturn(orderMock);


        mockMvc.perform(delete(CONTROLLER_PATH + "/{id}", 2).with(csrf()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice().doubleValue())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus().name())));

    }

    @Test
    @WithMockUser
    void testUpdateOrder() throws Exception {

        OrderResponse orderMock = new OrderResponse();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(BigDecimal.valueOf(123.12));
        orderMock.setStatus(OrderStatus.COMPLETED);


        OrderRequest orderRequestMock = new OrderRequest();
        orderRequestMock.setId(2L);
        orderRequestMock.setUserId(3L);
        orderRequestMock.setPrice(BigDecimal.valueOf(123.12));
        orderRequestMock.setStatus(OrderStatus.COMPLETED);


        when(orderService.update(any())).thenReturn(orderMock);

        mockMvc.perform(put(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderRequestMock)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice().doubleValue())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus().name())));

    }

    @Test
    @WithMockUser
    void testSaveOrder() throws Exception {

        OrderResponse orderMock = new OrderResponse();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(BigDecimal.valueOf(123.12));
        orderMock.setStatus(OrderStatus.COMPLETED);


        OrderRequest orderRequestMock = new OrderRequest();
        orderRequestMock.setId(2L);
        orderRequestMock.setUserId(3L);
        orderRequestMock.setPrice(BigDecimal.valueOf(123.12));
        orderRequestMock.setStatus(OrderStatus.COMPLETED);


        when(orderService.save(any())).thenReturn(orderMock);

        mockMvc.perform(post(CONTROLLER_PATH).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderRequestMock)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice().doubleValue())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus().name())));
    }

}
