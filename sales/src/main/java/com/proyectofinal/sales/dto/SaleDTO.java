package com.proyectofinal.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.proyectofinal.sales.model.Residence;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private Long id;
    private LocalDate date;
    private List<ProductDTO> products;
    private boolean paid;
    private boolean ready;
    private Long total;
    private UserDTO user;
    private String type;
    private Residence residence;
}
