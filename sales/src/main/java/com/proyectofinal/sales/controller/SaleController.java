package com.proyectofinal.sales.controller;

import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.model.Sale;
import com.proyectofinal.sales.service.ISaleService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private ISaleService saleServ;

    @GetMapping("/get")
    public List<SaleDTO> getAllSales(){
        return saleServ.getAllSales();
    }

    @GetMapping("getQuantityWithoutComplete/user/{userDni}")
    public Map<String,Integer> getQuantityWithoutComplete(@PathVariable String userDni){
        int quantity =  saleServ.findByUserDniAndCompletedFalse(userDni).size();
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("Quantity",quantity);
        return map;
    }

    @GetMapping("get/paginated")
    public Page<Sale> getAllSalesPaginated(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "date") String sortField){
        PageRequest pageRequest = PageRequest.of(page,15, Sort.by(sortField).descending());
        return saleServ.getAllNotCompletedPaginated(pageRequest);
    }

    @GetMapping("/get/user/{userDni}")
    public List<SaleDTO> getAllUserSales(@PathVariable String userDni){
        return saleServ.getAllSalesByUserDni(userDni);
    }

    @GetMapping("/get/{saleId}")
    public SaleDTO getSale(@PathVariable Long saleId){
        return saleServ.getSale(saleId);
    }

    @GetMapping("/search")
    public List<Sale> searchProducts(@RequestParam("query") String query){
        return saleServ.searchSales(query);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSale(@RequestBody Sale sale) {
        saleServ.saveSale(sale);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/markAsPaid/sale/{saleId}")
    public void markAsPaid(@PathVariable Long saleId){
        Sale sale = saleServ.getNormalSale(saleId);
        if(sale != null){
            sale.setPaid(true);
        }
        saleServ.updateSale(sale);
    }

    @PutMapping("/markAsReady/sale/{saleId}")
    public void markAsReady(@PathVariable Long saleId){
        Sale sale = saleServ.getNormalSale(saleId);
        if(sale != null){
            sale.setReady(true);
        }
        saleServ.updateSale(sale);
    }

    @PutMapping("/markAsCompleted/sale/{saleId}")
    public void markAsCompleted(@PathVariable Long saleId){
        Sale sale = saleServ.getNormalSale(saleId);
        if(sale != null){
            sale.setCompleted(true);
        }
        saleServ.updateSale(sale);
    }

    @DeleteMapping("/delete/sale/{saleId}")
    public void deleteSale(@PathVariable Long saleId){
        saleServ.deleteSale(saleId);
    }


}
