package com.proyectofinal.cart.controller;

import com.proyectofinal.cart.dto.CartDTO;
import com.proyectofinal.cart.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartServ;

    @GetMapping("/get/{cartId}")
    public CartDTO getCart(@PathVariable Long cartId){
        return cartServ.getCart(cartId);
    }
    @PostMapping("/create")
    public void createCart(@RequestParam List<Long> productsCodes){
        cartServ.createCart(productsCodes);
    }

    @PostMapping("/{cartId}/addProduct/{productId}")
    public void addProduct(@PathVariable Long productId, @PathVariable Long cartId){
        cartServ.addProduct(cartId,productId);
    }

    @DeleteMapping("/{cartId}/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Long productId, @PathVariable Long cartId){
        cartServ.deleteProduct(cartId,productId);
    }

    @DeleteMapping("/delete/{cartId}")
    public void deleteCart(@PathVariable Long cartId){
        cartServ.deleteCart(cartId);
    }


}
