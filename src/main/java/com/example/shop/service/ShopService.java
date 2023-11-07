package com.example.shop.service;

import com.example.shop.model.Product;

import java.util.List;

public interface ShopService {
    List<Product> getAll();

    void check(List<Product> products);

//добавлено для тестов
    void setUrlShopService(String urlShopService);

    void setUrlOrderService(String urlOrderService);

    String getUrlShopService();

    String getUrlOrderService();

}
