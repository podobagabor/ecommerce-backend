package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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
        return userRepository.findSavedProductsById(userId).stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public Integer addProductToSaved(Long productId, String userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        Product productReference = productService.getProductReferenceById(productId);
        if (productReference == null) {
            throw new EntityNotFoundException("Unknown entity");
        }
        userEntity.getSavedProducts().add(productReference);
        userEntity = userRepository.save(userEntity);
        return userEntity.getSavedProducts().size();
    }

    public Integer removeProductFromSaved(Long productId, String userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        userEntity.getSavedProducts().removeIf(product -> Objects.equals(product.getId(), productId));
        userEntity = userRepository.save(userEntity);
        return userEntity.getSavedProducts().size();
    }
}
