package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.dto.Product.ProductModifyDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        this.imageService = imageService;
    }

    public List<ProductDto> getProductList() {
        return this.productRepository.findAll().stream().map(
                ProductDto::new
        ).collect(Collectors.toList());
    }

    public ProductDto getProductDtoById(Long id) {
        return new ProductDto(getProductById(id));
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    public Product getProductReferenceById(Long id) {
        return productRepository.getReferenceById(id) ;
    }

    public Boolean deleteProductById(Long id) {
        productRepository.deleteById(id);
        return true;
    }

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        List<String> imageUrlList = new ArrayList<String>();
        for (MultipartFile file : productCreateDto.getImages()) {
            imageUrlList.add(imageService.saveImage(file));
        }
        Category categoryEntity = categoryService.getCategoryById(productCreateDto.getCategoryId());
        Brand brandEntity = brandService.getBrandById(productCreateDto.getBrandId());
        return new ProductDto(productRepository.save(new Product(
                productCreateDto.getName(),
                productCreateDto.getCount(),
                productCreateDto.getDescription(),
                productCreateDto.getDiscountPercentage(),
                imageUrlList,
                productCreateDto.getPrice(),
                categoryEntity,
                brandEntity
        )));
    }

    public ProductDto reduceCount(Long id, Integer value) {
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        productEntity.setCount(productEntity.getCount() - value);
        return new ProductDto(productRepository.save(productEntity));
    }

    public ProductDto increaseCount(Long id, Integer value) {
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        productEntity.setCount(productEntity.getCount() + value);
        return new ProductDto(productRepository.save(productEntity));
    }

    public ProductDto modifyProduct(ProductModifyDto product) {
        Product productEntity = productRepository.findById(product.getId()).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        if (!Objects.equals(productEntity.getCategory().getId(), product.getCategoryId())) {
            Category categoryEntity = categoryService.getCategoryById(product.getCategoryId());
            productEntity.setCategory(categoryEntity);
        }
        if (!Objects.equals(productEntity.getBrand().getId(), product.getBrandId())) {
            Brand brandEntity = brandService.getBrandById(product.getBrandId());
            productEntity.setBrand(brandEntity);
        }
        product.getImages().forEach(imageModifyDto -> {
            if (imageModifyDto.getDeleted()) {
                productEntity.setImages(productEntity.getImages().stream().filter(
                        imageUrl -> imageUrl.equals(imageModifyDto.getUrl())
                ).toList());
            }
        });
        product.getNewImages().forEach(multipartFile -> {
            productEntity.getImages().add(imageService.saveImage(multipartFile));
        });
        productEntity.setCount(product.getCount());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setDiscountPercentage(product.getDiscountPercentage());
        return new ProductDto(productRepository.save(productEntity));
    }
}
