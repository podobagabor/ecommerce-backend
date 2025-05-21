package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Brand.BrandCreateDto;
import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Brand.BrandSimpleDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private BrandService brandService;

    Brand mockBrand1;
    Brand mockBrand2;

    @BeforeEach()
    void init() {
        mockBrand1 = new Brand(1L, "Samsung", "image_url", "Technical devices from Korea");
        mockBrand2 = new Brand(2L, "Sony", "image_url", "Technical devices from Japan");
    }

    @Test
    void testCreateBrand() {
        BrandCreateDto mockCreateBrand = new BrandCreateDto("Gorenje", "For kitchen");
        Brand mockBrandWithoutId = new Brand(null, mockCreateBrand.getName(), null, mockCreateBrand.getDescription());
        when(brandRepository.save(mockBrandWithoutId)).thenReturn(mockBrandWithoutId);

        BrandDto brand = brandService.createBrand(mockCreateBrand, null);

        assertEquals(brand, new BrandDto(mockBrandWithoutId));
    }

    @Test
    void testGetBrandList() {
        List<Brand> mockBrandList = Arrays.asList(
                mockBrand1,
                mockBrand2
        );
        when(brandRepository.findAll()).thenReturn(mockBrandList);

        List<BrandSimpleDto> brandList = brandService.getBrandList();

        assertEquals(mockBrandList.size(), brandList.size());
        for (int i = 0; i < brandList.size(); i++) {
            assertEquals(brandList.get(i), new BrandSimpleDto(mockBrandList.get(i)));
        }
    }


    @Test
    void testGetBrandDtoById() {
        when(brandRepository.findById(mockBrand1.getId())).thenReturn(Optional.ofNullable(mockBrand1));

        BrandDto brandDto = brandService.getBrandDtoById(mockBrand1.getId());

        assertEquals(brandDto, new BrandDto(mockBrand1));
    }

    @Test
    void testGetBrandById() {
        when(brandRepository.findById(mockBrand1.getId())).thenReturn(Optional.ofNullable(mockBrand1));

        Brand brand = brandService.getBrandById(mockBrand1.getId());

        assertEquals(brand, mockBrand1);
    }

    @Test
    void testModifyBrand() {
        Brand modifiedBrand = new Brand(mockBrand1.getId(), mockBrand1.getName() + " - new", mockBrand1.getImage(), mockBrand1.getDescription());
        when(brandRepository.findById(mockBrand1.getId())).thenReturn(Optional.ofNullable(mockBrand1));

        BrandDto brandDto = brandService.modifyBrand(new BrandDto(modifiedBrand), null);
        assertEquals(brandDto, new BrandDto(modifiedBrand));
    }

    @Test
    void testDeleteBrand() {
        doNothing().when(brandRepository).deleteById(mockBrand1.getId());
        when(brandRepository.findById(mockBrand1.getId())).thenReturn(Optional.ofNullable(mockBrand1));
        doNothing().when(imageService).deleteImage(mockBrand1.getImage());
        brandService.deleteBrand(mockBrand1.getId());
    }
}
