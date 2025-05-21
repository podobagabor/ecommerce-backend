package hu.bme.ecommercebackend.dto.User;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NewPasswordDto {

    @NotNull
    private String token;

    @NotNull
    private String newPassword;
}
