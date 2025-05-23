package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ImageModifyDto;
import hu.bme.ecommercebackend.dto.Product.ProductCreateDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.dto.Product.ProductModifyDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ProductService productService;

    Product mockProduct1;
    Product mockProduct2;

    Category mockCategory1;
    Brand mockBrand1;

    MultipartFile emptyFile1;
    MultipartFile emptyFile2;

    String fileName1;
    String fileName2;

    @Value("${admin.email}")
    private String adminEmail;

    @BeforeEach()
    void init() {
        mockCategory1 = new Category(3L, "Ruházat");
        fileName1 = "TestUrl1";
        fileName2 = "TestUrl2";
        mockBrand1 = new Brand(4L, "Samsung", "image_url", "Technical devices from Korea");
        mockProduct1 = new Product(1L, "Test poduct1", 2, "Teszt description1", null, Arrays.asList(fileName1, fileName2), 100, mockCategory1, mockBrand1);
        mockProduct2 = new Product(2L, "Test poduct2", 2, "Teszt description2", 10, Arrays.asList(fileName1, fileName2), 100, mockCategory1, mockBrand1);
        emptyFile1 = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);
        emptyFile2 = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);
    }

    @Test
    void testGetProductList() {
        List<Product> mockProductList = Arrays.asList(
                mockProduct1,
                mockProduct2
        );
        when(productRepository.findAll()).thenReturn(mockProductList);

        List<ProductDto> products = productService.getProductList();

        assertEquals(mockProductList.size(), products.size());
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i), new ProductDto(mockProductList.get(i)));
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

        assertEquals(product, mockProduct1);
    }

    @Test
    void testDeleteProductById() {
        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.ofNullable(mockProduct1));

        Boolean result = productService.deleteProductById(mockProduct1.getId());

        assertEquals(result, true);
    }

    @Test
    void testCreateProduct() {
        ProductCreateDto mockProductCreateDto = new ProductCreateDto(mockProduct1.getName(), mockProduct1.getCount(), mockProduct1.getDescription(), mockProduct1.getDiscountPercentage(), mockProduct1.getPrice(), mockCategory1.getId(), mockBrand1.getId());
        Product mockProductWithoutId = new Product(null, mockProductCreateDto.getName(), mockProductCreateDto.getCount(), mockProductCreateDto.getDescription(), mockProductCreateDto.getDiscountPercentage(), Arrays.asList(fileName1, fileName2), mockProductCreateDto.getPrice(), mockCategory1, mockBrand1);
        when(categoryService.getCategoryById(mockCategory1.getId())).thenReturn(mockCategory1);
        when(brandService.getBrandById(mockBrand1.getId())).thenReturn(mockBrand1);
        when(productRepository.save(mockProductWithoutId)).thenReturn(mockProduct1);
        when(imageService.saveImage(emptyFile1)).thenReturn(fileName1);
        when(imageService.saveImage(emptyFile2)).thenReturn(fileName2);
        ProductDto product = productService.createProduct(mockProductCreateDto, Arrays.asList(emptyFile1, emptyFile2));
        assertEquals(product, new ProductDto(mockProduct1));
    }

    @Test
    void testReduceCount() {
        String emailMessage = "Test email message";

        Product mockProductWithReducedCount = new Product(null, null, mockProduct1.getCount() - 1, null, null, null, null, null, null, mockProduct1);

        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.of(new Product(mockProduct1)));
        when(emailService.getShortageOfStochForAdmin(mockProductWithReducedCount)).thenReturn(emailMessage);
        doNothing().when(emailService).sendEmail(adminEmail, "Shortage of stock", emailMessage);

        ProductDto productDto = productService.reduceCount(mockProduct1.getId(), 1);

        assertEquals(productDto.getId(), mockProduct1.getId());
        assertEquals(productDto.getCount(), mockProduct1.getCount() - 1);
    }

    @Test
    void testIncreaseCount() {
        Product mockProductWithIncreasedCount = new Product(null, null, mockProduct1.getCount() + 1, null, null, null, null, null, null, mockProduct1);
        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.of(new Product(mockProduct1)));
        ProductDto productDto = productService.increaseCount(mockProduct1.getId(), 1);

        assertEquals(productDto.getId(), mockProduct1.getId());
        assertEquals(productDto.getCount(), mockProduct1.getCount() + 1);
    }

    @Test
    void testModifyProduct() {
        Category mockCategory2 = new Category(2L, "Pólók", new ArrayList<>(), mockCategory1);
        Brand mockBrand2 = new Brand(2L, "Sony", "image_url", "Technical devices from Japan");

        Product modifiedMockProduct = new Product(null, mockProduct1.getName() + " - new", null, null, null, Arrays.asList(fileName2, fileName2), null, mockCategory2, mockBrand2, mockProduct1);

        ProductModifyDto mockProductCreateDto = new ProductModifyDto(
                modifiedMockProduct.getId(), modifiedMockProduct.getName(), modifiedMockProduct.getCount(),
                modifiedMockProduct.getDescription(), modifiedMockProduct.getDiscountPercentage(),
                Arrays.asList(new ImageModifyDto(fileName1, true), new ImageModifyDto(fileName2, false))
                , modifiedMockProduct.getPrice(), modifiedMockProduct.getCategory().getId(), modifiedMockProduct.getBrand().getId()
        );

        when(imageService.saveImage(emptyFile2)).thenReturn(fileName2);
        doNothing().when(imageService).deleteImage(fileName1);

        when(productRepository.findById(mockProduct1.getId())).thenReturn(Optional.of(mockProduct1));
        when(categoryService.getCategoryById(mockCategory2.getId())).thenReturn(mockCategory2);
        when(brandService.getBrandById(mockBrand2.getId())).thenReturn(mockBrand2);

        ProductDto modifiedProduct = productService.modifyProduct(mockProductCreateDto, Collections.singletonList(emptyFile2));

        assertEquals(modifiedProduct, new ProductDto(modifiedMockProduct));
    }
}
