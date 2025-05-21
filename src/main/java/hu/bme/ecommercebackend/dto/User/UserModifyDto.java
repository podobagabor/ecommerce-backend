package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserModifyDto {

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Gender gender;

    private Address address;
    private String phone;
}
