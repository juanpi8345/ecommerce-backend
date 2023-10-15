package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.CartDTO;
import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.model.Sale;
import com.proyectofinal.sales.repository.ICartAPI;
import com.proyectofinal.sales.repository.ISaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private ICartAPI cartApi;

    @Override
    @CircuitBreaker(name = "cart-service",fallbackMethod = "fallBack")
    @Retry(name="cart-service")
    public void saveSale(Long cartId) {
        Sale sale = new Sale();
        CartDTO cart = cartApi.getCart(cartId);
        if(cart != null){
            sale.setCartId(cart.getId());
            sale.setDate(LocalDate.now());
        }
        saleRepo.save(sale);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepo.deleteById(saleId);
    }

    @Override
    @CircuitBreaker(name = "cart-service",fallbackMethod = "fallBack")
    @Retry(name="cart-service")
    public SaleDTO getSale(Long saleId) {
        Sale sale  = saleRepo.findById(saleId).orElse(null);
        SaleDTO saleResponse = new SaleDTO();
        CartDTO cart = cartApi.getCart(sale.getCartId());
        if(sale != null && cart!= null){
            saleResponse.setId(sale.getId());
            saleResponse.setDate(sale.getDate());
            saleResponse.setCart(cart);
        }
        return saleResponse;
    }

    @Override
    @CircuitBreaker(name = "cart-service",fallbackMethod = "fallBack")
    @Retry(name="cart-service")
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepo.findAll();
        List<SaleDTO> salesResponse = new ArrayList<SaleDTO>();
        for(Sale sale : sales){
            CartDTO cart = cartApi.getCart(sale.getCartId());
            salesResponse.add(new SaleDTO(sale.getId(),sale.getDate(),cart));
        }
        return salesResponse;
    }

    public void fallback(Throwable throwable){
        throwable.printStackTrace();
    }
}
