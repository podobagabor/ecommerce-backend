package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.customExceptions.IllegalActionException;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.dto.User.UserDtoDetailed;
import hu.bme.ecommercebackend.dto.User.UserModifyDto;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.TokenType;
import hu.bme.ecommercebackend.repository.UserRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    public UserService(UserRepository userRepository,
                       KeycloakService keycloakService,
                       EmailService emailService,
                       VerificationTokenService verificationTokenService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public User getUserById(String id) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown user entity"));
        if (!userEntity.getSavedProducts().isEmpty() && userEntity.getSavedProducts().stream().anyMatch(product -> !product.getActive())) {
            userEntity.setSavedProducts(userEntity.getSavedProducts().stream().filter(Product::getActive).collect(Collectors.toSet()));
        }
        return userEntity;
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
        Boolean emailExist = userRepository.existsByEmail(user.getEmail());
        if (emailExist) {
            throw new IllegalActionException("Ez az e-mailcím már használva van");
        } else {
            String userId = keycloakService.registerUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            user.setId(userId);
            User userEntity = userRepository.save(new User(user));
            String verificationToken = verificationTokenService.saveToken(userEntity, TokenType.EMAIL);
            emailService.sendEmail(userEntity.getEmail(), "Email validation for registration", this.emailService.getVerificationMessage(userEntity.getFirstName(), verificationToken));
            return new UserDto(userEntity);
        }
    }

    @Transactional
    public UserDto validateUserEmail(String token) {
        Pair<User, Boolean> result = verificationTokenService.handleValidation(token, TokenType.EMAIL);
        if (!result.getRight()) {
            String verificationToken = verificationTokenService.saveToken(result.getLeft(), TokenType.EMAIL);
            emailService.sendEmail(result.getLeft().getEmail(), "Email validation for registration", this.emailService.getVerificationMessageAgain(result.getLeft().getFirstName(), verificationToken));
        }
        keycloakService.validateEmail(result.getLeft().getId());
        return new UserDto(result.getLeft());
    }

    @Transactional
    public void requestNewPassword(String email) {
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Unknown user email"));
        String userToken = verificationTokenService.saveToken(userEntity, TokenType.PASSWORD);
        emailService.sendEmail(userEntity.getEmail(), "Request new password", this.emailService.getPasswordResetMessage(userEntity.getFirstName(), userToken));
    }

    @Transactional
    public void setNewPassword(String token, String password) {
        Pair<User, Boolean> result = verificationTokenService.handleValidation(token, TokenType.PASSWORD);
        if (!result.getRight()) {
            String userToken = verificationTokenService.saveToken(result.getLeft(), TokenType.PASSWORD);
            emailService.sendEmail(result.getLeft().getEmail(), "Password reset", this.emailService.getPasswordResetMessageAgain(result.getLeft().getFirstName(), userToken));
        } else {
            keycloakService.setNewPassword(password, result.getLeft().getId());
        }
    }


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserDtoDetailed modifyUser(String userId, UserModifyDto userDto) {
        User userEntity = getUserById(userId);
        if(!Objects.equals(userEntity.getEmail(), userDto.getEmail())) {
            this.keycloakService.changeEmail(userId,userDto.getEmail());
        }
        userEntity.setAddress(userDto.getAddress());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPhoneNumber(userDto.getPhone());
        userEntity.setGender(userDto.getGender());
        return new UserDtoDetailed(userEntity);
    }

    @Transactional
    public List<ProductDto> getSavedItemsOfUser(String userId) {
        return userRepository.findSavedProductsByUser_Id(userId).stream().filter(Product::getActive).map(ProductDto::new).collect(Collectors.toList());
    }
}
