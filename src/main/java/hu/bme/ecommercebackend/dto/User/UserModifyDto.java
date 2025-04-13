package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserModifyDto {
    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Address address;
    private String phone;
}
