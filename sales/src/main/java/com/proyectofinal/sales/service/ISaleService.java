package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.ProductDTO;
import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.dto.UserDTO;
import com.proyectofinal.sales.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface ISaleService {
    long calculatePriceWithDescount(ProductDTO pr);
    void saveSale(Sale sale);
    void deleteSale(Long saleId);
    SaleDTO getSale(Long saleId);
    Sale getNormalSale(Long saleId);
    UserDTO getUser(String dni);
    List<SaleDTO> getAllSales();
    List<SaleDTO> getAllSalesByUserDni(String userDni);
    List<Sale> searchSales(String query);
    void updateSale(Sale sale);
    Page<Sale> getAllPaginated(int page, int pageSize, String sortField);
    Page<Sale> getAllNotCompletedPaginated(PageRequest pageRequest);

    List<Sale> findByUserDniAndCompletedFalse(String userDni);
}
