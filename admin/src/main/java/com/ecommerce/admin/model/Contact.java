package com.ecommerce.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String reason;
    private String message;
}
