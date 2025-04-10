package hu.bme.ecommercebackend.dto.User;

import lombok.Getter;

@Getter
public class NewPasswordDto {
    private String token;
    private String newPassword;
}
