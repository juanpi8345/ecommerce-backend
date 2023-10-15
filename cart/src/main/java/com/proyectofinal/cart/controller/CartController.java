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
    public String createCart(@RequestParam List<Long> productsCodes){
        cartServ.createCart(productsCodes);
        return "Carrito creado correctamente";
    }
    @PostMapping("/{cartId}/addProduct/{productId}")
    public String addProduct(@PathVariable Long productId, @PathVariable Long cartId){
        cartServ.addProduct(cartId,productId);
        return "Producto añadido correctamente";
    }

    @DeleteMapping("/{cartId}/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId, @PathVariable Long cartId){
        cartServ.deleteProduct(cartId,productId);
        return "Producto eliminado correctamente";
    }

    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable Long cartId){
        cartServ.deleteCart(cartId);
        return "Carrito eliminado correctamente";
    }


}
