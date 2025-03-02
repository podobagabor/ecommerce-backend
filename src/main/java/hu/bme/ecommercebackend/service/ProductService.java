package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService,
            BrandService brandService
    ) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    public List<ProductDto> getProductList() {
        return this.productRepository.findAll().stream().map(
                ProductDto::new
        ).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return new ProductDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity")));
    }

    public String deleteProductId(Long id) {
        productRepository.deleteById(id);
        return "Successful deleting";
    }
}
