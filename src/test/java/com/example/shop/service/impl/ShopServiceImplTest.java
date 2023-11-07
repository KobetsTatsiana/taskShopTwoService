package com.example.shop.service.impl;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.shop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        shopService = new ShopServiceImpl();
        restTemplate = mock(RestTemplate.class);
        shopService.setRestTemplate(restTemplate);
    }

    @Test
    public void testGetAll() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 1", 10, 10));
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 2", 5, 5));

        ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(expectedProducts, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(shopService.getUrlShopService()),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Product>>() {})
        )).thenReturn(responseEntity);

        List<Product> actualProducts = shopService.getAll();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testUpdateGoods() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Product 1", 10, 10));
        products.add(new Product(UUID.randomUUID(), "Product 2", 5, 5));

        HttpEntity<List<Product>> requestEntity = new HttpEntity<>(products);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Updated", HttpStatus.OK);

        when(restTemplate.exchange(
                eq(shopService.getUrlShopService()),
                eq(HttpMethod.PUT),
                eq(requestEntity),
                eq(String.class)
        )).thenReturn(responseEntity);

        shopService.updateGoods(products);

        // Assert if the update was successful
    }

    @Test
    public void testCheck() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Product 1", 2, 2));
        products.add(new Product(UUID.randomUUID(), "Product 2", 3, 3));

        List<Product> existGoods = new ArrayList<>();
        existGoods.add(new Product(products.get(0).getUuid(), "Product 1", 5, 5));
        existGoods.add(new Product(products.get(1).getUuid(), "Product 2", 2, 2));

        when(restTemplate.exchange(
                eq(shopService.getUrlShopService()),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Product>>() {})
        )).thenReturn(new ResponseEntity<>(existGoods, HttpStatus.OK));

        shopService.check(products);

        // Assert that the products were checked and updated correctly
        assertEquals(3, existGoods.get(0).getQuantity());
        assertEquals(0, existGoods.get(1).getQuantity());
    }
}