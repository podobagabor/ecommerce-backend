package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SavedItemsServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SavedItemsService savedItemsService;

    User mockUser1;
    User mockUser2;
    Product mockProduct1;


    @BeforeEach
    void init() {
        Category mockCategory1 = new Category(1L, "Ruházat");
        Brand mockBrand1 = new Brand(1L, "Samsung", "image_url", "Technical devices from Korea");
        mockProduct1 = new Product(1L, "Test poduct1", 2, "Teszt description1", null, Arrays.asList("TestUrl11", "TestUrl21"), 100, mockCategory1, mockBrand1);
        Product mockProduct2 = new Product(2L, "Test poduct2", 2, "Teszt description2", 10, Arrays.asList("TestUrl12", "TestUrl22"), 100, mockCategory1, mockBrand1);
        mockUser1 = new User("asdf", Role.USER, "testEmail1@email.com", "Test1First", "Test1Last", "06709887776", new HashSet<>(Set.of(mockProduct1, mockProduct2)), Gender.MALE, new ArrayList<>(), new ArrayList<>(), new Address("HU", "Dabas", "Temető utca", "23", "2371"));
        mockUser2 = new User("fdsa", Role.ADMIN, "testEmail2@email.com", "Test2First", "Test2Last", "06205454345", new HashSet<>(Set.of(mockProduct2)), Gender.MALE, new ArrayList<>(), new ArrayList<>(), new Address("HU", "Dabas", "Temető utca", "23", "2371"));

    }

    @Test
    void testGetSavedItemsOfUser() {
        when(userService.getSavedItemsOfUser(mockUser1.getId())).thenReturn(mockUser1.getSavedProducts().stream().map(ProductDto::new).collect(Collectors.toList()));
        List<Product> mockSavedProduct = mockUser1.getSavedProducts().stream().toList();

        List<ProductDto> savedProducts = savedItemsService.getSavedItemsOfUser(mockUser1.getId());

        assertEquals(savedProducts.size(), mockUser1.getSavedProducts().size());
        for (int i = 0; i < mockUser1.getSavedProducts().size(); i++) {
            assertEquals(new ProductDto(mockSavedProduct.get(i)), savedProducts.get(i));
        }
    }

    @Test
    void testAddProductToSaved() {
        User modifiedMockUser = new User(mockUser2.getId(), mockUser2.getRole(), mockUser2.getEmail(), mockUser2.getFirstName(), mockUser2.getLastName(), mockUser2.getPhoneNumber(), mockUser2.getSavedProducts(), mockUser2.getGender(), mockUser2.getCart(), mockUser2.getOrders(), mockUser2.getAddress());
        Set<Product> newSet = new HashSet<Product>(mockUser2.getSavedProducts());
        newSet.add(mockProduct1);
        modifiedMockUser.setSavedProducts(Set.copyOf(newSet));
        when(userService.getUserById(mockUser2.getId())).thenReturn(mockUser2);
        when(productService.getProductById(mockProduct1.getId())).thenReturn(mockProduct1);

        ProductDto product = savedItemsService.addProductToSaved(mockProduct1.getId(), mockUser2.getId());
        assertEquals(modifiedMockUser, mockUser2);
    }

    @Test
    void testRemoveProductToSaved() {
        User mockUser = new User(mockUser2.getId(), mockUser2.getRole(), mockUser2.getEmail(), mockUser2.getFirstName(), mockUser2.getLastName(), mockUser2.getPhoneNumber(), mockUser2.getSavedProducts(), mockUser2.getGender(), mockUser2.getCart(), mockUser2.getOrders(), mockUser2.getAddress());
        Set<Product> newSet = new HashSet<Product>(mockUser2.getSavedProducts());
        newSet.add(mockProduct1);
        mockUser.setSavedProducts(newSet);
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);

        Integer savedCount = savedItemsService.removeProductFromSaved(mockProduct1.getId(), mockUser2.getId());

        assertEquals(savedCount, mockUser2.getSavedProducts().size());
    }
}
