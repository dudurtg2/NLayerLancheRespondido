package com.snack.applications;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;

class ProductApplicationTest {
    ProductApplication productApplication;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();

        produtoXBurguer = new Product(1, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        produtoXSalada = new Product(2, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test2.jpg");

        productApplication = new ProductApplication(productRepository, productService);
    }

    @Test
    public void tentarRemoverProdutoIdInexistente() { //8
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
    public void retornarListaCompletaProdutos() { //1

        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        List<Product> products = productApplication.getAll();

        Assertions.assertTrue(products.contains(produtoXBurguer));
        Assertions.assertTrue(products.contains(produtoXSalada));

    }

    @Test
    public void retornarProdutoCorretoFornecerID(){ //2
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        Product product1 = productApplication.getById(1);
        Product product2 = productApplication.getById(2);

        Assertions.assertEquals(product1, produtoXBurguer);
        Assertions.assertEquals(product2, produtoXSalada);
    }
    @Test
    public void atualizarUmProdutoExistenteESubstituirSuaImagem() { //5
        // Arrange (Fizemos no setUp())
        // Act
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        Product produtoXSaladaZ = new Product(2, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test3.jpg");

        productApplication.update(2, produtoXSaladaZ);

        // Assert
        Assertions.assertEquals("x-salada", produtoXSaladaZ.getDescription());
        Assertions.assertEquals(15, produtoXSaladaZ.getPrice());
        Path path = Paths.get(produtoXSaladaZ.getImage());
        Assertions.assertTrue(Files.exists(path));
    }
    @Test
    public void retornarNuloErroTentarObterProdutoIDInvalido(){ //3
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        Product product1 = productApplication.getById(1);
        Product product2 = productApplication.getById(2);


        Assertions.assertEquals(product1, produtoXBurguer);
        Assertions.assertEquals(product2, produtoXSalada);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Product product3 = productApplication.getById(3);
        });
    }

    @Test
    public void verificarUmProdutoExisteIDvalido(){ //4
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);

        Assertions.assertTrue(productApplication.exists(1));
        Assertions.assertTrue(productApplication.exists(2));
    }

    @Test
    public void verificarUmProdutoNaoExisteIDinvalido(){ //4
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);


        Assertions.assertFalse(productApplication.exists(3));
    }

    @Test
    public void adicionarNovoProdutoSalvarImagemCorretamente(){ //6
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);
        Path path = Paths.get(productApplication.getById(1).getImage().toString());
        Assertions.assertTrue(Files.exists(path));

    }


    @Test
    public void tentarRemoverProdutoExistente() { //7
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