package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findAllByUserDni(String userDni);

}

