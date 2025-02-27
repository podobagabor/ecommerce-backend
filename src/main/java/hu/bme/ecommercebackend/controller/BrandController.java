package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.service.BrandService;
import hu.bme.ecommercebackend.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
    }

    @PostMapping("/create")
    public BrandDto createBrand(@RequestParam("name") String name,@RequestParam("description") String description, @RequestParam("image") MultipartFile image) {
        return this.brandService.createBrand(new BrandCreateDto(name,description),image);
    }

    @GetMapping("/list")
    public List<BrandDto> getBrandList() {
        return this.brandService.getBrandList();
    }

    @GetMapping("/{id}")
    public BrandDto getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        return brandService.deleteBrand(id);
    }

    @PutMapping("/modify")
    public BrandDto modifyBrand(@RequestBody BrandDto brandDto) {
        return brandService.modifyBrand(brandDto);
    }
}
