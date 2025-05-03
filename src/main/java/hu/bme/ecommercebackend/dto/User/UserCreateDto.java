package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {

    @Nullable
    private String id;

    private Role role;

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Gender gender;
    @Nullable
    private Address address;
    @Nullable
    private String phone;

    @NotNull
    private String password;
}
