package com.example.practice_modsen_shop;


import com.example.practice_modsen_shop.Controllers.OrderController;
import com.example.practice_modsen_shop.DTO.OrderRequestTo;
import com.example.practice_modsen_shop.DTO.OrderResponseTo;
import com.example.practice_modsen_shop.Service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IService<OrderRequestTo, OrderResponseTo> orderService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testGetOrders() throws Exception {
        List<OrderResponseTo> responseTos = new ArrayList<>();
        OrderResponseTo orderMock = new OrderResponseTo();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(123.12);
        orderMock.setStatus("Completed");
        responseTos.add(orderMock);

        when(orderService.getAll(2, 2, "price", "desc")).thenReturn(responseTos);


        mockMvc.perform(get("/orders")
                        .param("pageNumber", "2")
                        .param("pageSize", "2")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc")
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$[0].price", is(orderMock.getPrice())))
                .andExpect(jsonPath("$[0].status", is(orderMock.getStatus())));
    }


    @Test
    @WithMockUser
    public void testGetOrderById() throws Exception {
        OrderResponseTo orderMock = new OrderResponseTo();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(123.12);
        orderMock.setStatus("Completed");


        when(orderService.getById(2L)).thenReturn(orderMock);


        mockMvc.perform(get("/orders/{id}", 2).with(csrf()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus())));
    }

    @Test
    @WithMockUser
    public void testDeleteById() throws Exception {
        OrderResponseTo orderMock = new OrderResponseTo();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(123.12);
        orderMock.setStatus("Completed");


        when(orderService.delete(2L)).thenReturn(orderMock);


        mockMvc.perform(delete("/orders/{id}", 2).with(csrf()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus())));

    }

    @Test
    @WithMockUser
    public void testUpdateOrder() throws Exception {

        OrderResponseTo orderMock = new OrderResponseTo();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(123.12);
        orderMock.setStatus("Completed");


        OrderRequestTo orderRequestMock = new OrderRequestTo();
        orderRequestMock.setId(2L);
        orderRequestMock.setUserId(3L);
        orderRequestMock.setPrice(123.12);
        orderRequestMock.setStatus("Completed");


        when(orderService.update(any())).thenReturn(orderMock);

        mockMvc.perform(put("/orders").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderRequestMock)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus())));

    }

    @Test
    @WithMockUser
    public void testCreateOrder() throws Exception {

        OrderResponseTo orderMock = new OrderResponseTo();
        orderMock.setId(2L);
        orderMock.setUserId(3L);
        orderMock.setPrice(123.12);
        orderMock.setStatus("Completed");


        OrderRequestTo orderRequestMock = new OrderRequestTo();
        orderRequestMock.setId(2L);
        orderRequestMock.setUserId(3L);
        orderRequestMock.setPrice(123.12);
        orderRequestMock.setStatus("Completed");


        when(orderService.save(any())).thenReturn(orderMock);

        mockMvc.perform(post("/orders").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderRequestMock)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", is(orderMock.getId().intValue())))
                .andExpect(jsonPath("$.userId", is(orderMock.getUserId().intValue())))
                .andExpect(jsonPath("$.price", is(orderMock.getPrice())))
                .andExpect(jsonPath("$.status", is(orderMock.getStatus())));
    }

}
