package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Common.ActionResponseDto;
import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.exception.AccessDeniedException;
import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.CartRepository;
import hu.bme.ecommercebackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductService productService,
                       UserService userService,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public CartElement getCartElementById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    public Boolean deleteCartElement(Long id) {
        cartRepository.deleteById(id);
        return true;
    }

    public ActionResponseDto deleteCartElementFromUser(Long id, String userId) {
        User user = cartRepository.findUserById(id);
        ActionResponseDto result = new ActionResponseDto();
        if (!Objects.equals(user.getId(), userId)) {
            result.setMessage("You don't have permission for this activity");
            result.setSuccess(false);
            throw new AccessDeniedException("You don't have permission for this activity");
        }
        cartRepository.deleteById(id);
        result.setSuccess(true);
        return result;
    }

    public CartElementDto modifyQuantity(Long id, Integer quantity, String userId) {
        CartElement cartElementEntity = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        if (!Objects.equals(cartElementEntity.getUser().getId(),userId)) {
            throw new AccessDeniedException("You don't have permission for this activity");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity have to be at least 1.");
        }
        cartElementEntity.setQuantity(quantity);
        return new CartElementDto(cartRepository.save(cartElementEntity));
    }

    public CartElementDto addToCart(String userId, CartElementCreateDto cartElement) {
        User userEntity = userService.getUserById(userId);
        Product productEntity = productService.getProductById(cartElement.getProductId());
        if(userEntity.getCart().stream().map(CartElement::getProduct).findFirst().equals(productEntity)) {
            throw new IllegalArgumentException("This product is already in the cart.");
        }
        CartElement cartElementEntity = cartRepository.save(new CartElement(productEntity, cartElement.getQuantity(), userEntity));
        return new CartElementDto(cartElementEntity);
    }

    public List<CartElementDto> getCartOfUser(String userId) {
        User userEntity = userService.getUserById(userId);
        return userEntity.getCart().stream().map(CartElementDto::new).collect(Collectors.toList());
    }
}
