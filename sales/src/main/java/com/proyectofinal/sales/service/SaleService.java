package com.proyectofinal.sales.service;

import com.proyectofinal.sales.dto.ProductDTO;
import com.proyectofinal.sales.dto.SaleDTO;
import com.proyectofinal.sales.dto.UserDTO;
import com.proyectofinal.sales.model.ProductOnSale;
import com.proyectofinal.sales.model.Residence;
import com.proyectofinal.sales.model.Sale;
import com.proyectofinal.sales.repository.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private IResidenceRepository residenceRepo;

    @Autowired
    private IProductAPI productApi;

    @Autowired
    private IProductSaleRepository productOnSaleRepo;

    @Autowired
    private IUserAPI userApi;

    @Override
    public Sale getNormalSale(Long saleId){
        return saleRepo.findById(saleId).orElse(null);
    }

    @Override
    public long calculatePriceWithDescount(ProductDTO pr) {
        long price = pr.getPrice();
        if(pr.isHasDiscount()){
            price = (price) - (price*pr.getPercentageDiscount())/100;
        }
        return price;
    }

    @Override
    @CircuitBreaker(name = "product-service",fallbackMethod = "fallBack")
    @Retry(name="product-service")
    public void saveSale(Sale sale) {
        UserDTO user = this.getUser(sale.getUserDni());
        int userSalesWithoutComplete = saleRepo.findByUserDniAndCompletedFalse(user.getDni()).size();

        if (user != null && userSalesWithoutComplete< 4) {
            sale.setDate(LocalDate.now());
            sale.setPaid(false);
            sale.setReady(false);
            sale.setCompleted(false);

            if (sale.getType().equalsIgnoreCase("Envio a domicilio")) {
                Residence existingResidence = residenceRepo.findByProvinceAndLocalityAndStreetAndResidenceNumber(
                        sale.getResidence().getProvince(), sale.getResidence().getLocality(),
                        sale.getResidence().getStreet(), sale.getResidence().getResidenceNumber());
                if (existingResidence != null) {
                    sale.setResidence(existingResidence);
                } else {
                    residenceRepo.save(sale.getResidence());
                }
            } else {
                sale.setResidence(null);
            }

            saleRepo.save(sale);
            for (Long prId : sale.getProductsId()) {
                ProductDTO pr = productApi.getProductById(prId);
                if (pr != null) {
                    productApi.reduceStock(pr.getCode());
                    sale.setTotal(sale.getTotal() + this.calculatePriceWithDescount(pr));
                    ProductOnSale productOnSale = new ProductOnSale(null,pr.getCode(), pr.getName(), this.calculatePriceWithDescount(pr),sale,pr.isHasDiscount(),pr.getPercentageDiscount());
                    productOnSaleRepo.save(productOnSale);
                }
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
        Sale sale  = saleRepo.findById(saleId).orElse(null);
        SaleDTO saleResponse = new SaleDTO();
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(ProductOnSale pr : sale.getProducts()){
            ProductDTO prDTO = new ProductDTO(pr.getProductCode(),pr.getProductName(),pr.getPrice(),pr.isHasDescount(),pr.getPercentageDiscount());
            products.add(prDTO);
        }
        if(sale != null){
            UserDTO user = this.getUser(sale.getUserDni());
            saleResponse.setId(sale.getSaleId());
            saleResponse.setDate(sale.getDate());
            saleResponse.setProducts(products);
            saleResponse.setTotal(sale.getTotal());
            saleResponse.setPaid(sale.isPaid());
            saleResponse.setReady(sale.isReady());
            saleResponse.setCompleted(sale.isCompleted());
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
    public List<Sale> searchSales(String query){
        return saleRepo.findByUserDniContaining(query);
    }

    @Override
    @CircuitBreaker(name = "products-service",fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepo.findAll();
        List<SaleDTO> salesResponse = new ArrayList<SaleDTO>();
        for(Sale sale : sales){
            SaleDTO saleDTO = this.getSale(sale.getSaleId());
            salesResponse.add(saleDTO);
        }
        return salesResponse;
    }

    @Override
    public List<SaleDTO> getAllSalesByUserDni(String userDni) {
        List<Sale> sales = saleRepo.findAllByUserDni(userDni);
        List<SaleDTO> salesResponse = new ArrayList<SaleDTO>();
        for(Sale sale : sales){
            SaleDTO saleDTO = this.getSale(sale.getSaleId());
            salesResponse.add(saleDTO);
        }
        return salesResponse;
    }

    @Override
    public void updateSale(Sale sale) {
        saleRepo.save(sale);
    }

    @Override
    public Page<Sale> getAllPaginated(int page, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(sortField).descending());
        return saleRepo.findAll(pageRequest);
    }

    @Override
    public Page<Sale> getAllNotCompletedPaginated(PageRequest pageRequest) {
        return saleRepo.findByCompletedFalse(pageRequest);
    }

    @Override
    public List<Sale> findByUserDniAndCompletedFalse(String userDni) {
        return saleRepo.findByUserDniAndCompletedFalse(userDni);
    }

    public void fallback(Throwable throwable){
        throwable.printStackTrace();
    }
}
