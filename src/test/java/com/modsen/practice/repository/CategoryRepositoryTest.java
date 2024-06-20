package com.modsen.practice.repository;

import com.modsen.practice.entity.Category;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CategoryRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateCategoryAndFindById() {
        Category toSaveCategory = new Category(null,"Saved category",null);
        categoryRepository.save(toSaveCategory);

        assertTrue(categoryRepository.findById(toSaveCategory.getId()).isPresent());
        assertTrue(toSaveCategory.getName().contentEquals(categoryRepository.findById(toSaveCategory.getId()).get().getName()));
    }

    @Test
    void testUpdateCategory() {
        Category newCategory = new Category(null,"New category",null);
        categoryRepository.save(newCategory);

        long categoryID = newCategory.getId();
        newCategory.setName("changed");
        categoryRepository.save(newCategory);

        assertTrue(categoryRepository.findById(categoryID).isPresent());
        assertTrue(categoryRepository.findById(categoryID).get().getName().contentEquals(newCategory.getName()));
        assertEquals(newCategory.getId(), categoryID);
    }

    @Test
    void testDeleteByIdCategory() {
        Category toDeleteCategory = new Category(null,"to delete",null);

        categoryRepository.save(toDeleteCategory);
        categoryRepository.deleteById(toDeleteCategory.getId());

        assertNull(categoryRepository.findById(toDeleteCategory.getId()).orElse(null));
    }

    @Test
    void testDeletedCategory() {
        Category toDeleteCategory = new Category(null,"to delete",null);

        categoryRepository.save(toDeleteCategory);
        categoryRepository.delete(toDeleteCategory);

        assertTrue(categoryRepository.findById(toDeleteCategory.getId()).isEmpty());
    }

    @Test
    void testPaginationAndSorting() {
        List<Category> categories;

        categoryRepository.deleteAll();

        Category category1 = new Category(null,"Cat1",null);
        categoryRepository.save(category1);

        Category category2 = new Category(null,"Cat2",null);
        categoryRepository.save(category2);

        categories = new ArrayList<>(categoryRepository.findAll());

        List<Category> result = categoryRepository.findAll(PageRequest.of(0, categories.size(), Sort.Direction.ASC, "name")).toList();
        categories.sort(Comparator.comparing(Category::getName));
        assertTrue(IntStream.range(0, categories.size())
                .allMatch(i -> categories.get(i).getId().equals(result.get(i).getId())));

        categoryRepository.deleteAll();
    }
}
