package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.dto.Product.ProductModifyDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.repository.ProductRepository;
import hu.bme.ecommercebackend.specification.EcommerceSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final EmailService emailService;

    @Value("${admin.email}")
    private String adminEmail;

    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService,
            BrandService brandService,
            ImageService imageService,
            EmailService emailService
    ) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.emailService = emailService;
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
        return productRepository.getReferenceById(id);
    }

    @Transactional
    public Boolean deleteProductById(Long id) {
        Product productEntity = getProductById(id);
        productEntity.setActive(false);
        return true;
    }

    @Transactional
    public ProductDto createProduct(ProductCreateDto productCreateDto, List<MultipartFile> images) {
        List<String> imageUrlList = new ArrayList<String>();
        for (MultipartFile file : images) {
            imageUrlList.add(imageService.saveImage(file));
        }
        Category categoryEntity = categoryService.getCategoryById(productCreateDto.getCategoryId());
        Brand brandEntity = brandService.getBrandById(productCreateDto.getBrandId());
        return new ProductDto(productRepository.save(new Product(
                null,
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

    @Transactional
    public ProductDto reduceCount(Long id, Integer value) {
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown product entity"));
        productEntity.setCount(productEntity.getCount() - value);
        if (productEntity.getCount() < 3) {
            this.emailService.sendEmail(adminEmail, "Shortage of stock", this.emailService.getShortageOfStochForAdmin(productEntity));
        }
        return new ProductDto(productEntity);
    }

    @Transactional
    public ProductDto increaseCount(Long id, Integer value) {
        Product productEntity = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown product entity"));
        productEntity.setCount(productEntity.getCount() + value);
        return new ProductDto(productEntity);
    }

    @Transactional
    public ProductDto modifyProduct(ProductModifyDto product, List<MultipartFile> newImages) {
        Product productEntity = productRepository.findById(product.getId()).orElseThrow(() -> new EntityNotFoundException("Unknown product entity"));
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
                        imageUrl -> !imageUrl.equals(imageModifyDto.getUrl())
                ).collect(Collectors.toCollection(ArrayList::new)));
                this.imageService.deleteImage(imageModifyDto.getUrl());
            }
        });
        if (newImages != null) {
            newImages.forEach(multipartFile -> {
                productEntity.getImages().add(imageService.saveImage(multipartFile));
            });
        }
        productEntity.setCount(product.getCount());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setDiscountPercentage(product.getDiscountPercentage());
        return new ProductDto(productEntity);
    }

    public Page<ProductDto> findAll(String name, List<Long> categoryId, Boolean discount, Integer minPrice, Integer maxPrice, List<Long> brandId, Pageable pageable) {
        Specification<Product> spec = EcommerceSpecification.filterBy(name, categoryId, discount, minPrice, maxPrice, brandId);
        return productRepository.findAll(spec, pageable).map(ProductDto::new);
    }

}
