package hu.bme.ecommercebackend.config;

import org.springframework.beans.factory.annotation.Value;

public class EmailConstants {

    @Value("${frontend.url}")
    private String frontendUrl;

    private final String greeting = "Dear ";
    private final String verififactionText1 = "You successfully registered to our webshop! Just one step remained:<br><br><strong>You have to verify your email!</string><br><br>Verify your email by clicking on this link: ";
    private final String farewell = "Best regard,<br>Team of E-commerce webshop";
    private final String urlStarting = "http://localhost:8080";
    private final String lineBreak = "<br>";

    public String getVerificationMessage(String name,String token) {
        return this.greeting + name + lineBreak + verififactionText1 + frontendUrl + "/" + token + lineBreak + lineBreak + farewell;
    }

    public String getPasswordResetMessage(String name,String token) {
        return this.greeting + name + lineBreak + verififactionText1 + frontendUrl + "/" + token + lineBreak + lineBreak + farewell;
    }

}
