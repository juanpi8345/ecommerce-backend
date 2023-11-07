package com.ecommerce.admin.service;

import com.ecommerce.admin.model.Contact;
import com.ecommerce.admin.repository.IUserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.admin.model.User;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class ContactService implements IContactService {

    @Autowired
    private IUserRepository userRepository;
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

    @Override
    public boolean sendCode(String email) {
        MimeMessage message = mail.createMimeMessage();
        User user = userRepository.findUserByEmail(email);
        if(user != null && !user.isVerificated()){
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                //in setTo is going to be the admin email.
                helper.setTo(email);
                helper.setSubject("Codigo de verificacion");
                String htmlContent = "<html><body>";
                htmlContent += "<h1 style='color: #13c2c2;'>Verificar su cuenta de clothing store</h1>";
                htmlContent += "<h2 style='font-size: 18px;'>Codigo de verificacion: "+ "<span style='color:#13c2c2'>"+user.getVerticationCode()+"</span>"+"</h2>";
                htmlContent += "<h4 style='font-size: 12px;'>No comparta este codigo con nadie.</h4>";
                htmlContent += "</body></html>";
                helper.setText(htmlContent, true); //
                mail.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return user != null;

    }

    @Override
    public boolean verificateCode(String email, String code) {
        User user = userRepository.findUserByEmail(email);
        if(user!= null && user.getVerticationCode().equals(code)){
            user.setVerificated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
