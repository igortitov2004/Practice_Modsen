package com.modsen.practice.service.impl;



import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.Product;
import com.modsen.practice.exception.orderItem.OrderItemIsNotExistsException;
import com.modsen.practice.repository.OrderItemRepository;
import com.modsen.practice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import com.modsen.practice.auth.UserVODetails;
import com.modsen.practice.dto.*;
import com.modsen.practice.entity.*;
import com.modsen.practice.exception.OrderNotExistException;
import com.modsen.practice.repository.OrderRepository;
import com.modsen.practice.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserVODetails userVODetails;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private User user;
    private Order order;
    private OrderRequest orderRequest;
    private OrderResponse orderResponse;
    private OrderItemRequest orderItemRequest;
    private OrderItemResponse orderItemResponse;
    private OrderItem orderItem;
    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .apartmentNumber(1)
                .build();

        orderResponse = OrderResponse.builder()
                .id(1L)
                .apartmentNumber(1)
                .build();
    }

    @Test
    void getById_whenOrderExists() {
        Order order1 = Order.builder()
                .id(1L)
                .build();
        OrderResponse expected = OrderResponse.builder()
                .id(1L)
                .build();


        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order1));
        Mockito.when(conversionService.convert(order1, OrderResponse.class)).thenReturn(expected);

        OrderResponse actual = orderServiceImpl.getById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void getById_whenOrderNotExist() {
        Mockito.when(orderRepository.findById((1L))).thenThrow(new OrderNotExistException("Error"));

        assertThrows(OrderNotExistException.class, () -> orderServiceImpl.getById(1L));
    }

    @Test
    void getAll() {
        List<Order> ordersList = new ArrayList<>();
        Order order1 = Order.builder()
                .id(1L)
                .build();
        ordersList.add(order1);

        OrderResponse orderResponse1 = OrderResponse.builder()
                .id(1L)
                .build();

        List<OrderResponse> expected = new ArrayList<>();
        expected.add(orderResponse1);

        Mockito.when(orderRepository.findAll(PageRequest.of(1,1, Sort.by("desk")))).thenReturn(new PageImpl<>(ordersList));

        Mockito.when(conversionService.convert(ordersList.get(0), OrderResponse.class)).thenReturn(orderResponse1);

        List<OrderResponse> actual = orderServiceImpl.getAll(1,1, "desk", null);

        assertEquals(expected, actual);
    }

    @Test
    void getAllByUserId() {
        List<OrderResponse> expected = new ArrayList<>();
        expected.add(orderResponse);

        List<Order> orders = List.of(order);

        Mockito.when(orderRepository.findByUser_id(eq(1L), any(PageRequest.class))).thenReturn(orders);
        Mockito.when(conversionService.convert(order, OrderResponse.class)).thenReturn(orderResponse);

        List<OrderResponse> actual = orderServiceImpl.getAllByUserId(1L, 0, 10, "id", "asc");

        assertEquals(expected.size(), actual.size());
        assertEquals(orderResponse, actual.get(0));
    }

    @Test
    void save() {
        OrderRequest request = OrderRequest.builder()
                .id(1L)
                .apartmentNumber(1)
                .orderItems(Collections.emptySet())
                .build();

        User user = new User();
        user.setId(1L);

        Order order = Order.builder()
                .id(1L)
                .apartmentNumber(request.getApartmentNumber())
                .user(user)
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .apartmentNumber(request.getApartmentNumber())
                .user(user)
                .build();

        OrderResponse expected = OrderResponse.builder()
                .id(1L)
                .apartmentNumber(request.getApartmentNumber())
                .build();

        // Mock security context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userVODetails);
        when(userVODetails.getUser()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);

        // Mock modelMapper for both conversions
        when(modelMapper.map(request, Order.class)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(modelMapper.map(savedOrder, OrderResponse.class)).thenReturn(expected);

        // Execute save method
        OrderResponse actual = orderServiceImpl.save(request);

        // Assert results
        assertEquals(expected, actual);

        // Verify interactions
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(modelMapper, times(1)).map(request, Order.class);
        verify(modelMapper, times(1)).map(savedOrder, OrderResponse.class);
    }
    @Test
    void delete_whenOrderNotExist() {
        Mockito.when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderNotExistException.class, () -> orderServiceImpl.delete(1L));
        Mockito.verify(orderRepository, Mockito.times(0)).deleteById(1L);
    }

    @Test
    void delete_whenOrderExists() {
        Mockito.when(orderRepository.existsById(1L)).thenReturn(true);

        orderServiceImpl.delete(1L);

        Mockito.verify(orderRepository, times(1)).deleteById(1L);
    }
}
