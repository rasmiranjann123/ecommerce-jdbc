package com.kodewala.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kodewala.ecommerce.model.Product;

public class ProductRepositoryTest {
	
	private final ProductRepository repository =
            new ProductRepository();

    @Test
    void shouldFetchAllProducts() throws SQLException {

        List<Product> products = repository.findAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());

        products.forEach(System.out::println);
    }

    @Test
    void shouldFindProductById() throws SQLException {

        Product product = repository.findById(1);

        assertNotNull(product);
        assertEquals(1, product.getProductId());
    }

}
