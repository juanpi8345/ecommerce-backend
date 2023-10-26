package com.proyectofinal.sales.controller;

import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.model.Sale;
import com.proyectofinal.sales.service.ISaleService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private ISaleService saleServ;

    @GetMapping("/get")
    public List<SaleDTO> getAllSales(){
        return saleServ.getAllSales();
    }

    @GetMapping("/get/{saleId}")
    public SaleDTO getSale(@PathVariable Long saleId){
        return saleServ.getSale(saleId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSale(@RequestBody Sale sale) {
        saleServ.saveSale(sale);
        return ResponseEntity.ok().build();
    }


}
