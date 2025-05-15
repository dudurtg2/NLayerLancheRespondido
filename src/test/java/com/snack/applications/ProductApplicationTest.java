package com.snack.applications;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductApplicationTest {
    ProductApplication productApplication;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();

        produtoXBurguer = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        produtoXSalada = new Product(2, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");

        productApplication = new ProductApplication(productRepository, productService);
    }

    @Test
    public void tentarRemoverProdutoIdInexistente() {
        // Arrange (Fizemos no setUp())
        // Act
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        // Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productApplication.remove(3);
        });
    }

    @Test
    public void tentarRemoverProdutoExistente() {
        // Arrange (Fizemos no setUp())
        Product produtoXBurguerZ = new Product(120, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        Product produtoXSaladaZ = new Product(120, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");

        // Act
        productApplication.append(produtoXBurguerZ);
        productApplication.append(produtoXSaladaZ);
        productApplication.remove(120);

        Path path = Paths.get(produtoXBurguerZ.getImage());
        Assertions.assertFalse(Files.exists(path));
    }
}