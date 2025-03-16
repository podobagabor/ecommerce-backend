package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.User.CartElementCreateDto;
import hu.bme.ecommercebackend.dto.User.CartElementDto;
import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProductService productService;
    public UserService(UserRepository userRepository,
                       ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
    }

    public UserDto getUserDtoById(String id) {
        return new UserDto(getUserById(id));
    }

    public UserDto createUser(UserCreateDto user) {
        return new UserDto(userRepository.save(new User(user)));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto modifyUser(UserDto userDto) {
        User userEntity = getUserById(userDto.getId());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirsName(userEntity.getFirsName());
        userEntity.setLastName(userEntity.getLastName());
        return new UserDto(userRepository.save(userEntity));
    }
}
