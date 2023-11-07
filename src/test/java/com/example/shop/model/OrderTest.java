package com.example.shop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }

    @Test
    public void testUuid() {
        UUID uuid = UUID.randomUUID();
        order.setUuid(uuid);
        assertEquals(uuid, order.getUuid());
    }

    @Test
    public void testTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setTimestamp(timestamp);
        assertEquals(timestamp, order.getTimestamp());
    }

    @Test
    public void testSum() {
        Integer sum = 100;
        order.setSum(sum);
        assertEquals(sum, order.getSum());
    }

    @Test
    public void testClientId() {
        UUID clientId = UUID.randomUUID();
        order.setClientId(clientId);
        assertEquals(clientId, order.getClientId());
    }

    @Test
    public void testProducts() {
        List<String> products = new ArrayList<>();
        products.add("Product 1");
        products.add("Product 2");
        order.setProducts(products);
        assertEquals(products, order.getProducts());
    }

    @Test
    public void testEqualsAndHashCode() {
        Order order1 = new Order();
        order1.setUuid(UUID.randomUUID());
        order1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        order1.setSum(100);
        order1.setClientId(UUID.randomUUID());

        List<String> products1 = new ArrayList<>();
        products1.add("Product 1");
        products1.add("Product 2");
        order1.setProducts(products1);

        Order order2 = new Order();
        order2.setUuid(order1.getUuid());
        order2.setTimestamp(order1.getTimestamp());
        order2.setSum(order1.getSum());
        order2.setClientId(order1.getClientId());

        List<String> products2 = new ArrayList<>(order1.getProducts());
        order2.setProducts(products2);

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }
}