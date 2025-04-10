package hu.bme.ecommercebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    private String token;

    @OneToOne
    @Getter
    private User user;

    private LocalDateTime expiryDate;
}
