package com.proyectofinal.productos.controller;

import com.proyectofinal.productos.model.Product;
import com.proyectofinal.productos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private IProductService productServ;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/get/{productCode}")
    public Product getProductById(@PathVariable Long productCode){
        return productServ.getProductByCode(productCode);
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts(){
        return productServ.getAll();
    }

    @GetMapping("/get")
    public Page<Product> getAllPaginated(@RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(defaultValue = "name") String sortField){
        System.out.println("Port: "+serverPort);
        return productServ.getAll(page,6,sortField);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("query") String query){
        return productServ.searchProducts(query);
    }
    @PostMapping("/create")
    public void createProduct(@RequestBody Product product){
        productServ.saveProduct(product);
    }

    @PutMapping("/edit")
    public void editProduct(@RequestBody Product product){
        productServ.editProduct(product);
    }

    @PutMapping("/reduceStock/{productCode}")
    public void reduceStock(@PathVariable Long productCode){
        Product pr = productServ.getProductByCode(productCode);
        if(pr != null)
            pr.setStock(pr.getStock()-1);
        productServ.saveProduct(pr);
    }

    @DeleteMapping("/delete/{productCode}")
    public void deleteProduct(@PathVariable Long productCode){
        productServ.deleteProductByCode(productCode);
    }
}
