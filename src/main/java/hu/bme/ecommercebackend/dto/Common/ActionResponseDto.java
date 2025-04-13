package hu.bme.ecommercebackend.dto.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ActionResponseDto {
    private Boolean success;
    private String message;
}
