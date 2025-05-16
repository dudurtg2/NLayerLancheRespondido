package com.snack.repositories;

import java.util.List;
import java.util.NoSuchElementException;

import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.snack.entities.Product;

public class ProductRepositoryTest {
    ProductService productService;
    Product produtoXBurguer;
    Product produtoXBurguerNew;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        produtoXBurguer = new Product(50, "x-burguer", 10, "C:\\Users\\dudur\\Pictures\\aula\\test.png");
        produtoXBurguerNew = new Product(50, "x-burguer", 15, "C:\\Users\\dudur\\Pictures\\aula\\test2.jpg");
        produtoXSalada = new Product(51, "x-salada", 15, "C:\\Users\\dudur\\Pictures\\aula\\test3.jpg");
        productService = new ProductService();
    }

    @Test
    void adicionadoCorretamenteAoRepositorio() {
        ProductRepository productRepository = new ProductRepository();

        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);

        Assertions.assertEquals(2, productRepository.getAll().size());
        Assertions.assertEquals(produtoXBurguer, productRepository.getAll().get(0));
    }
    
    @Test
    void recuperarUmProdutoUsandoSeuID() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);

        Assertions.assertTrue(productRepository.exists(51));
        Assertions.assertFalse(productRepository.exists(1));
    }
    @Test
    void produtoExisteNoRepositorio() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);

        Product productById = productRepository.getById(51);

        Assertions.assertNotNull(productById);
        Assertions.assertEquals(produtoXSalada, productById);
    }
    @Test
    void  produtoRemovidoCorretamenteDoRepositorio () {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);
        productRepository.remove(51);

        Assertions.assertFalse(productRepository.exists(51));
        Assertions.assertTrue(productRepository.exists(50));

        Assertions.assertEquals(1, productRepository.getAll().size());

    }

    @Test
    void produtoAtualizadoCorretamente() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);

        productRepository.update(50, produtoXBurguerNew);

        Product productById = productRepository.getById(50);

        Assertions.assertEquals(produtoXBurguer.getDescription(), productById.getDescription());
        Assertions.assertEquals(produtoXBurguer.getPrice(), productById.getPrice());
        Assertions.assertEquals(produtoXBurguer.getImage(), productById.getImage());
    }

    @Test
    void todosProdutosArmazenadosRecuperadosCorretamente() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);

        List<Product> products = productRepository.getAll();
        Assertions.assertEquals(2, products.size());
        Assertions.assertTrue(products.contains(produtoXBurguer));
        Assertions.assertTrue(products.contains(produtoXSalada));
    }

    @Test
    void VerificarComportamentoTentarRemoverProdutoNaoExiste() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);
        productRepository.remove(1);

        Assertions.assertEquals(2, productRepository.getAll().size());
    }

    @Test
    void testarAconteceTentarAtualizarProdutoNaoEstaRepositorio() {
        ProductRepository productRepository = new ProductRepository();
        productRepository.append(produtoXBurguer);
        productRepository.append(produtoXSalada);
        
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productRepository.update(1, produtoXBurguerNew);
        });
    }

    @Test
    void VerificarRepositorioAceitaAdicaoProdutosComIDsDuplicados() {
        ProductRepository productRepository = new ProductRepository();

        productRepository.append(produtoXBurguerNew);
        productRepository.append(produtoXBurguer);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productRepository.append(produtoXBurguer);
        });
    }

    @Test
    void confirmarQueRepositorioRetornaListaVaziaSerInicializado() {
        ProductRepository productRepository = new ProductRepository();
        
        Assertions.assertEquals(0, productRepository.getAll().size());
    }

}
