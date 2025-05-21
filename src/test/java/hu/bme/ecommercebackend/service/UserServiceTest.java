package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.dto.User.UserDtoDetailed;
import hu.bme.ecommercebackend.dto.User.UserModifyDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import hu.bme.ecommercebackend.model.enums.TokenType;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    KeycloakService keycloakService;
    @Mock
    EmailService emailService;
    @Mock
    VerificationTokenService verificationTokenService;

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

        mockUser1 = new User("asdf", Role.USER,"testEmail1@email.com","Test1First","Test1Last","063010102" ,Set.of(mockProduct1), Gender.MALE, new ArrayList<>(), new ArrayList<>(),new Address("HU","Dabas","Fő utca","23","2371"));
        mockUser2 = new User("fdsa", Role.ADMIN,"testEmail2@email.com","Test2First","Test2Last","0640505050", Set.of(mockProduct1),Gender.FEMALE, new ArrayList<>(), new ArrayList<>(),new Address("HU","Dabas","Temető utca","23","2371"));

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
        User mockUser = new User("asdf", Role.USER,"testEmail1@email.com","Test1First","Test1Last","0640121214", Set.of(),Gender.FEMALE, new ArrayList<>(), new ArrayList<>(),new Address("HU","Dabas","Temető utca","23","2371"));
        when(userRepository.save(argThat(user ->
                user.equals(mockUser)
                ))).thenReturn(mockUser);
        when(verificationTokenService.saveToken(mockUser, TokenType.EMAIL)).thenReturn("abc123");
        when(emailService.getVerificationMessage(mockUser.getFirstName(),"abc123")).thenReturn("message");
        doNothing().when(emailService).sendEmail(mockUser.getEmail(),"Email validation for registration","message");

        when(keycloakService.registerUser(mockUser.getEmail(),mockUser.getFirstName(),mockUser.getLastName(),mockUser.getEmail(),"password")).thenReturn(mockUser.getId());
        UserCreateDto userCreateDto = new UserCreateDto(mockUser.getId(),mockUser.getRole(),mockUser.getEmail(),mockUser.getFirstName(),mockUser.getLastName(),mockUser.getGender(),mockUser.getAddress(),mockUser.getPhoneNumber(),"password");

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
        User modifiedUser = new User("asdf", Role.USER,"testEmail1@email-new.com","Test1FirstNew","Test1LastNew","0640121214", Set.of(),Gender.FEMALE, new ArrayList<>(), new ArrayList<>(),new Address("HU","Dabas","Temető utca","23","2371"));;
        when(userRepository.findById(modifiedUser.getId())).thenReturn(Optional.ofNullable(modifiedUser));

        UserDtoDetailed user = userService.modifyUser(modifiedUser.getId(),new UserModifyDto(modifiedUser.getEmail(),modifiedUser.getFirstName(),modifiedUser.getLastName(),modifiedUser.getGender(),modifiedUser.getAddress(),modifiedUser.getPhoneNumber()));

        assertEquals(user,new UserDtoDetailed(modifiedUser));
    }


}
