package com.example.practice_modsen_shop;

import com.example.practice_modsen_shop.entities.Category;
import com.example.practice_modsen_shop.entities.Product;
import com.example.practice_modsen_shop.repository.CategoryRepository;
import com.example.practice_modsen_shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateProductAndFindById() {
        Product toSaveProduct = new Product();
        toSaveProduct.setName("Saved product");

        productRepository.save(toSaveProduct);

        assertTrue(productRepository.findById(toSaveProduct.getId()).isPresent());
        assertTrue(toSaveProduct.getName().contentEquals(productRepository.findById(toSaveProduct.getId()).get().getName()));
    }

    @Test
    void testUpdateProduct() {
        Product newProduct = new Product();
        String newName = "changed";

        newProduct.setName("New product");

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
        Product toDeleteProduct = new Product();
        toDeleteProduct.setName("to delete");

        productRepository.save(toDeleteProduct);
        productRepository.deleteById(toDeleteProduct.getId());

        assertNull(productRepository.findById(toDeleteProduct.getId()).orElse(null));
    }

    @Test
    void testDeletedProduct() {
        Product toDeleteProduct = new Product();
        toDeleteProduct.setName("to delete");

        productRepository.save(toDeleteProduct);
        productRepository.delete(toDeleteProduct);

        assertTrue(productRepository.findById(toDeleteProduct.getId()).isEmpty());
    }

    @Test
    void testPaginationAndSorting() {
        List<Product> products;

        productRepository.deleteAll();

        Product product = new Product();
        product.setName("Пепперони");
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок");
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок барбекю");
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

        products = new ArrayList<>(productRepository.findAll());

        List<Product> result = productRepository.findAll(PageRequest.of(0, products.size(), Sort.Direction.ASC, "name")).toList();
        products.sort(Comparator.comparing(Product::getName));
        assertTrue(IntStream.range(0, products.size())
                .allMatch(i -> products.get(i).getId().equals(result.get(i).getId())));

        productRepository.deleteAll();
    }

    @Test
    void testGetProductsByCategory() {
        Category category = new Category();
        category.setName("cat1");
        categoryRepository.save(category);

        List<Product> products;

        productRepository.deleteAll();

        Product product = new Product();
        product.setName("Пепперони");
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок");
        product.setCategory(category);
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок барбекю");
        product.setCategory(category);
        productRepository.save(product);

        products = new ArrayList<>(productRepository.findAll()).stream()
                .filter(product1 -> {
                    Category tempCategory = product1.getCategory();
                    if (tempCategory != null)
                        return product1.getCategory().getName().contentEquals(category.getName());
                    return false;
                }).toList();
        List<Product> result = productRepository.findByCategory(category);

        assertTrue(IntStream.range(0, products.size())
                .allMatch(i -> products.get(i).getId().equals(result.get(i).getId())));
    }
}
