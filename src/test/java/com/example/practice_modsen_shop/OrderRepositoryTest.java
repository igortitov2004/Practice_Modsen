package com.example.practice_modsen_shop;


import com.example.practice_modsen_shop.entities.Order;
import com.example.practice_modsen_shop.entities.OrderItem;
import com.example.practice_modsen_shop.entities.Product;
import com.example.practice_modsen_shop.entities.User;
import com.example.practice_modsen_shop.repository.OrderItemRepository;
import com.example.practice_modsen_shop.repository.OrderRepository;
import com.example.practice_modsen_shop.repository.ProductRepository;
import com.example.practice_modsen_shop.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {


        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private OrderItemRepository orderItemRepository;

        private List<Product> allProducts;

        private List<User> allUsers;

        private User testUser;

        private Long findingId;


        void setUpProducts(){
            Product product = new Product();
            product.setName("Пепперони");
            productRepository.save(product);

            product = new Product();
            product.setName("Ципленок");
            productRepository.save(product);

            product = new Product();
            product.setName("Ципленок барбекю");
            productRepository.save(product);

            product = new Product();
            product.setName("Диабло");
            productRepository.save(product);

            product = new Product();
            product.setName("Бургер");
            productRepository.save(product);

            product = new Product();
            product.setName("Деревенская");
            productRepository.save(product);

            product = new Product();
            product.setName("4 сезона");
            productRepository.save(product);

            allProducts = productRepository.findAll();
        }

        void setUpUsers(){
            User user = new User();
            user.setFirstname("Первый пользователь");
            userRepository.save(user);

            user = new User();
            user.setFirstname("Второй пользователь");
            userRepository.save(user);

            user = new User();
            user.setFirstname("Третий пользователь");
            userRepository.save(user);

            user = new User();
            user.setFirstname("Четвертый пользователь");
            userRepository.save(user);

            user = new User();
            user.setFirstname("Пятый пользователь");
            userRepository.save(user);

            allUsers = userRepository.findAll();

        }

        Set<OrderItem> setUpOrderItemsForOrder(){
            Set<OrderItem> orderItems = new HashSet<>();
            OrderItem item = new OrderItem();
            item.setCount((short)10);
            item.setProduct(allProducts.get(0));

            OrderItem item1 = new OrderItem();
            item1.setCount((short)2);
            item1.setProduct(allProducts.get(4));
            orderItems.add(item);
            orderItems.add(item1);
            return  orderItems;
        }

        @BeforeEach
        void setUp() {
            setUpProducts();

            setUpUsers();

            Order testOrder = new Order();
            testUser = allUsers.get(2);

            testOrder.setUser(testUser);

            testOrder.setPrice(BigDecimal.valueOf(2000));
            Set<OrderItem> orderItems = setUpOrderItemsForOrder();
            for(OrderItem item:orderItems)
            {
                item.setOrder(testOrder);
            }
            testOrder.setOrderItems(orderItems);
            orderRepository.save(testOrder);
            List<Order> orders = orderRepository.findAll();
            findingId = orders.get(0).getId();

        }

        @AfterEach
        public void tearDown()
        {
            orderRepository.deleteAll();
            userRepository.deleteAll();
            orderItemRepository.deleteAll();
        }

        @Test
        void testFindById() {
                List<Order> orders = orderRepository.findAll();
                Optional<Order> orderOptional = orderRepository.findById(findingId);
                if(orderOptional.isPresent()) {
                    Order order =orderOptional.get();
                    Set<OrderItem> orderItems = order.getOrderItems();
                    User user = order.getUser();

                    assertEquals(user.getId(),allUsers.get(2).getId());
                    List<Product> orderProducts = new ArrayList<>();

                    for(OrderItem item:orderItems)
                    {
                        orderProducts.add(item.getProduct());
                    }
                    for(Product product:orderProducts)
                    {
                        assertTrue(allProducts.contains(product));
                    }
                }
        }

        @Test
        void findByUser(){
            List<Order> orders = orderRepository.findByUser(testUser);
            assertTrue(!orders.isEmpty());
            orders = orderRepository.findByUser(allUsers.get(0));
            assertTrue(orders.isEmpty());
        }

        @Test
        void findAll(){
            assertTrue(!orderRepository.findAll().isEmpty());
            orderRepository.deleteAll();
            assertTrue(orderRepository.findAll().isEmpty());
        }

        @Test
        void saveOrder()
        {
            orderRepository.deleteAll();
            orderItemRepository.deleteAll();

            Order order = new Order();
            order.setUser(allUsers.get(0));
            order.setPrice(BigDecimal.valueOf(12));

            Set<OrderItem> orderItems = new HashSet<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setCount((short) 10);
            orderItem.setProduct(allProducts.get(3));
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            order.setOrderItems(orderItems);

            orderRepository.save(order);

            assertTrue(!orderRepository.findAll().isEmpty());
            assertTrue(!orderItemRepository.findAll().isEmpty());

            List<Order> foundOrders = orderRepository.findByUser(allUsers.get(0));
            assertEquals(allUsers.get(0),foundOrders.get(0).getUser());
            assertEquals(BigDecimal.valueOf(12),foundOrders.get(0).getPrice());

            List<OrderItem> savedOrderItems = orderItemRepository.findAll();
            assertEquals(savedOrderItems.get(0).getProduct(),allProducts.get(3));

        }
    }
