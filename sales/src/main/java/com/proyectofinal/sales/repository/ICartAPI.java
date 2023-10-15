package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface ICartAPI {
    @GetMapping("cart/get/{cartId}")
    public CartDTO getCart(@PathVariable Long cartId);
}
