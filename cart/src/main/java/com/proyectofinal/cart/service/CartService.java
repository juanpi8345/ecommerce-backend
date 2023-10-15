package com.proyectofinal.cart.service;

import com.proyectofinal.cart.dto.CartDTO;
import com.proyectofinal.cart.dto.ProductDTO;
import com.proyectofinal.cart.model.Cart;
import com.proyectofinal.cart.repository.ICartRepository;
import com.proyectofinal.cart.repository.IProductAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements  ICartService {

    @Autowired
    private ICartRepository cartRepo;

    @Autowired
    private IProductAPI productAPI;

    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public void addProduct(Long cartId, Long productCode) {
        ProductDTO pr = productAPI.getProductById(productCode);
        Cart cart = cartRepo.findById(cartId).orElse(null);
        if(pr != null && cart!= null){
            cart.getProductsCodes().add(pr.getCode());
            cart.setTotal(this.calculateTotal(cart));
            cartRepo.save(cart);
        }
    }
    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public void deleteProduct(Long cartId, Long productCode) {
        ProductDTO pr = productAPI.getProductById(productCode);
        Cart cart = cartRepo.findById(cartId).orElse(null);
        if(pr != null & cart!= null){
            cart.getProductsCodes().remove(productCode);
            cart.setTotal(this.calculateTotal(cart));
            cartRepo.save(cart);
        }
    }

    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public void createCart(List<Long> productsCode) {
        Cart c = new Cart();
        List<Long> listCodes = new ArrayList<Long>();
        for(Long productCode:productsCode){
            ProductDTO pr = productAPI.getProductById(productCode);
            if(pr != null)
                listCodes.add(pr.getCode());
        }
        c.setProductsCodes(listCodes);
        c.setTotal(this.calculateTotal(c));
        cartRepo.save(c);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepo.deleteById(cartId);
    }

    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "fallBack")
    @Retry(name="products-service")
    public CartDTO getCart(Long cartId) {
        CartDTO cartResponse = new CartDTO();
        Cart cart = cartRepo.findById(cartId).orElse(null);
        List<ProductDTO> productsInCart = new ArrayList<ProductDTO>();
        if(cart != null){
            cartResponse.setTotal(cart.getTotal());
            cartResponse.setId(cart.getId());
            for(Long productCode: cart.getProductsCodes()){
                ProductDTO pr = productAPI.getProductById(productCode);
                productsInCart.add(pr);
            }
            cartResponse.setProductsList(productsInCart);
        }
        return cartResponse;
    }

    public long calculateTotal(Cart cart){
        long total = 0;
        for(Long productCode: cart.getProductsCodes()){
            ProductDTO pr = productAPI.getProductById(productCode);
            total += pr.getPrice();
        }
        return total;
    }

    public void fallBack(Throwable throwable){
        throwable.printStackTrace();
    }
}
