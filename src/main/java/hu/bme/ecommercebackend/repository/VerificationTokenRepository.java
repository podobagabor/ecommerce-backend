package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.VerificationToken;
import hu.bme.ecommercebackend.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken getVerificationTokenByUserAndType(User user, TokenType type);
}
