package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SavedItemsService {
    private final UserRepository userRepository;
    private final ProductService productService;

    public SavedItemsService(UserRepository userRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    public List<ProductDto> getSavedItemsOfUser(String userId) {
        return userRepository.findSavedProductsByUser_Id(userId).stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addProductToSaved(Long productId, String userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        Product productEntity = productService.getProductById(productId);
        if (productEntity == null) {
            throw new EntityNotFoundException("Unknown entity");
        }
        userEntity.getSavedProducts().add(productEntity);
        userRepository.save(userEntity);
        return new ProductDto(productEntity);
    }

    @Transactional
    public Integer removeProductFromSaved(Long productId, String userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        userEntity.getSavedProducts().removeIf(product -> Objects.equals(product.getId(), productId));
        return userEntity.getSavedProducts().size();
    }
}
