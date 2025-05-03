package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Common.ActionResponseDto;
import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/addToCart")
    public ResponseEntity<CartElementDto> createCartElement(@RequestBody CartElementCreateDto cartElement, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(jwt.getSubject(), cartElement));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CartElementDto>> getCartOfUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(cartService.getCartOfUser(jwt.getSubject()));
    }

    @PutMapping(value = "/{id}/increase/{quantity}")
    public ResponseEntity<CartElementDto> changeQuantity(@PathVariable Long id, @PathVariable Integer quantity, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(cartService.modifyQuantity(id, quantity, jwt.getSubject()));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ActionResponseDto> deleteCartElement(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.deleteCartElementFromUser(id, jwt.getSubject()));
    }
}
