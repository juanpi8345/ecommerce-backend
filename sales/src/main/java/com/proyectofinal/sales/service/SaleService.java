package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.ProductDTO;
import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.dto.UserDTO;
import com.proyectofinal.sales.model.Sale;
import com.proyectofinal.sales.repository.IProductAPI;
import com.proyectofinal.sales.repository.ISaleRepository;
import com.proyectofinal.sales.repository.IUserAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private IProductAPI productApi;

    @Autowired
    private IUserAPI userApi;

    @Override
    @CircuitBreaker(name = "product-service",fallbackMethod = "fallBack")
    @Retry(name="product-service")
    public void saveSale(Sale sale) {
        List<Long> filterList = new ArrayList<Long>();
        UserDTO us = this.getUser(sale.getUserDni());
        if(us != null){
            for(Long prId : sale.getProductsId()){
                ProductDTO pr = productApi.getProductById(prId);
                if(pr != null){
                    filterList.add(pr.getCode());
                    sale.setTotal(sale.getTotal()+ pr.getPrice());
                }
            }
            if(!sale.getProductsId().isEmpty()){
                sale.setProductsId(filterList);
                sale.setDate(LocalDate.now());
                sale.setPaid(false);
                sale.setReady(false);
                saleRepo.save(sale);
            }
        }

    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepo.deleteById(saleId);
    }

    @Override
    @CircuitBreaker(name = "product-service",fallbackMethod = "fallBack")
    @Retry(name="product-service")
    public SaleDTO getSale(Long saleId) {
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        Sale sale  = saleRepo.findById(saleId).orElse(null);
        for(Long prId:sale.getProductsId()){
            ProductDTO pr = productApi.getProductById(prId);
            products.add(pr);
        }

        SaleDTO saleResponse = new SaleDTO();
        if(sale != null){
            UserDTO user = this.getUser(sale.getUserDni());
            saleResponse.setId(sale.getId());
            saleResponse.setDate(sale.getDate());
            saleResponse.setProducts(products);
            saleResponse.setTotal(sale.getTotal());
            saleResponse.setPaid(sale.isPaid());
            saleResponse.setReady(sale.isReady());
            saleResponse.setUser(user);
            saleResponse.setType(sale.getType());
            saleResponse.setResidence(sale.getResidence());
        }
        return saleResponse;
    }

    @CircuitBreaker(name = "user-service",fallbackMethod = "fallBack")
    @Retry(name="user-service")
    @Override
    public UserDTO getUser(String dni) {
        return userApi.findByDni(dni).getBody();
    }


    @Override
    @CircuitBreaker(name = "products-service",fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepo.findAll();
        List<SaleDTO> salesResponse = new ArrayList<SaleDTO>();
        for(Sale sale : sales){
            SaleDTO saleDTO = this.getSale(sale.getId());
            salesResponse.add(saleDTO);
        }
        return salesResponse;
    }

    public void fallback(Throwable throwable){
        throwable.printStackTrace();
    }
}
