package com.example.shop.sevice;

import com.example.shop.model.Product;

import java.util.List;

public interface ShopService {

    void setUrlShopService(String urlShopService);

    void setUrlOrderService(String urlOrderService);

    String getUrlShopService();

    String getUrlOrderService();

    List<Product> getAll();

    void check(List<Product> products);
}
