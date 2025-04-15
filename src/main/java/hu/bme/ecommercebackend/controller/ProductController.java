package hu.bme.ecommercebackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.dto.Product.ProductModifyDto;
import hu.bme.ecommercebackend.service.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok(productService.getProductList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDtoById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(
            @RequestPart("product") ProductCreateDto productCreateDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productCreateDto,images));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @PutMapping(value = "reduce/{id}/{count}")
    public ResponseEntity<ProductDto> reduceProductCount(@PathVariable Long id, @PathVariable Integer count) {
        return ResponseEntity.ok(productService.reduceCount(id, count));
    }

    @PutMapping(value = "increase/{id}/{count}")
    public ResponseEntity<ProductDto> increaseProductCount(@PathVariable Long id, @PathVariable Integer count) {
        return ResponseEntity.ok(productService.increaseCount(id, count));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<ProductDto>> getProductsByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> categoryId,
            @RequestParam(defaultValue = "false") Boolean discount,
            @RequestParam(defaultValue = "0") Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false)  List<Long> brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortId,
            @RequestParam(required = false) Sort.Direction sortDirection
            ) {
        Pageable pageable = PageRequest.of(page,size, sortDirection == null ? Sort.Direction.ASC : sortDirection , sortId);
        return ResponseEntity.ok(productService.findAll(name,categoryId,discount,minPrice,maxPrice,brandId,pageable));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductModifyDto productModifyDto) {
        return ResponseEntity.ok(productService.modifyProduct(productModifyDto));
    }
}
