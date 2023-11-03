package com.ecommerce.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDTO {
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    private String role;
    private String password;
}
