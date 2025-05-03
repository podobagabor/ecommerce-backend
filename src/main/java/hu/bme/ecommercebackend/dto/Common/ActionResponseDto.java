package hu.bme.ecommercebackend.dto.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionResponseDto {
    private Boolean success;
    private String message;
}
