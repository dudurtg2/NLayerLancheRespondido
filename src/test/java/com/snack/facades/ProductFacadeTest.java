package com.snack.facades;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductFacadeTest {
    ProductFacade productFacade;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();
        ProductApplication productApplication = new ProductApplication(productRepository, productService);
        productFacade = new ProductFacade(productApplication);

        produtoXBurguer = new Product(10, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        produtoXSalada = new Product(20, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
    }

    @Test
    public void testarListaCompletaProdutosChamarMetodoGetAll() {
        // Arrange (fiz no setup())

        // Act
        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);
        List<Product> produtos = productFacade.getAll();

        // Assert
        Assertions.assertEquals(2, produtos.size());
    }
}
