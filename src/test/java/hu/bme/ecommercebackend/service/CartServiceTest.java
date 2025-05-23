package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.IllegalActionException;
import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import hu.bme.ecommercebackend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartService cartService;

    CartElement mockCartElement1;
    CartElement mockCartElement2;
    User mockUser1;
    Product mockProduct1;

    @BeforeEach
    void init() {
        Category mockCategory1 = new Category(3L, "Ruházat");
        Brand mockBrand1 = new Brand(4L, "Samsung", "image_url", "Technical devices from Korea");
        mockProduct1 = new Product(1L, "Test poduct1", 8, "Teszt description1", null, Arrays.asList("TestUrl11", "TestUrl21"), 100, mockCategory1, mockBrand1);
        Product mockProduct2 = new Product(2L, "Test poduct2", 2, "Teszt description2", 10, Arrays.asList("TestUrl12", "TestUrl22"), 100, mockCategory1, mockBrand1);
        mockUser1 = new User("asdf", Role.USER, "testEmail1@email.com", "Test1First", "Test1Last", "06301212012", Set.of(mockProduct1), Gender.MALE, new ArrayList<>(), new ArrayList<>(), new Address("HU", "Dabas", "Temető utca", "23", "2371"));
        mockCartElement1 = new CartElement(3L, mockProduct1, 2, mockUser1);
        mockCartElement2 = new CartElement(4L, mockProduct2, 3, mockUser1);
        mockUser1.getCart().add(mockCartElement1);
        mockUser1.getCart().add(mockCartElement2);
    }

    @Test
    void testGetCartElementById() {
        when(cartRepository.findById(mockCartElement1.getId())).thenReturn(Optional.ofNullable(mockCartElement1));

        CartElement cartElement = cartService.getCartElementById(mockCartElement1.getId());

        assertEquals(cartElement, mockCartElement1);
    }

    @Test
    void testDeleteCartElement() {
        doNothing().when(cartRepository).deleteById(mockCartElement1.getId());

        boolean result = cartService.deleteCartElement(mockCartElement1.getId());

        assertTrue(result);
    }

    @Test
    void testDeleteCartElementFromUser() {
        doNothing().when(cartRepository).deleteById(mockCartElement1.getId());
        when(cartRepository.findUserById(mockCartElement1.getId())).thenReturn(mockUser1);

        cartService.deleteCartElementFromUser(mockCartElement1.getId(), mockUser1.getId());
    }

    @Test
    void testModifyQuantity() {
        CartElement modifiedCartElement = new CartElement(mockCartElement1.getId(), mockCartElement1.getProduct(), 5, mockCartElement1.getUser());
        when(cartRepository.findById(mockCartElement1.getId())).thenReturn(Optional.ofNullable(mockCartElement1));
        CartElementDto cartElementDto = cartService.modifyQuantity(mockCartElement1.getId(), 5, mockUser1.getId());

        assertEquals(cartElementDto, new CartElementDto(modifiedCartElement));
    }

    @Test
    void testModifyQuantityMinus() {
        when(cartRepository.findById(mockCartElement1.getId())).thenReturn(Optional.ofNullable(mockCartElement1));

        assertThrows(IllegalActionException.class, () -> cartService.modifyQuantity(mockCartElement1.getId(), -1, mockUser1.getId()));
    }

    @Test
    void testAddToCart() {
        User mockUser = new User(mockUser1.getId(), mockUser1.getRole(), mockUser1.getEmail(), mockUser1.getFirstName(), mockUser1.getLastName(), mockUser1.getPhoneNumber(), new HashSet<>(), mockUser1.getGender(), new ArrayList<CartElement>(), new ArrayList<>(), mockUser1.getAddress());
        CartElement mockCartElement = new CartElement(null, mockCartElement1.getProduct(), mockCartElement1.getQuantity(), mockUser);
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
        mockUser.getCart().add(mockCartElement);
        when(productService.getProductById(mockProduct1.getId())).thenReturn(mockProduct1);
        when(cartRepository.save(mockCartElement)).thenReturn(mockCartElement);

        CartElementDto cartElementDto = cartService.addToCart(mockUser.getId(), new CartElementCreateDto(mockCartElement.getProduct().getId(), mockCartElement.getQuantity()));

        assertEquals(cartElementDto, new CartElementDto(mockCartElement));
    }

    @Test
    void testGetCartOfUser() {
        when(userService.getUserById(mockUser1.getId())).thenReturn(mockUser1);

        List<CartElementDto> cart = cartService.getCartOfUser(mockUser1.getId());

        assertEquals(cart.size(), mockUser1.getCart().size());
        for (int i = 0; i < cart.size(); i++) {
            assertEquals(cart.get(i), new CartElementDto(mockUser1.getCart().get(i)));
        }
    }
}
