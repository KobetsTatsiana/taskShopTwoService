package com.example.shop.controller;

import com.example.shop.model.Product;
import com.example.shop.sevice.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShopControllerTest {

    @Mock
    private ShopServiceImpl shopService;

    @InjectMocks
    private ShopController shopController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGoods() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(shopService.getAll()).thenReturn(products);

        List<Product> result = shopController.getAllGoods();

        assertEquals(products, result);
        verify(shopService, times(1)).getAll();
    }

    @Test
    void testSaveAllGoodsSuccess() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        ResponseEntity<String> responseEntity = shopController.saveAllGoods(products);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Заказ получен", responseEntity.getBody());
        verify(shopService, times(1)).check(products);
    }

    @Test
    void testSaveAllGoodsError() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        doThrow(new RuntimeException("Error saving products")).when(shopService).check(products);

        ResponseEntity<String> responseEntity = shopController.saveAllGoods(products);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error saving products", responseEntity.getBody());
        verify(shopService, times(1)).check(products);
    }
}