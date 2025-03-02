package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ImageService imageService;

    public BrandService(BrandRepository brandRepository, ImageService imageService) {
        this.brandRepository = brandRepository;
        this.imageService = imageService;
    }

    public BrandDto createBrand(BrandCreateDto brandCreateDto, MultipartFile image) {
        String imageEntity = this.imageService.saveImage(image);
        Brand brandEntity = brandRepository.save(new Brand(brandCreateDto.getName(), imageEntity, brandCreateDto.getDescription()));
        return new BrandDto(brandEntity.getId(), brandEntity.getImage(), brandEntity.getDescription(), brandEntity.getName());
    }

    public List<BrandDto> getBrandList() {
        return brandRepository.findAll().stream().map(
                BrandDto::new
        ).collect(Collectors.toList());
    }

    public BrandDto getBrandDtoById(Long id) {
        return new BrandDto(getBrandById(id));
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    public String deleteBrand(Long id) {
        Brand brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        imageService.deleteImageFromStorage(brandEntity.getImage());
        brandRepository.deleteById(id);
        return "Successful deleting";
    }

    public BrandDto modifyBrand(BrandDto brandDto) {
        Brand brandEntity = brandRepository.findById(brandDto.getId()).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        brandEntity.setDescription(brandDto.getDescription());
        brandEntity.setName(brandDto.getName());
        Brand modifiedBrand = brandRepository.save(brandEntity);
        return new BrandDto(modifiedBrand);
    }

    public BrandDto modifyImage(Long id, MultipartFile multipartFile) {
        Brand brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        if (!Objects.equals(imageService.deleteImage(brandEntity.getImage()), "Success")) {
            throw new RuntimeException("Delete failed");
        }
        String imageEntity = imageService.saveImage(multipartFile);
        brandEntity.setImage(imageEntity);
        return new BrandDto(brandRepository.save(brandEntity));
    }
}
