package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.service.SavedItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/saved")
public class SavedController {

    private final SavedItemsService savedItemsService;

    public SavedController(SavedItemsService savedItemsService) {
        this.savedItemsService = savedItemsService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<ProductDto>> getSavedOfUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(savedItemsService.getSavedItemsOfUser(jwt.getSubject()));
    }

    @PutMapping(value = "/add/{id}")
    public ResponseEntity<Integer> addProductToSaved(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItemsService.addProductToSaved(id,jwt.getSubject()));
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<Integer> removeProductFromSaved(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(savedItemsService.removeProductFromSaved(id,jwt.getSubject()));
    }
}
