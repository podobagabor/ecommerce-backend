package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    private String id;

    private Role role;
    @Setter
    private String email;
    @Setter
    private String firsName;
    @Setter
    private String lastName;

    @ManyToMany
    @Setter
    private Set<Product> savedProducts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartElement> cart;

    @Embedded
    @Setter
    private Address address;

    public User(UserCreateDto user) {
        this.address = user.getAddress();
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firsName = user.getFirstName();
        this.lastName = user.getLastName();
        this.savedProducts = new HashSet<>();
        this.cart = new ArrayList<>();
    }

}
