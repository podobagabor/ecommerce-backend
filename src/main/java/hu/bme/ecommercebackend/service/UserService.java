package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.dto.User.UserDtoDetailed;
import hu.bme.ecommercebackend.dto.User.UserModifyDto;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.TokenType;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    public UserService(UserRepository userRepository,
                       ProductService productService,
                       KeycloakService keycloakService,
                       EmailService emailService,
                       VerificationTokenService verificationTokenService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown user entity"));
    }

    public User getUserReferenceById(String id) {
        return userRepository.getReferenceById(id);
    }

    public UserDto getUserDtoById(String id) {
        return new UserDto(getUserById(id));
    }

    public UserDtoDetailed getUserDtoDetailedById(String id) {
        return new UserDtoDetailed(getUserById(id));
    }

    @Transactional
    public UserDto createUser(UserCreateDto user) {
        String userId = keycloakService.registerUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        user.setId(userId);
        User userEntity = userRepository.save(new User(user));
        String verificationToken = verificationTokenService.saveToken(userEntity, TokenType.EMAIL);
        emailService.sendEmail(user.getEmail(), "Email validation for registration", "http://localhost:4200?userToken=" + verificationToken + "&emailVerification=true");
        return new UserDto(userEntity);
    }

    @Transactional
    public UserDto validateUserEmail(String token) {
        User userEntity = verificationTokenService.handleValidation(token);
        keycloakService.validateEmail(userEntity.getId());
        return new UserDto(userEntity);
    }

    @Transactional
    public void requestNewPassword(String email) {
        //Todo: szép email
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Unknown user email"));
        String userToken = verificationTokenService.saveToken(userEntity, TokenType.PASSWORD);
        emailService.sendEmail(userEntity.getEmail(), "Password reset", "http://localhost:4200?forgotPassword=true&userToken=" + userToken);
    }

    @Transactional
    public void setNewPassword(String token, String password) {
        User userEntity = verificationTokenService.handleValidation(token);
        keycloakService.setNewPassword(password, userEntity.getId());
    }


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserDtoDetailed modifyUser(String userId, UserModifyDto userDto) {
        User userEntity = getUserById(userId);
        userEntity.setAddress(userDto.getAddress());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPhoneNumber(userDto.getPhone());
        return new UserDtoDetailed(userEntity);
    }
}
