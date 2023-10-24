package com.ecommerce.admin.service;

import com.ecommerce.admin.model.Contact;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class ContactService implements IContactService {

    @Autowired
    private JavaMailSender mail;
    @Override
    public void sendEmail(Contact contact) {
        MimeMessage message = mail.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //in setTo is going to be the admin email.
            helper.setTo("juanpipre@outlook.com");
            helper.setSubject(contact.getReason());
            String htmlContent = "<html><body>";
            htmlContent += "<h1 style='color: #13c2c2;'>Nuevo mensaje en su tienda online</h1>";
            htmlContent += "<h2 style='font-size: 18px;'>Nuevo mensaje del cliente "+ "<span style='color:#13c2c2'>"+contact.getName() + " " +contact.getLastname()+"</span>"+"</h2>";
            htmlContent += "<p style='font-size: 16px; line-height:1.5;'>"+"Mensaje: "+contact.getMessage()+"</p>";
            htmlContent += "<p style='font-size: 14px; line-height:1.5;'> Datos del cliente: <br>"+"Email: "+contact.getEmail() + "<br>"+"Telefono: " +contact.getPhone()+"</p>";
            htmlContent += "<h4 style='font-size: 12px;'>Este es un mensaje que recibio de la tienda online, por favor pongase en contacto con el cliente</h4>";
            htmlContent += "</body></html>";
            helper.setText(htmlContent, true); //
            mail.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
