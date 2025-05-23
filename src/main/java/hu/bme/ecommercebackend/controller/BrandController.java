package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Brand.BrandSimpleDto;
import hu.bme.ecommercebackend.service.BrandService;
import hu.bme.ecommercebackend.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BrandDto> createBrand(@RequestPart("brand") BrandCreateDto brand, @RequestPart(value = "image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.brandService.createBrand(brand, image));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<BrandDto>> getBrandPageable(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortId,
            @RequestParam(required = false) Sort.Direction sortDirection
    ) {
        Pageable pageable = PageRequest.of(page, size, sortDirection == null ? Sort.Direction.ASC : sortDirection, sortId);
        return ResponseEntity.ok(this.brandService.getBrandPageable(name, pageable));
    }

    @GetMapping()
    public ResponseEntity<List<BrandSimpleDto>> getBrands() {
        return ResponseEntity.ok(this.brandService.getBrandList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandDtoById(id));
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @PutMapping(value = "/modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BrandDto> modifyBrand(@RequestPart("brand") BrandDto brand,
                                                @RequestPart(value = "newImage", required = false) MultipartFile newImage) {
        return ResponseEntity.ok(brandService.modifyBrand(brand, newImage));
    }

}
