package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${frontend.url}")
    private String frontendUrl;

    public EmailService(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom("test@test.com");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error happened during mail sending.", e);
        }
    }

    public String orderCreatedMessage(String name, Order order) {
        String orderItems = "";
        for (int i = 0; i < order.getItems().size(); i++) {
            orderItems += "- " + order.getItems().get(i).getProduct().getName() + " - " + order.getItems().get(i).getQuantity() + "db<br>";
        }
        return "Dear " + name + "!<br>Your order with number " + order.getId() + " was created in the webshops service.<br>Your order contains the fallowing items:<br>" + orderItems + "<br><br>Best regards,<br>E-commerce Webshop Company";
    }

    public String orderStatusChangedMessage(String name, Order order) {
        return "Dear " + name + "!<br>Your order's status with number " + order.getId() + " was updated to <strong>" + order.getStatus().getDisplayName() + "</strong>.<br><br>Best regards,<br>E-commerce Webshop Company";
    }

    public String getVerificationMessage(String name, String token) {
        return "Dear " + name + "<br>You successfully registered to our webshop! Just one step remained:<br><br><strong>You have to verify your email!</string><br><br>Verify your email by clicking on this link: " + frontendUrl + "?emailVerification=true&userToken=" + token + "<br><br>Best regard,<br>Team of E-commerce webshop";
    }

    public String getPasswordResetMessage(String name, String token) {
        return "Dear " + name + "<br>You requested a new password for the E-commerce webshop.<br> To change you password click this url: " + frontendUrl + "/" + token + " ." + "<br><br>Best regard,<br>Team of E-commerce webshop";
    }

    public String getVerificationMessageAgain(String name, String token) {
        return "Dear " + name + "<br>You successfully registered to our webshop! We have sent a verification code previously, but because it was expired this is the new one. Just one step remained:<br><br><strong>You have to verify your email!</string><br><br>Verify your email by clicking on this link: " + frontendUrl + "/" + token + "<br><br>Best regard,<br>Team of E-commerce webshop";
    }

    public String getPasswordResetMessageAgain(String name, String token) {
        return "Dear " + name + "<br>You requested a new password for the E-commerce webshop.<br> We have sent an url previously, but because it was expired this is the new one. To change you password click this url: " + frontendUrl + "/" + token + " ." + "<br><br>Best regard,<br>Team of E-commerce webshop";
    }

    public String getNewOrderMessageForAdmin(Order order) {
        String orderItems = "";
        for (int i = 0; i < order.getItems().size(); i++) {
            orderItems += "- " + order.getItems().get(i).getProduct().getName() + " - " + order.getItems().get(i).getQuantity() + "db<br>";
        }
        return "Dear Admin," + "<br>The webshop has a new order.<br><br> Ordered products: <br><br>" + orderItems + "For additional info, check the admin surface.";
    }

    public String getShortageOfStochForAdmin(Product product) {
        return "Dear Admin," + "<br>The webshop has a shortage of stock in case of product: <strong>" + product.getId() + " - " + product.getName() + "</strong><br>Elérhető darabszám: " + product.getCount();
    }
}
