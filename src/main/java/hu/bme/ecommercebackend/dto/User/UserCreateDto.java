package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String id;
    private Role role;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;
}
