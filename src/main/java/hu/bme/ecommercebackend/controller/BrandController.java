package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.service.BrandService;
import hu.bme.ecommercebackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/create")
    public ResponseEntity<BrandDto> createBrand(@RequestParam("name") String name,@RequestParam("description") String description, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.brandService.createBrand(new BrandCreateDto(name,description),image));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<BrandDto>> getBrandList() {
        return ResponseEntity.ok(this.brandService.getBrandList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandDtoById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.deleteBrand(id));
    }

    @PutMapping(value = "/modify")
    public ResponseEntity<BrandDto> modifyBrand(@RequestBody BrandDto brandDto) {
        return ResponseEntity.ok(brandService.modifyBrand(brandDto));
    }

    @PutMapping(value = "/modifyImage/{id}")
    public ResponseEntity<BrandDto> modifyBrand(@PathVariable Long id, @RequestParam("newImage") MultipartFile file) {
        return ResponseEntity.ok(brandService.modifyImage(id,file));
    }
}
