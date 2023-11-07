package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.Contact;
import com.ecommerce.admin.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@RequestParam String email){
        if(contactServ.sendCode(email))
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/verificateCode")
    public ResponseEntity<?> verificateCode(@RequestParam String email, @RequestParam String code){
        if(contactServ.verificateCode(email, code)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
