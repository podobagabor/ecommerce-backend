package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.VerificationToken;
import hu.bme.ecommercebackend.model.enums.TokenType;
import hu.bme.ecommercebackend.repository.VerificationTokenRepository;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String saveToken(User user, TokenType type) {
        VerificationToken existingToken = verificationTokenRepository.getVerificationTokenByUserAndType(user,type);
        if(existingToken != null) {
            this.verificationTokenRepository.delete(existingToken);
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusDays(1);
        verificationTokenRepository.save(new VerificationToken(token, user,type ,expirationTime));
        return token;
    }

    @Transactional
    public Pair<User,Boolean> handleValidation(String token) {
        try {
            VerificationToken tokenEntity = verificationTokenRepository.findById(token).orElseThrow(() -> new EntityNotFoundException("Unknown token"));
            verificationTokenRepository.delete(tokenEntity);
            Pair<User, Boolean> result = new MutablePair<>(tokenEntity.getUser(),true);
            if(tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
                result.setValue(false);
            }
            return result;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
