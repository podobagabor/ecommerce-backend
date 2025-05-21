package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.User;
import hu.bme.ecommercebackend.model.VerificationToken;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.Role;
import hu.bme.ecommercebackend.model.enums.TokenType;
import hu.bme.ecommercebackend.repository.VerificationTokenRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VerificationTokenServiceTest {
    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private VerificationTokenService verificationTokenService;

    VerificationToken emailToken;
    VerificationToken emailTokenExpired;

    User mockUser1;

    @BeforeEach()
    void init() {
        mockUser1 = new User("asdf", Role.USER, "testEmail1@email.com", "Test1First", "Test1Last", "06709887776", new HashSet<>(Set.of()), Gender.MALE, new ArrayList<>(), new ArrayList<>(), new Address("HU", "Dabas", "Temető utca", "23", "2371"));
        emailToken = new VerificationToken("tesztToken1", mockUser1, TokenType.EMAIL, LocalDateTime.now().plusDays(1));
        emailTokenExpired = new VerificationToken("tesztToken2", mockUser1, TokenType.EMAIL, LocalDateTime.now());

    }

    @Test
    void saveTokenTest() {
        when(verificationTokenRepository.getVerificationTokenByUserAndType(mockUser1, TokenType.EMAIL)).thenReturn(null);
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(emailToken);

        verificationTokenService.saveToken(mockUser1, TokenType.EMAIL);

    }

    @Test
    void handleValidationOkTest() {
        when(verificationTokenRepository.findById(emailToken.getToken())).thenReturn(Optional.ofNullable(emailToken));
        doNothing().when(verificationTokenRepository).delete(emailToken);

        Pair<User, Boolean> currentToken = verificationTokenService.handleValidation(emailToken.getToken(), emailToken.getType());

        assertEquals(true, currentToken.getRight());
        assertEquals(emailToken.getUser(), currentToken.getKey());
    }

    @Test
    void handleValidationDateNotTest() {
        when(verificationTokenRepository.findById(emailTokenExpired.getToken())).thenReturn(Optional.ofNullable(emailTokenExpired));
        doNothing().when(verificationTokenRepository).delete(emailToken);

        Pair<User, Boolean> currentToken = verificationTokenService.handleValidation(emailTokenExpired.getToken(), emailTokenExpired.getType());

        assertEquals(false, currentToken.getRight());
        assertEquals(emailToken.getUser(), currentToken.getKey());
    }


    @Test
    void handleValidationTypeNotOkTestTest() {
        when(verificationTokenRepository.findById(emailTokenExpired.getToken())).thenReturn(Optional.ofNullable(emailTokenExpired));

        Pair<User, Boolean> currentToken = verificationTokenService.handleValidation(emailTokenExpired.getToken(), TokenType.PASSWORD);

        assertEquals(false, currentToken.getRight());
        assertEquals(emailToken.getUser(), currentToken.getKey());
    }
}