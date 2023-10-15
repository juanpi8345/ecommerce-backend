package com.proyectofinal.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long total;
    private List<ProductDTO> productsList;
}
