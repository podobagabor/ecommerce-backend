package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    private String id;
    private Role role;
    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    @Nullable
    private Address address;
    @Nullable
    private String phone;
    private String password;
}
