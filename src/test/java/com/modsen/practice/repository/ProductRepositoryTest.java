package com.modsen.practice.repository;

import com.modsen.practice.entity.Category;
import com.modsen.practice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ProductRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        Category newCategory = new Category(null,"preparedCategory",null);
        categoryRepository.save(newCategory);
    }

    @Test
    void testCreateProductAndFindById() {
        Product toSaveProduct = new Product(null,categoryRepository.findAll().get(0),"Saved product"," ",
                BigDecimal.valueOf(12), " ",(short)1,(short)12,null);
        productRepository.save(toSaveProduct);

        assertTrue(productRepository.findById(toSaveProduct.getId()).isPresent());
        assertTrue(toSaveProduct.getName().contentEquals(productRepository.findById(toSaveProduct.getId()).get().getName()));
    }

    @Test
    void testUpdateProduct() {
        Product newProduct = new Product(null,categoryRepository.findAll().get(0),"New product"," ",
                BigDecimal.valueOf(12), " ",(short)1,(short)12,null);
        String newName = "changed";

        productRepository.save(newProduct);
        long productID = newProduct.getId();

        newProduct.setName(newName);
        productRepository.save(newProduct);

        assertTrue(productRepository.findById(productID).isPresent());
        assertTrue(productRepository.findById(productID).get().getName().contentEquals(newProduct.getName()));
        assertEquals(newProduct.getId(), productID);
    }

    @Test
    void testDeleteByIdProduct() {
        Product toDeleteProduct = new Product(null,categoryRepository.findAll().get(0),"to delete"," ",
                BigDecimal.valueOf(12), " ",(short)1,(short)12,null);;

        productRepository.save(toDeleteProduct);
        productRepository.deleteById(toDeleteProduct.getId());

        assertNull(productRepository.findById(toDeleteProduct.getId()).orElse(null));
    }

    @Test
    void testDeletedProduct() {
        Product toDeleteProduct = new Product(null, categoryRepository.findAll().get(0), "to delete",
                " ", BigDecimal.valueOf(12), " ", (short) 1, (short) 12, null);

        productRepository.save(toDeleteProduct);
        productRepository.delete(toDeleteProduct);

        assertTrue(productRepository.findById(toDeleteProduct.getId()).isEmpty());
    }

    @Test
    void testPaginationAndSorting() {
        List<Product> products;

        productRepository.deleteAll();

        Product product1 = new Product(null, categoryRepository.findAll().get(0), "Product1", " ",
                BigDecimal.valueOf(12), " ", (short) 1, (short) 12, null);
        productRepository.save(product1);

        Product product2 = new Product(null, categoryRepository.findAll().get(0), "Product2", " ",
                BigDecimal.valueOf(12), " ", (short) 1, (short) 12, null);
        productRepository.save(product2);

        products = new ArrayList<>(productRepository.findAll());

        List<Product> result = productRepository.findAll(PageRequest.of(0, products.size(),
                Sort.Direction.ASC, "name")).toList();
        products.sort(Comparator.comparing(Product::getName));
        assertTrue(IntStream.range(0, products.size())
                .allMatch(i -> products.get(i).getId().equals(result.get(i).getId())));

        productRepository.deleteAll();
    }

    @Test
    void testGetProductsByCategory() {
        Category category = new Category(null, "cat1", null);
        categoryRepository.save(category);

        List<Product> products;
        productRepository.deleteAll();

        Product product1 = new Product(null, category, "Product1", " ", BigDecimal.valueOf(12),
                " ", (short) 1, (short) 12, null);
        productRepository.save(product1);

        Product product2 = new Product(null, category, "Product2", " ", BigDecimal.valueOf(12),
                " ", (short) 1, (short) 12, null);
        productRepository.save(product2);

        products = new ArrayList<>(productRepository.findAll()).stream()
                .filter(product -> {
                    Category tempCategory = product.getCategory();
                    if (tempCategory != null)
                        return product.getCategory().getName().contentEquals(category.getName());
                    return false;
                }).toList();
        List<Product> result = productRepository.findByCategory_id(category.getId(), PageRequest.ofSize(100).first());

        assertTrue(IntStream.range(0, products.size())
                .allMatch(i -> products.get(i).getId().equals(result.get(i).getId())));
    }
}
