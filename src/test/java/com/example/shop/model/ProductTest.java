package com.example.shop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void testUuid() {
        UUID uuid = UUID.randomUUID();
        product.setUuid(uuid);
        assertEquals(uuid, product.getUuid());
    }

    @Test
    void testName() {
        String name = "Test Product";
        product.setName(name);
        assertEquals(name, product.getName());
    }

    @Test
    void testPrice() {
        int price = 100;
        product.setPrice(price);
        assertEquals(price, product.getPrice());
    }

    @Test
    void testQuantity() {
        int quantity = 10;
        product.setQuantity(quantity);
        assertEquals(quantity, product.getQuantity());
    }
    @Test
    public void testToString() {
        UUID uuid = UUID.randomUUID();
        String name = "Test Product";
        int price = 100;
        int quantity = 10;
        Product product = new Product(uuid, name, price, quantity);
        String expected = "Product{name='Test Product'}";
        String actual = product.toString();
        assertEquals(expected, actual);
    }
}