package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.SaleDTO;

import java.util.List;

public interface ISaleService {
    void saveSale(Long cartId);
    void deleteSale(Long saleId);
    SaleDTO getSale(Long saleId);
    List<SaleDTO> getAllSales();
}
