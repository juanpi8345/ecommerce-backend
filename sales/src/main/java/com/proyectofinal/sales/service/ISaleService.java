package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.dto.UserDTO;
import com.proyectofinal.sales.model.Sale;

import java.util.List;

public interface ISaleService {
    void saveSale(Sale sale);
    void deleteSale(Long saleId);
    SaleDTO getSale(Long saleId);

    UserDTO getUser(String dni);
    List<SaleDTO> getAllSales();
}
