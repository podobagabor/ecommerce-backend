package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {
}
