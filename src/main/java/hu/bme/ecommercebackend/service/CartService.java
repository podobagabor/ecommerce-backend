package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.ActionForbiddenException;
import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.customExceptions.IllegalActionException;
import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.CartRepository;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                       UserService userService,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public CartElement getCartElementById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown cart entity"));
    }

    @Transactional
    public Boolean deleteCartElement(Long id) {
        cartRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteCartElementFromUser(Long id, String userId) {
        User user = cartRepository.findUserById(id);
        if (!Objects.equals(user.getId(), userId)) {
            throw new ActionForbiddenException("You don't have permission for this activity");
        }
        cartRepository.deleteById(id);
    }

    @Transactional
    public CartElementDto modifyQuantity(Long id, Integer quantity, String userId) {
        CartElement cartElementEntity = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown cart entity"));
        if (!Objects.equals(cartElementEntity.getUser().getId(), userId)) {
            throw new ActionForbiddenException("You don't have permission for this activity");
        }
        if (quantity < 1) {
            throw new IllegalActionException("Quantity have to be at least 1.");
        }
        if (cartElementEntity.getProduct().getCount() < quantity) {
            throw new IllegalActionException("There isn't enough product in stock.");
        }
        cartElementEntity.setQuantity(quantity);
        return new CartElementDto(cartElementEntity);
    }

    @Transactional
    public CartElementDto addToCart(String userId, CartElementCreateDto cartElement) {
        User userEntity = userService.getUserById(userId);
        Product productEntity = productService.getProductById(cartElement.getProductId());
        if (userEntity.getCart().stream().map(CartElement::getProduct).findFirst().equals(productEntity)) {
            throw new IllegalActionException("This product is already in the cart.");
        }
        if (productEntity.getCount() < 1) {
            throw new IllegalActionException("Shortage of stock.");
        }
        CartElement cartElementEntity = cartRepository.save(new CartElement(productEntity, cartElement.getQuantity(), userEntity));
        return new CartElementDto(cartElementEntity);
    }

    public List<CartElementDto> getCartOfUser(String userId) {
        User userEntity = userService.getUserById(userId);
        return userEntity.getCart().stream().map(CartElementDto::new).collect(Collectors.toList());
    }
}
