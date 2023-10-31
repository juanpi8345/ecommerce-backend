package com.proyectofinal.sales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}
