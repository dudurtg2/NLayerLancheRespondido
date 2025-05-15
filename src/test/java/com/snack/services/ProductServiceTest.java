package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductServiceTest {
    ProductService productService;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        produtoXBurguer = new Product(50, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        produtoXSalada = new Product(50, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        productService = new ProductService();
    }

    @Test
    public void atualizarUmProdutoExistente() {
        // Arrange (Fizemos no setUp())

        // Act (Chamando os métodos)
        productService.save(produtoXBurguer);
        productService.update(produtoXSalada);

        // Assert
        Assertions.assertEquals("x-salada", produtoXSalada.getDescription());
        Assertions.assertEquals(15, produtoXSalada.getPrice());
        Path path = Paths.get(produtoXSalada.getImage());
        Assertions.assertTrue(Files.exists(path));
    }

    @Test
    public void salvarUmProdutoComImagemValida() {
        // Act
        boolean result = productService.save(produtoXBurguer);

        // Assert
        Assertions.assertTrue(result);
        Path path = Paths.get(produtoXBurguer.getImage());
        Assertions.assertTrue(Files.exists(path));
    }

    @Test
    public void salvarUmProdutoComImagemInexistente() {
        // Arrange
        Product produtoInvalido = new Product(60, "produto-invalido", 20, "C:\\Users\\dudur\\Pictures\\aula\\test.png");

        // Act
        boolean result = productService.save(produtoInvalido);

        // Assert
        Assertions.assertFalse(result);
    }

   

    @Test
    public void removerUmProdutoExistente() {
        // Act
        productService.save(produtoXBurguer);
        productService.remove(produtoXBurguer.getId());

        // Assert
        Path path = Paths.get(produtoXBurguer.getImage());
        Assertions.assertFalse(Files.exists(path));
    }

    @Test
    public void obterCaminhoDaImagemPorId() {
        // Arrange
        productService.save(produtoXBurguer);

        // Act
        String imagePath = productService.getImagePathById(produtoXBurguer.getId());

        // Assert
        Assertions.assertEquals(produtoXBurguer.getImage(), imagePath);
    }

}
