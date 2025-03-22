package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.Role;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User mockUser1;
    User mockUser2;

    @BeforeEach()
    void init() {
        Category mockCategory1 = new Category(1L, "Ruházat");
        Brand mockBrand1 = new Brand(1L, "Samsung", "image_url", "Technical devices from Korea");
        Product mockProduct1 = new Product(1L, "Test poduct1", 2, "Teszt description1", null, Arrays.asList("TestUrl11", "TestUrl21"), 100, mockCategory1, mockBrand1);
        Product mockProduct2 = new Product(2L, "Test poduct2", 2, "Teszt description2", 10, Arrays.asList("TestUrl12", "TestUrl22"), 100, mockCategory1, mockBrand1);
        mockUser1 = new User("asdf", Role.USER,"testEmail1@email.com","Test1First","Test1Last", Set.of(mockProduct1), new ArrayList<>(),new Address("HU","Dabas","Temető utca","23","2371"));
        mockUser2 = new User("fdsa", Role.ADMIN,"testEmail2@email.com","Test2First","Test2Last", Set.of(mockProduct1), new ArrayList<>(),new Address("HU","Dabas","Temető utca","23","2371"));
        CartElement mockCartElement1 = new CartElement(mockProduct2,2,mockUser1);
        CartElement mockCartElement2 = new CartElement(mockProduct2,2,mockUser2);
        mockUser1.getCart().add(mockCartElement1);
        mockUser1.getCart().add(mockCartElement2);

    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.ofNullable(mockUser1));

        User user = userService.getUserById(mockUser1.getId());

        assertEquals(user, mockUser1);
    }

    @Test
    void testGetUserReferenceById() {
        when(userRepository.getReferenceById(mockUser1.getId())).thenReturn(mockUser1);

        User user = userService.getUserReferenceById(mockUser1.getId());

        assertEquals(user, mockUser1);
    }

    @Test
    void testGetUserDtoById() {
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.ofNullable(mockUser1));

        UserDto user = userService.getUserDtoById(mockUser1.getId());

        assertEquals(user, new UserDto(mockUser1));
    }

    @Test
    void testCreateUser() {
        User mockUser = new User("asdf", Role.USER,"testEmail1@email.com","Test1First","Test1Last", Set.of(), List.of(),new Address("HU","Dabas","Temető utca","23","2371"));;
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        UserCreateDto userCreateDto = new UserCreateDto(mockUser1.getId(),mockUser1.getRole(),mockUser1.getEmail(),mockUser1.getFirstName(),mockUser1.getLastName(),mockUser1.getAddress());

        UserDto user = userService.createUser(userCreateDto);

        assertEquals(user,new UserDto(mockUser));
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUserList = Arrays.asList(mockUser1,mockUser2);
        when(userRepository.findAll()).thenReturn(mockUserList);

        List<UserDto> users = userService.getAllUsers();

        assertEquals(users.size(),mockUserList.size());
        for(int i=0; i<users.size();i++) {
            assertEquals(users.get(i),new UserDto(mockUserList.get(i)));
        }
    }

    @Test
    void testModifyUser() {
        User modifiedUser = new User(mockUser1.getId(),mockUser1.getRole(),mockUser1.getEmail() + " - new",mockUser1.getFirstName() + " - new",mockUser1.getLastName() + " - new",mockUser1.getSavedProducts(),mockUser1.getCart(),mockUser1.getAddress());
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.ofNullable(mockUser1));
        when(userRepository.save(modifiedUser)).thenReturn(modifiedUser);

        UserDto user = userService.modifyUser(new UserDto(modifiedUser));

        assertEquals(user,new UserDto(modifiedUser));
    }


}
