package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private BrandService brandService;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private ProductService productService;

    Product mockProduct1;
    Product mockProduct2;

    Category mockCategory1;
    Brand mockBrand1;

    @BeforeEach()
    void init() {
        mockCategory1 = new Category(3L, "Ruházat");
        mockBrand1 = new Brand(4L, "Samsung", "image_url", "Technical devices from Korea");
        mockProduct1 = new Product(1L,"Test poduct1",2,"Teszt description1",null, Arrays.asList("TestUrl11","TestUrl21"),100,mockCategory1,mockBrand1);
        mockProduct2 = new Product(2L,"Test poduct2",2,"Teszt description2",10, Arrays.asList("TestUrl12","TestUrl22"),100,mockCategory1,mockBrand1);
    }

    @Test
    void testGetProductList() {
        List<Product> mockProductList = Arrays.asList(
                mockProduct1,
                mockProduct2
        );
        when(productRepository.findAll()).thenReturn(mockProductList);

        List<ProductDto> products = productService.getProductList();

        assertEquals(mockProductList.size(),products.size());
        for(int i =0; i<products.size();i++) {
            assertEquals(products.get(i),new ProductDto(mockProductList.get(i)));
        }
    }

    @Test
    void testGetProductDtoById() {
        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.ofNullable(mockProduct1));

        ProductDto productDto = productService.getProductDtoById(mockProduct1.getId());

        assertEquals(productDto, new ProductDto(mockProduct1));
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.ofNullable(mockProduct1));

        Product product = productService.getProductById(mockProduct1.getId());

        assertEquals(product, mockProduct1);
    }

    @Test
    void testGetProductReference() {
        when(productRepository.getReferenceById(mockProduct1.getId())).thenReturn(mockProduct1);

        Product product = productService.getProductReferenceById(mockProduct1.getId());

        assertEquals(product,mockProduct1);
    }

    @Test
    void testDeleteProductById(){
        doNothing().when(productRepository).deleteById(mockProduct1.getId());

        Boolean result = productService.deleteProductById(mockProduct1.getId());

        assertEquals(result,true);
    }

    @Test
    void testCreateProduct() {
        ProductCreateDto mockProductCreateDto = new ProductCreateDto(mockProduct1.getName(),mockProduct1.getCount(),mockProduct1.getDescription(),mockProduct1.getDiscountPercentage(),new ArrayList<MultipartFile>(),mockProduct1.getPrice(),mockCategory1.getId(),mockBrand1.getId());
        Product mockProductWithoutId = new Product(null,mockProductCreateDto.getName(),mockProductCreateDto.getCount(),mockProductCreateDto.getDescription(),mockProductCreateDto.getDiscountPercentage(),mockProductCreateDto.getImages().stream().map(multipartFile -> "url").toList(),mockProductCreateDto.getPrice(),mockCategory1,mockBrand1);
        //if files will be handled
        //when(imageService.saveImage(null)).thenReturn("url");
        when(categoryService.getCategoryById(mockCategory1.getId())).thenReturn(mockCategory1);
        when(brandService.getBrandById(mockBrand1.getId())).thenReturn(mockBrand1);
        when(productRepository.save(mockProductWithoutId)).thenReturn(mockProduct1);

        ProductDto product = productService.createProduct(mockProductCreateDto);

        assertEquals(product,new ProductDto(mockProduct1));
    }


}
