package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {
    List<Sale> findAllByUserDni(String userDni);
    List<Sale> findByUserDniContaining(String query);
    Page<Sale> findByCompletedFalse(PageRequest pageRequest);

    List<Sale> findByUserDniAndCompletedFalse(String UserDni);

}

