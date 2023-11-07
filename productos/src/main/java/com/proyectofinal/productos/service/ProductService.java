package com.proyectofinal.productos.service;

import com.proyectofinal.productos.model.Product;
import com.proyectofinal.productos.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepo;

    @Override
    public void saveProduct(Product pr) {
        productRepo.save(pr);
    }

    @Override
    public void editProduct(Product pr) {
        Product p = productRepo.findById(pr.getCode()).orElse(null);
        if(p!=null){
            p.setSize(pr.getSize());
            p.setName(pr.getName());
            p.setPrice(pr.getPrice());
            p.setDescription(pr.getDescription());
            p.setImgUrl(pr.getImgUrl());
            p.setStock(pr.getStock());
            p.setHasDiscount(pr.isHasDiscount());
            p.setPercentageDiscount(pr.getPercentageDiscount());
            productRepo.save(p);
        }
    }

    @Override
    public Page<Product> getAll(int page, int pageSize,String sortField) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(sortField));
        return productRepo.findAll(pageRequest);
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepo.findByNameContaining(query);
    }

    @Override
    public Product getProductByCode(Long code) {
        return productRepo.findById(code).orElse(null);
    }

    @Override
    public void deleteProductByCode(Long code) {
        productRepo.deleteById(code);
    }
}
