package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.VerificationToken;
import hu.bme.ecommercebackend.repository.UserRepository;
import hu.bme.ecommercebackend.repository.VerificationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(
            VerificationTokenRepository verificationTokenRepository
    ) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public String saveToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusDays(1);
        verificationTokenRepository.save(new VerificationToken(token,user,expirationTime));
        return token;
    }

    public User handleValidation(String token) {
        try {
            VerificationToken tokenEntity = verificationTokenRepository.findById(token).orElseThrow(() -> new EntityNotFoundException("Unknown token"));
            User userEntity = tokenEntity.getUser();
            verificationTokenRepository.delete(tokenEntity);
            return userEntity;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
