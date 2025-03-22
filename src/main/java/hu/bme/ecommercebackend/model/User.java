package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    private String id;

    private Role role;
    @Setter
    private String email;
    @Setter
    private String firstName;
    @Setter
    private String lastName;

    @ManyToMany
    @Setter
    private Set<Product> savedProducts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartElement> cart = new ArrayList<>();

    @Embedded
    @Setter
    private Address address;

    public User(UserCreateDto user) {
        this.address = user.getAddress();
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.savedProducts = new HashSet<>();
        this.cart = new ArrayList<>();
    }

    public User(User user) {
        this.address = user.getAddress();
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.savedProducts = user.getSavedProducts().stream().map(Product::new).collect(Collectors.toSet());
        this.cart = user.getCart().stream().map(CartElement::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && role == user.role && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(new HashSet<>(savedProducts), new HashSet<>(user.savedProducts)) && Objects.equals(cart, user.cart) && Objects.equals(address, user.address);
    }

}
