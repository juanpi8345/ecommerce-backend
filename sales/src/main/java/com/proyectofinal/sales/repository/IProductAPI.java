package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "products-service")
public interface IProductAPI {
    @GetMapping("products/getAll")
    List<ProductDTO> getProducts();
    @GetMapping("products/get/{productCode}")
    ProductDTO getProductById(@PathVariable Long productCode);

    @PutMapping("products/reduceStock/{productCode}")
    public void reduceStock(@PathVariable Long productCode);

}
