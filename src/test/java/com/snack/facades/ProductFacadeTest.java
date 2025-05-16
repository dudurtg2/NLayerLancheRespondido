package com.snack.facades;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;

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
        produtoXSalada = new Product(20, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test2.jpg");
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

    @Test
    public void retornarProdutoCorretoFornecerID() { //2
        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);

        Product product1 = productFacade.getById(10);
        Product product2 = productFacade.getById(20);

        Assertions.assertEquals(product1, produtoXBurguer);
        Assertions.assertEquals(product2, produtoXSalada);
    }

    @Test
    public void retornarTrueParaIDExistenteFalseParaIDInexistente() { //2
        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);

        Assertions.assertTrue(productFacade.exists(10));
        Assertions.assertFalse(productFacade.exists(11));

    }

    @Test
    public void adicionarNovoProdutoCorretamenteAoChamarAppend() {

        productFacade.append(produtoXBurguer);
        List<Product> produtos = productFacade.getAll();

        Assertions.assertTrue(produtos.contains(produtoXBurguer));
        Assertions.assertEquals(1, produtos.size());
    }

    @Test
    public void removerUmProdutoExistenteAoFornecerUmIDValidoNoMetodoRemove() {

        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);

        productFacade.remove(10);

        Assertions.assertEquals(1, productFacade.getAll().size());
        Assertions.assertFalse(productFacade.exists(10));
    }

}
