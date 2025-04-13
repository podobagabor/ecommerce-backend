package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.dto.User.UserCreateDto;
import hu.bme.ecommercebackend.model.enums.Gender;
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

    @Enumerated(EnumType.STRING)
    private Role role;
    @Setter
    @Column(unique = true)
    private String email;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String phoneNumber;

    @ManyToMany
    @Setter
    private Set<Product> savedProducts;

    private Gender gender;

    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartElement> cart = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

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
        this.orders = new ArrayList<>();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhone();
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
        this.orders = user.getOrders().stream().map(Order::new).collect(Collectors.toList());
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && role == user.role && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(savedProducts, user.savedProducts) && gender == user.gender && Objects.equals(cart, user.cart) && Objects.equals(orders, user.orders) && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, email, firstName, lastName, phoneNumber, savedProducts, gender, cart, orders, address);
    }
}
