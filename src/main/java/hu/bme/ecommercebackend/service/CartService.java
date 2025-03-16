package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.exception.AccessDeniedException;
import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartService(CartRepository cartRepository,
                       ProductService productService,
                       UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public CartElement getCartElementById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    public Boolean deleteCartElement(Long id) {
        cartRepository.deleteById(id);
        return true;
    }

    public String deleteCartElementFromUser(Long id,String userId) {
        String ownerId = cartRepository.getUserIdById(id);
        if(Objects.equals(userId, ownerId)) {
            throw new AccessDeniedException("You don't have permission for this activity");
        }
        cartRepository.deleteById(id);
        return "Successful deletion";
    }

    public CartElementDto createCard(CartElementCreateDto cardElement, String userId) {
        User userEntity = userService.getUserById(userId);
        Product productEntity = productService.getProductById(cardElement.getProductId());
        CartElement cartEntity = cartRepository.save(new CartElement(productEntity, cardElement.getQuantity(), userEntity));
        return new CartElementDto(cartEntity);
    }

    public CartElementDto modifyQuantity(Long id, Integer quantity, String userId) {
        CartElement cartElementEntity = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        if(Objects.equals(cartElementEntity.getUser().getId(), userId)) {
            throw new AccessDeniedException("You don't have permission for this activity");
        }
        cartElementEntity.setQuantity(quantity);
        return new CartElementDto(cartRepository.save(cartElementEntity));
    }

    public CartElementDto addToCart(String userId, CartElementCreateDto cartElement) {
        User userEntity = userService.getUserById(userId);
        Product productEntity = productService.getProductById(cartElement.getProductId());
        CartElement cartElementEntity = cartRepository.save(new CartElement(productEntity, cartElement.getQuantity(), userEntity));
        return new CartElementDto(cartElementEntity);
    }

    public List<CartElementDto> getCartOfUser(String userId) {
        return cartRepository.findCartElementsByUserId(userId).stream().map(CartElementDto::new).collect(Collectors.toList());
    }
}
