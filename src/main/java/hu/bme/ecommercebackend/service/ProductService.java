package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ImageService imageService;

    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService,
            BrandService brandService,
            ImageService imageService
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

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        List<String> imageUrlList = new ArrayList<String>();
        for(MultipartFile file: productCreateDto.getImages()) {
            imageUrlList.add(imageService.saveImage(file));
        }
        Category categoryEntity = categoryService.getCategoryById(productCreateDto.getCategoryId());
        Brand brandEntity = brandService.getBrandById(productCreateDto.getBrandId());
        productRepository.save(new Product(

        ))
    }
}
