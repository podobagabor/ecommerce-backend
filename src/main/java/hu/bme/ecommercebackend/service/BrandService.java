package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandDto createBrand(BrandCreateDto brandCreateDto,MultipartFile image) {
        String imageUrl = saveImage(image);
        Brand brandEntity = brandRepository.save(new Brand(brandCreateDto.getName(),imageUrl,brandCreateDto.getDescription()));
        return new BrandDto(brandEntity.getId(),brandEntity.getImageUrl(),brandEntity.getDescription(),brandEntity.getName());
    }

    public List<BrandDto> getBrandList() {
        return brandRepository.findAll().stream().map(
                BrandDto::new
        ).collect(Collectors.toList());
    }

    public BrandDto getBrandById(Long id) {
        Brand brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        return new BrandDto(brandEntity);
    }

    public String deleteBrand(Long id) {
        brandRepository.deleteById(id);
        return "Successful deleting";
    }

    public BrandDto modifyBrand(BrandDto brandDto) {
        Brand brandEntity = brandRepository.findById(brandDto.getId()).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        brandEntity.setDescription(brandEntity.getDescription());
        brandEntity.setName(brandDto.getName());
        brandEntity.setImageUrl(brandDto.getImageUrl());
        Brand modifiedBrand = brandRepository.save(brandEntity);
        return new BrandDto(modifiedBrand);
    }

    private String saveImage(MultipartFile image) {
        try {
          String uplodDir = "files/";
          Files.createDirectories(Paths.get(uplodDir));

          String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
          Path filePath = Paths.get(uplodDir + fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
          return "/files/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
