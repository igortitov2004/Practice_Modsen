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

@SpringBootTest
class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemServiceImpl;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ConversionService conversionService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void getById_whenExists() {
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .build();

        OrderItemResponse expected = OrderItemResponse.builder()
                .id(1L)
                .build();

        Mockito.when(orderItemRepository.findById(1L)).thenReturn(Optional.ofNullable(orderItem));
        Mockito.when(conversionService.convert(orderItem, OrderItemResponse.class)).thenReturn(expected);

        OrderItemResponse actual = orderItemServiceImpl.getById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void getById_whenNotExists() {
        Mockito.when(orderItemRepository.findById(1L)).thenThrow(new OrderItemIsNotExistsException(""));

        assertThrows(OrderItemIsNotExistsException.class, () -> orderItemServiceImpl.getById(1L));
    }

    @Test
    void getAll() {
        List<OrderItem> orderItemsList = new ArrayList<>();
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .build();
        orderItemsList.add(orderItem);

        OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                .id(1L)
                .build();

        List<OrderItemResponse> expected = new ArrayList<>();
        expected.add(orderItemResponse);

        Mockito.when(orderItemRepository.findAll(PageRequest.of(1, 1, Sort.by("desc")))).thenReturn(new PageImpl<>(orderItemsList));
        Mockito.when(conversionService.convert(orderItemsList.get(0), OrderItemResponse.class)).thenReturn(orderItemResponse);

        List<OrderItemResponse> actual = orderItemServiceImpl.getAll(1, 1, "desc", null);

        assertEquals(expected, actual);
    }

    @Test
    void save() {
        OrderItemRequest request = OrderItemRequest.builder()
                .productId(1L)
                .count((short) 1)
                .build();

        Order order = Order.builder()
                .id(1L)
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .id(1L)
                .build();

        Product product = Product.builder()
                .id(1L)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .count(request.getCount())
                .build();

        OrderItem savedOrderItem = OrderItem.builder()
                .id(1L)
                .order(order)
                .product(product)
                .count(request.getCount())
                .build();

        OrderItemResponse expected = OrderItemResponse.builder()
                .id(1L)
                .orderId(1L)
                .product(productResponse)
                .count(request.getCount())
                .build();

        Mockito.when(productServiceImpl.getById(request.getProductId())).thenReturn(productResponse);
        Mockito.when(modelMapper.map(productResponse, Product.class)).thenReturn(product);
        Mockito.when(orderItemRepository.save(orderItem)).thenReturn(savedOrderItem);
        Mockito.when(modelMapper.map(savedOrderItem, OrderItemResponse.class)).thenReturn(expected);

        OrderItemResponse actual = orderItemServiceImpl.save(request, order);

        assertEquals(expected, actual);
    }

    @Test
    void delete_whenExists() {
        Mockito.when(orderItemRepository.existsOrderItemById(1L)).thenReturn(true);

        orderItemServiceImpl.delete(1L);

        Mockito.verify(orderItemRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test()
    void delete_whenNotExists() {
        Mockito.when(orderItemRepository.existsOrderItemById(1L)).thenReturn(false);

        assertThrows(OrderItemIsNotExistsException.class, () -> orderItemServiceImpl.delete(1L));

        Mockito.verify(orderItemRepository, Mockito.times(0)).deleteById(1L);
    }
}