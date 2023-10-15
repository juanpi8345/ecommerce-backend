package com.proyectofinal.cart.repository;

import com.proyectofinal.cart.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service")
public interface IProductAPI {
    @GetMapping("products/get/{productCode}")
    public ProductDTO getProductById(@PathVariable Long productCode);
}
