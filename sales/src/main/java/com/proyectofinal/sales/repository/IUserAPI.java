package com.proyectofinal.sales.repository;

import com.proyectofinal.sales.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface IUserAPI {
    @GetMapping("user/find/dni/{dni}")
    ResponseEntity<UserDTO> findByDni(@PathVariable String dni);


}
