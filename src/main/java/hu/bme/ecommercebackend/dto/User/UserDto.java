package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    @NotNull
    private String id;

    @NotNull
    private Role role;

    @NotNull
    private String email;

    @NotNull
    private String firsName;

    @NotNull
    private String lastName;

    @NotNull
    private Integer savedNumber;

    @NotNull
    private Integer cartNumber;

    private Address address;

    @NotNull
    private Gender gender;

    private String phone;

    public UserDto(User user) {
        this.address = user.getAddress();
        this.cartNumber = user.getCart().size();
        this.email = user.getEmail();
        this.id = user.getId();
        this.role = user.getRole();
        this.firsName = user.getFirstName();
        this.lastName = user.getLastName();
        this.savedNumber = user.getSavedProducts().size();
        this.gender = user.getGender();
        this.phone = user.getPhoneNumber();
    }
}
