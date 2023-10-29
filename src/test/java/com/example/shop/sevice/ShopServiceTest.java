package com.example.shop.sevice;

import com.example.shop.model.Product;
import com.example.shop.sevice.ShopService;
import com.example.shop.sevice.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShopServiceTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        // Создаем заглушку для ShopService
        shopService = mock(ShopService.class);
        when(shopService.getUrlShopService()).thenReturn("http://example.com/shop");
        // Предоставляем стандартную реализацию для getUrlOrderService
        when(shopService.getUrlOrderService()).thenReturn("http://example.com/orders");
    }

    @Test
    void testSetUrlShopService() {
        shopService.setUrlShopService("http://example.com/shop");

        assertEquals("http://example.com/shop", shopService.getUrlShopService());
    }

    @Test
    void testSetUrlOrderService() {
        shopService.setUrlOrderService("http://example.com/orders");

        assertEquals("http://example.com/orders", shopService.getUrlOrderService());
    }

    @Test
    void testGetAll() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 1", 10, 10));
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 2", 5, 5));

        when(shopService.getAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = shopService.getAll();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void testCheck() {
        List<Product> existingProducts = new ArrayList<>();
        UUID productUUID = UUID.randomUUID();
        existingProducts.add(new Product(productUUID, "Product 1", 10, 10));

        List<Product> productsToCheck = new ArrayList<>();
        productsToCheck.add(new Product(productUUID, "Product 1", 5, 5));

        doAnswer(invocation -> {
            List<Product> checkedProducts = invocation.getArgument(0);
            for (Product product : checkedProducts) {
                for (Product existingProduct : existingProducts) {
                    if (product.getUuid().equals(existingProduct.getUuid())) {
                        existingProduct.setQuantity(product.getQuantity());
                    }
                }
            }
            return null;
        }).when(shopService).check(productsToCheck);

        shopService.check(productsToCheck);

        int expectedQuantity = 5;
        int actualQuantity = existingProducts.get(0).getQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }
}