package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.User.*;
import hu.bme.ecommercebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/current")
    public ResponseEntity<UserDtoDetailed> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.getUserDtoDetailedById(jwt.getSubject()));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping(value = "/modify")
    public ResponseEntity<UserDtoDetailed> modifyUser(@RequestBody UserModifyDto user, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.modifyUser(jwt.getSubject(), user));
    }

    @GetMapping(value = "verifyEmail/{token}")
    public ResponseEntity<UserDto> validateUserEmail(@PathVariable String token) {
        return ResponseEntity.ok(userService.validateUserEmail(token));
    }

    @PostMapping(value = "/passwordChangeRequest")
    public ResponseEntity<Void> requestPasswordChange(@RequestBody String email) {
        userService.requestNewPassword(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/setNewPassword")
    public ResponseEntity<Void> setNewPassword(@RequestBody NewPasswordDto newPasswordDto) {
        userService.setNewPassword(newPasswordDto.getToken(), newPasswordDto.getNewPassword());
        return ResponseEntity.accepted().build();
    }
}
