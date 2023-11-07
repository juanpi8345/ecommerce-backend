package com.ecommerce.admin.service;

import com.ecommerce.admin.model.Contact;

public interface IContactService {
    void sendEmail(Contact contact);
    boolean sendCode(String email);

    boolean verificateCode(String email, String code);
}
