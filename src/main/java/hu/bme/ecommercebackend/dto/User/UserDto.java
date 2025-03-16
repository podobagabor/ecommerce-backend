package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String id;
    private Role role;
    private String email;
    private String firsName;
    private String lastName;
    private Integer savedNumber;
    private Integer cartNumber;
    private Address address;

    public UserDto(User user) {
        this.address = user.getAddress();
        this.cartNumber = user.getCart().size();
        this.email = user.getEmail();
        this.id = user.getId();
        this.role = user.getRole();
        this.firsName = user.getFirsName();
        this.lastName = user.getLastName();
        this.savedNumber = user.getSavedProducts().size();
    }
}
