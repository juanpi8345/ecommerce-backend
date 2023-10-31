package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResidenceRepository extends JpaRepository<Residence,Long> {
    Residence findByProvinceAndLocalityAndStreetAndResidenceNumber(String province,String locality,String street, long residenceNumber);
}
