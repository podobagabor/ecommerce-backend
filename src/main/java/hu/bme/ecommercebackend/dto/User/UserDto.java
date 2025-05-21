package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && role == userDto.role && Objects.equals(email, userDto.email) && Objects.equals(firsName, userDto.firsName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(savedNumber, userDto.savedNumber) && Objects.equals(cartNumber, userDto.cartNumber) && Objects.equals(address, userDto.address) && gender == userDto.gender && Objects.equals(phone, userDto.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, email, firsName, lastName, savedNumber, cartNumber, address, gender, phone);
    }
}
