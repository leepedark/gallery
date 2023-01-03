package com.example.backend.controller;

import com.example.backend.entity.Cart;
import com.example.backend.entity.Item;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.ItemRepository;
import com.example.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token") String token) {

        if (!jwtService.isValid(token)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart :: getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);


        return new ResponseEntity(items , HttpStatus.OK);
    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(@PathVariable("itemId") int itemId,
                                       @CookieValue(value = "token") String token) {

        if (!jwtService.isValid(token)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);

        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setItemId(itemId);
            newCart.setMemberId(memberId);

            cartRepository.save(newCart);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(@CookieValue(value = "token") String token
            , @PathVariable("itemId") int itemId) {

        if (!jwtService.isValid(token)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Cart cart = cartRepository.findByMemberIdAndItemId(jwtService.getId(token), itemId);
        cartRepository.delete(cart);

        return new ResponseEntity(HttpStatus.OK);
    }

}