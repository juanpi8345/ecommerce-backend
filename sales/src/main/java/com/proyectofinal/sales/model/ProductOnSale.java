package com.proyectofinal.sales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOnSaleId;
    private Long productCode;
    private String productName;
    private Long price;
    @ManyToOne
    @JoinColumn(name = "saleId")
    private Sale sale;
    private boolean hasDescount;
    private int percentageDiscount;
}
