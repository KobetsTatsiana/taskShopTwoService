package com.example.shop.sevice.impl;

import com.example.shop.model.Product;
import com.example.shop.sevice.ShopService;
import com.example.shop.sevice.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShopServiceImplTest {

    @InjectMocks
    private ShopServiceImpl shopService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ShopServiceImpl shopService = new ShopServiceImpl(); // Создаем реальный объект ShopServiceImpl

        // Задаем значения для свойств urlShopService и urlOrderService
        shopService.setUrlShopService("http://localhost:8080/GoodsService/goods");
        shopService.setUrlOrderService("http://other-service-url/create");

        // Присвоим shopService созданному объекту
        this.shopService = shopService;
    }

    @Test
    void testGetAll() {
        // Задаем ожидаемый результат
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 1", 10, 10));
        expectedProducts.add(new Product(UUID.randomUUID(), "Product 2", 5, 5));

        // Задаем поведение мок-объекта restTemplate
        when(restTemplate.exchange(
                eq("http://localhost:8080/GoodsService/goods"),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<Product>>() {
                })
        )).thenReturn(ResponseEntity.ok(expectedProducts));

        // Вызываем метод getAll() на объекте shopService
        List<Product> actualProducts = shopService.getAll();

        // Проверяем, что полученные данные совпадают с ожидаемыми
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void testSetUrlShopService() {
        // Устанавливаем URL магазина
        shopService.setUrlShopService("http://example.com/shop");

        // Получаем URL магазина с помощью геттера и проверяем, что он был установлен правильно
        assertEquals("http://example.com/shop", shopService.getUrlShopService());
    }

    @Test
    void testSetUrlOrderService() {
        // Устанавливаем URL для службы заказов
        shopService.setUrlOrderService("http://example.com/orders");

        // Получаем URL службы заказов с помощью геттера и проверяем, что он был установлен правильно
        assertEquals("http://example.com/orders", shopService.getUrlOrderService());
    }
}