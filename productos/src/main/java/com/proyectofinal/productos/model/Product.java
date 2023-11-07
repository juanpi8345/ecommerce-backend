package com.proyectofinal.productos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String name;
    private String size;
    @Lob
    private String description;
    private Long price;
    private Long stock;
    private String imgUrl;
    private boolean hasDiscount;
    private int percentageDiscount;
}
