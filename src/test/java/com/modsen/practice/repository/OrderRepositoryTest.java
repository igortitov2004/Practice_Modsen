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

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

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

    private User testUser;

    private Long findingId;


    void setUpCategories() {
        Category category = new Category();
        category.setName("Cat1");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat2");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat3");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat4");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat5");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat6");
        categoryRepository.save(category);

        category = new Category();
        category.setName("Cat7");
        categoryRepository.save(category);
    }

    void setUpProducts() {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.get(0);

        Product product = new Product();
        product.setName("Пепперони");
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок");
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок барбекю");
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Диабло");
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Бургер");
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setCategory(category);
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        allProducts = productRepository.findAll();
    }

    void setUpUsers() {
        User user = new User();
        user.setFirstname("Первый пользователь");
        user.setBirthDate(new Date(12));
        user.setLastname(" ");
        user.setGender(Gender.MALE);
        user.setLogin("");
        user.setMiddleName("");
        user.setPhoneNumber("");
        user.setPasswordHash("");
        user.setRole(UserRole.CUSTOMER);
        user.setEmail(" ");
        userRepository.save(user);

        user = new User();
        user.setFirstname("Второй пользователь");
        user.setBirthDate(new Date(12));
        user.setLastname(" ");
        user.setGender(Gender.MALE);
        user.setLogin("");
        user.setMiddleName("");
        user.setPhoneNumber("");
        user.setPasswordHash("");
        user.setRole(UserRole.CUSTOMER);
        user.setEmail(" ");
        userRepository.save(user);

        user = new User();
        user.setFirstname("Третий пользователь");
        user.setBirthDate(new Date(12));
        user.setLastname(" ");
        user.setGender(Gender.MALE);
        user.setLogin("");
        user.setMiddleName("");
        user.setPhoneNumber("");
        user.setPasswordHash("");
        user.setRole(UserRole.CUSTOMER);
        user.setEmail(" ");
        userRepository.save(user);

        user = new User();
        user.setFirstname("Четвертый пользователь");
        user.setBirthDate(new Date(12));
        user.setLastname(" ");
        user.setGender(Gender.MALE);
        user.setLogin("");
        user.setMiddleName("");
        user.setPhoneNumber("");
        user.setPasswordHash("");
        user.setRole(UserRole.CUSTOMER);
        user.setEmail(" ");
        userRepository.save(user);

        user = new User();
        user.setFirstname("Пятый пользователь");
        user.setBirthDate(new Date(12));
        user.setLastname(" ");
        user.setGender(Gender.MALE);
        user.setLogin("");
        user.setMiddleName("");
        user.setPhoneNumber("");
        user.setPasswordHash("");
        user.setRole(UserRole.CUSTOMER);
        user.setEmail(" ");
        userRepository.save(user);

        allUsers = userRepository.findAll();

    }

    Set<OrderItem> setUpOrderItemsForOrder() {
        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem item = new OrderItem();
        item.setCount((short) 10);
        item.setProduct(allProducts.get(0));

        OrderItem item1 = new OrderItem();
        item1.setCount((short) 2);
        item1.setProduct(allProducts.get(4));
        orderItems.add(item);
        orderItems.add(item1);
        return orderItems;
    }

    @BeforeEach
    void setUp() {
        setUpCategories();
        setUpProducts();
        setUpUsers();
        Order testOrder = new Order();
        testUser = allUsers.get(2);

        testOrder.setUser(testUser);

        testOrder.setPrice(BigDecimal.valueOf(2000));
        Set<OrderItem> orderItems = setUpOrderItemsForOrder();
        for (OrderItem item : orderItems) {
            item.setOrder(testOrder);
        }
        testOrder.setOrderItems(orderItems);
        testOrder.setStatus(OrderStatus.COMPLETED);
        testOrder.setCity("");
        testOrder.setPrice(BigDecimal.valueOf(12));
        testOrder.setApartmentNumber(12);
        testOrder.setCreationDate(new Date(12));
        testOrder.setStreet("");
        testOrder.setHouseNumber("");
        orderRepository.save(testOrder);
        List<Order> orders = orderRepository.findAll();
        findingId = orders.get(0).getId();

    }

    @AfterEach
    public void tearDown() {
         orderRepository.deleteAll();
         userRepository.deleteAll();
         orderItemRepository.deleteAll();
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

            assertEquals(user.getId(), allUsers.get(2).getId());
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
        List<Order> orders = orderRepository.findByUserId(testUser.getId());
        assertTrue(!orders.isEmpty());
        orders = orderRepository.findByUserId(allUsers.get(0).getId());
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
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();

        Order order = new Order();
        order.setUser(allUsers.get(0));
        order.setStatus(OrderStatus.COMPLETED);
        order.setCity("");
        order.setPrice(BigDecimal.valueOf(12));
        order.setApartmentNumber(12);
        order.setCreationDate(new Date(12));
        order.setStreet("");
        order.setHouseNumber("");

        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setCount((short) 10);
        orderItem.setProduct(allProducts.get(3));
        orderItem.setOrder(order);
        orderItems.add(orderItem);

        orderRepository.save(order);

        for (OrderItem item : orderItems) {
            orderItemRepository.save(orderItem);
        }
        List<Order> orders = orderRepository.findAll();
        assertTrue(!orders.isEmpty());
        assertTrue(!orderItemRepository.findById(orderItem.getId()).isEmpty());

        List<Order> foundOrders = orderRepository.findByUserId(allUsers.get(0).getId());
        assertEquals(allUsers.get(0), foundOrders.get(0).getUser());
        assertEquals(BigDecimal.valueOf(12), foundOrders.get(0).getPrice());

        List<OrderItem> savedOrderItems = orderItemRepository.findAll();
        assertEquals(savedOrderItems.get(0).getProduct(), allProducts.get(3));

    }
}
