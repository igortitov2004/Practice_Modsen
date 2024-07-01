package com.modsen.practice.repository;


import com.modsen.practice.entity.*;
import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.OrderStatus;
import com.modsen.practice.enumeration.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class OrderRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    private List<Product> allProducts;

    private List<User> allUsers;

    private Long findingId;



    void setUpCategories() {
        Category category1 = new Category(null,"Cat1",null);
        categoryRepository.save(category1);

        Category category2 = new Category(null,"Cat2",null);
        categoryRepository.save(category2);
    }

    void setUpProducts() {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.get(0);

        Product product1 = new Product(null,category,"Product1"," ",BigDecimal.valueOf(12),
                " ",(short)1,(short)12,null);
        productRepository.save(product1);

        Product product2 = new Product(null,category,"Product2"," ",BigDecimal.valueOf(12),
                " ",(short)1,(short)12,null);
        productRepository.save(product2);

        allProducts = productRepository.findAll();
    }

    void setUpUsers() {
        User user1 = new User(null,"FirstUser"," "," ",Gender.MALE,
                " "," ",UserRole.CUSTOMER," ",new Date(12),"",null);
        userRepository.save(user1);

        User user2 = new User(null,"SecondUser"," "," ",Gender.MALE,
                " "," ",UserRole.CUSTOMER," ",new Date(12),"",null);
        userRepository.save(user2);

        allUsers = userRepository.findAll();
    }

    Set<OrderItem> setUpOrderItemsForOrder() {
        OrderItem item = new OrderItem();
        item.setCount((short) 10);
        item.setProduct(allProducts.get(0));

        OrderItem item1 = new OrderItem();
        item1.setCount((short) 2);
        item1.setProduct(allProducts.get(1));

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(item);
        orderItems.add(item1);
        return orderItems;
    }

    @BeforeEach
    void setUp() {
        setUpCategories();
        setUpProducts();
        setUpUsers();
        Set<OrderItem> orderItems = setUpOrderItemsForOrder();
        Order testOrder = new Order(null,allUsers.get(1),BigDecimal.valueOf(12),OrderStatus.COMPLETED,"",
                "","",12, new Date(12),orderItems);
        for (OrderItem item : orderItems) {
            item.setOrder(testOrder);
        }
        orderRepository.save(testOrder);

        List<Order> orders = orderRepository.findAll();
        findingId = orders.get(0).getId();

    }

    @AfterEach
    public void tearDown() {
         orderItemRepository.deleteAll();
         orderRepository.deleteAll();
         userRepository.deleteAll();
         categoryRepository.deleteAll();
    }

    @Test
    void testFindById() {
        List<Order> orders = orderRepository.findAll();
        Optional<Order> orderOptional = orderRepository.findById(findingId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Set<OrderItem> orderItems = order.getOrderItems();
            User user = order.getUser();

            assertEquals(user.getId(), allUsers.get(1).getId());
            List<Product> orderProducts = new ArrayList<>();

            for (OrderItem item : orderItems) {
                orderProducts.add(item.getProduct());
            }
            for (Product product : orderProducts) {
                assertTrue(allProducts.contains(product));
            }
        }
    }

    @Test
    void findByUser() {
        List<Order> orders = orderRepository.findByUser_id(allUsers.get(1).getId(), PageRequest.ofSize(100).first());
        assertTrue(!orders.isEmpty());
        orders = orderRepository.findByUser_id(allUsers.get(0).getId(), PageRequest.ofSize(100).first());
        assertTrue(orders.isEmpty());
    }

    @Test
    void findAll() {
        assertTrue(!orderRepository.findAll().isEmpty());
        orderRepository.deleteAll();
        assertTrue(orderRepository.findAll().isEmpty());
    }

    @Test
    void saveOrder() {
        Set<OrderItem> orderItems = new HashSet<>();
        Order order = new Order(null,allUsers.get(1),BigDecimal.valueOf(12),OrderStatus.COMPLETED,"",
                "","",12, new Date(12),orderItems);

        OrderItem orderItem = new OrderItem();
        orderItem.setCount((short) 10);
        orderItem.setProduct(allProducts.get(1));
        orderItem.setOrder(order);
        orderItems.add(orderItem);

        orderRepository.save(order);

        for (OrderItem item : orderItems) {
            orderItemRepository.save(orderItem);
        }

        List<Order> orders = orderRepository.findAll();
        assertTrue(!orders.isEmpty());
        assertTrue(!orderItemRepository.findById(orderItem.getId()).isEmpty());

        List<Order> foundOrders = orderRepository.findByUser_id(allUsers.get(1).getId(), PageRequest.ofSize(100).first());
        assertEquals(allUsers.get(1), foundOrders.get(0).getUser());
        assertEquals(BigDecimal.valueOf(12), foundOrders.get(0).getPrice());

        List<OrderItem> savedOrderItems = orderItemRepository.findAll();
        assertEquals(savedOrderItems.get(0).getProduct(), allProducts.get(1));

    }
}
