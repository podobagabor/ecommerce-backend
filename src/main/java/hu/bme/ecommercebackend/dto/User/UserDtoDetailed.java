package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDtoDetailed {

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
    private List<ProductDto> savedItems;

    @NotNull
    private List<CartElementDto> cartItems;

    private Address address;

    @NotNull
    private Gender gender;

    private String phone;

    public UserDtoDetailed(User user) {
        this.address = user.getAddress();
        this.cartItems = user.getCart().stream().map(CartElementDto::new).collect(Collectors.toList());
        this.email = user.getEmail();
        this.id = user.getId();
        this.role = user.getRole();
        this.firsName = user.getFirstName();
        this.lastName = user.getLastName();
        this.savedItems = user.getSavedProducts().stream().map(ProductDto::new).collect(Collectors.toList());
        this.gender = user.getGender();
        this.phone = user.getPhoneNumber();
    }
}
