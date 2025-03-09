package hu.bme.ecommercebackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok(productService.getProductList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(
            @RequestPart("product") String productCreateDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCreateDto productCreateDtoObject = objectMapper.readValue(productCreateDto, ProductCreateDto.class);
        productCreateDtoObject.setImages(images);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productCreateDtoObject));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProductId(id));
    }

    @PutMapping("reduce/{id}/{count}")
    public ResponseEntity<ProductDto> reduceProductCount(@PathVariable Long id, @PathVariable Integer count) {
        return ResponseEntity.ok(productService.reduceCount(id, count));
    }

    @PutMapping("increase/{id}/{count}")
    public ResponseEntity<ProductDto> increaseProductCount(@PathVariable Long id, @PathVariable Integer count) {
        return ResponseEntity.ok(productService.increaseCount(id, count));
    }
}
