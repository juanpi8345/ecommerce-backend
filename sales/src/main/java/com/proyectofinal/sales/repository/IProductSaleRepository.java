package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.model.ProductOnSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductSaleRepository extends JpaRepository<ProductOnSale,Long> {
}
