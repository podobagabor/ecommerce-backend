package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SavedItemsService {
    private final ProductService productService;
    private final UserService userService;

    public SavedItemsService(UserRepository userRepository, ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public List<ProductDto> getSavedItemsOfUser(String userId) {
        return userService.getSavedItemsOfUser(userId);
    }

    @Transactional
    public ProductDto addProductToSaved(Long productId, String userId) {
        User userEntity = userService.getUserById(userId);
        Product productEntity = productService.getProductById(productId);
        if (productEntity == null) {
            throw new EntityNotFoundException("Unknown product entity");
        }
        userEntity.getSavedProducts().add(productEntity);
        return new ProductDto(productEntity);
    }

    @Transactional
    public Integer removeProductFromSaved(Long productId, String userId) {
        User userEntity = userService.getUserById(userId);
        userEntity.getSavedProducts().removeIf(product -> Objects.equals(product.getId(), productId));
        return userEntity.getSavedProducts().size();
    }
}
