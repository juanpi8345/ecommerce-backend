package com.proyectofinal.sales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Residence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long residenceId;
    private String province;
    private String locality;
    private String street;
    private long residenceNumber;
    @OneToMany(mappedBy = "residence")
    @JsonIgnore
    private List<Sale> sales;
}
