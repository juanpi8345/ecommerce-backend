package com.proyectofinal.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    private boolean paid;
    private boolean ready;
    private long total;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> productsId ;
    private String userDni;
    private String type;
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "residenceId",nullable = true)
    private Residence residence;

    /*
        this is a products copy because of its can be updated or deleted and we dont want products on a sale
        can be updated, or deleted, we prefer hold prices, and products.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
    @JsonIgnore
    private List<ProductOnSale> products = new ArrayList<ProductOnSale>();
}
