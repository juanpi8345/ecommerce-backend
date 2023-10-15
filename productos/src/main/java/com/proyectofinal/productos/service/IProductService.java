package com.proyectofinal.productos.service;

import com.proyectofinal.productos.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    void saveProduct(Product pr);

    void editProduct(Product pr);
    Page<Product> getAll(int page, int pageSize,String sortField);

    List<Product> searchProducts(String query);
    Product getProductByCode(Long code);
    void deleteProductByCode(Long code);

}
