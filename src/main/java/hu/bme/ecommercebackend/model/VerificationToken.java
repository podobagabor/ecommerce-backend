package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.model.enums.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    private String token;

    @OneToOne
    private User user;

    private TokenType type;

    private LocalDateTime expiryDate;
}
