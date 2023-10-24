package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.Contact;
import com.ecommerce.admin.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IContactService contactServ;

    @PostMapping("/contact")
    public void sendEmail(@RequestBody Contact contact){
        contactServ.sendEmail(contact);
    }
}
