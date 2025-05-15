package com.snack.repositories;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snack.entities.Product;

public class ProductRepositoryTest {
    @Test
    void testAppend() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product);

        Assertions.assertEquals(1, productRepository.getAll().size());
        Assertions.assertEquals(product, productRepository.getAll().get(0));
    }
    
    @Test
    void testExists() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product);

        Assertions.assertTrue(productRepository.exists(1));
        Assertions.assertFalse(productRepository.exists(2));
    }
    @Test
    void testGetById() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product);

        Product productById = productRepository.getById(1);

        Assertions.assertNotNull(productById);
        Assertions.assertEquals(product, productById);
    }
    @Test
    void testRemove() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product);
        productRepository.remove(1);

        Assertions.assertFalse(productRepository.exists(1));
        Assertions.assertEquals(0, productRepository.getAll().size());
    }

    @Test
    void testUpdate() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product);

        Product updatedProduct = new Product(1, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.update(1, updatedProduct);

        Product productById = productRepository.getById(1);
        Assertions.assertEquals(updatedProduct.getDescription(), productById.getDescription());
        Assertions.assertEquals(updatedProduct.getPrice(), productById.getPrice());
        Assertions.assertEquals(updatedProduct.getImage(), productById.getImage());
    }

    @Test
    void testGetAll() {
        ProductRepository productRepository = new ProductRepository();
        Product product1 = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        Product product2 = new Product(2, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product1);
        productRepository.append(product2);

        List<Product> products = productRepository.getAll();
        Assertions.assertEquals(2, products.size());
        Assertions.assertTrue(products.contains(product1));
        Assertions.assertTrue(products.contains(product2));
    }

    @Test
    void testRemoveNonExistentProduct() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.remove(1);

        Assertions.assertEquals(0, productRepository.getAll().size());
    }

    @Test
    void testUpdateNonExistentProduct() {
        ProductRepository productRepository = new ProductRepository();
        Product product = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productRepository.update(1, product);
        });
    }

    @Test
    void testDuplicateId() {
        ProductRepository productRepository = new ProductRepository();
        Product product1 = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        Product product2 = new Product(1, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productRepository.append(product1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productRepository.append(product2);
        });
    }

    @Test
    void testEmptyRepository() {
        ProductRepository productRepository = new ProductRepository();
        
        Assertions.assertEquals(0, productRepository.getAll().size());
    }

}
