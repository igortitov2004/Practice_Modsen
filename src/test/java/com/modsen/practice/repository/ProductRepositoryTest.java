package com.modsen.practice.repository;

import com.modsen.practice.entity.Category;
import com.modsen.practice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp()
    {
        Category newCategory = new Category();
        newCategory.setName("preparedCategory");
        categoryRepository.save(newCategory);
    }
    @Test
    void testCreateProductAndFindById() {
        Product toSaveProduct = new Product();
        toSaveProduct.setName("Saved product");
        toSaveProduct.setIngredients(" ");
        toSaveProduct.setDescription(" ");
        toSaveProduct.setWeight((short) 1);
        toSaveProduct.setCaloricValue((short) 12);
        toSaveProduct.setPrice(BigDecimal.valueOf(12));
        toSaveProduct.setCategory(categoryRepository.findAll().get(0));
        productRepository.save(toSaveProduct);

        assertTrue(productRepository.findById(toSaveProduct.getId()).isPresent());
        assertTrue(toSaveProduct.getName().contentEquals(productRepository.findById(toSaveProduct.getId()).get().getName()));
    }

    @Test
    void testUpdateProduct() {
        Product newProduct = new Product();
        String newName = "changed";
        newProduct.setIngredients(" ");
        newProduct.setDescription(" ");
        newProduct.setWeight((short) 1);
        newProduct.setCaloricValue((short) 12);
        newProduct.setPrice(BigDecimal.valueOf(12));
        newProduct.setCategory(categoryRepository.findAll().get(0));
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
        toDeleteProduct.setIngredients(" ");
        toDeleteProduct.setDescription(" ");
        toDeleteProduct.setWeight((short) 1);
        toDeleteProduct.setCaloricValue((short) 12);
        toDeleteProduct.setPrice(BigDecimal.valueOf(12));
        toDeleteProduct.setCategory(categoryRepository.findAll().get(0));

        productRepository.save(toDeleteProduct);
        productRepository.deleteById(toDeleteProduct.getId());

        assertNull(productRepository.findById(toDeleteProduct.getId()).orElse(null));
    }

    @Test
    void testDeletedProduct() {
        Product toDeleteProduct = new Product();
        toDeleteProduct.setName("to delete");
        toDeleteProduct.setIngredients(" ");
        toDeleteProduct.setDescription(" ");
        toDeleteProduct.setWeight((short) 1);
        toDeleteProduct.setCaloricValue((short) 12);
        toDeleteProduct.setPrice(BigDecimal.valueOf(12));
        toDeleteProduct.setCategory(categoryRepository.findAll().get(0));

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
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Цыпленок барбекю");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Диабло");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Бургер");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("Деревенская");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
        productRepository.save(product);

        product = new Product();
        product.setName("4 сезона");
        product.setCategory(categoryRepository.findAll().get(0));
        product.setIngredients(" ");
        product.setDescription(" ");
        product.setWeight((short) 1);
        product.setCaloricValue((short) 12);
        product.setPrice(BigDecimal.valueOf(12));
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
        product.setCategory(categoryRepository.findAll().get(0));
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
