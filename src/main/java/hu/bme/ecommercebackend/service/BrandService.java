package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Brand.BrandSimpleDto;
import hu.bme.ecommercebackend.dto.Common.ActionResponseDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.repository.BrandRepository;
import hu.bme.ecommercebackend.specification.OrderSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
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

    @Transactional
    public BrandDto createBrand(BrandCreateDto brandCreateDto, MultipartFile image) {
        String imageEntity = this.imageService.saveImage(image);
        Brand brandEntity = brandRepository.save(new Brand(brandCreateDto.getName(), imageEntity, brandCreateDto.getDescription()));
        return new BrandDto(brandEntity.getId(), brandEntity.getImage(), brandEntity.getDescription(), brandEntity.getName());
    }

    public List<BrandSimpleDto> getBrandList() {
        return brandRepository.findAll().stream().map(
                BrandSimpleDto::new
        ).collect(Collectors.toList());
    }

    public Page<BrandDto> getBrandPageable(String searchKey, Pageable pageable) {
        Specification<Brand> spec = OrderSpecification.filterBy(searchKey);
        return brandRepository.findAll(spec,pageable).map(
                BrandDto::new
        );
    }

    public BrandDto getBrandDtoById(Long id) {
        return new BrandDto(getBrandById(id));
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    @Transactional
    public ActionResponseDto deleteBrand(Long id) {
        try {
            Brand brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
            imageService.deleteImage(brandEntity.getImage());
            brandRepository.deleteById(id);
            return new ActionResponseDto(true,"");
        } catch (Exception e) {
            return new ActionResponseDto(false,e.toString());
        }

    }

    @Transactional
    public BrandDto modifyBrand(BrandDto brand, MultipartFile newImage) {
        Brand brandEntity = brandRepository.findById(brand.getId()).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        brandEntity.setDescription(brand.getDescription());
        brandEntity.setName(brand.getName());
        if(newImage != null) {
            this.imageService.deleteImage(brandEntity.getImage());
            brandEntity.setImage(this.imageService.saveImage(newImage));
        }
        return new BrandDto(brandEntity);
    }

    @Transactional
    public BrandDto modifyImage(Long id, MultipartFile multipartFile) {
        Brand brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        imageService.deleteImage(brandEntity.getImage());
        String imageEntity = imageService.saveImage(multipartFile);
        brandEntity.setImage(imageEntity);
        return new BrandDto(brandEntity);
    }
}
