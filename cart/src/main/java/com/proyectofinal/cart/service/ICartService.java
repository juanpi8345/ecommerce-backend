package com.proyectofinal.cart.service;

import com.proyectofinal.cart.dto.CartDTO;

import java.util.List;

public interface ICartService {
    void addProduct(Long cartId, Long productCode);
    void deleteProduct(Long cartId, Long productCode);
    void createCart(List<Long> productsCode);
    void deleteCart(Long cartId);

    CartDTO getCart(Long cartId);
}
