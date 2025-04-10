package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.config.EmailConstants;
import hu.bme.ecommercebackend.model.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailConstants emailConstants;

    public EmailService(JavaMailSender javaMailSender) {

        this.javaMailSender = javaMailSender;
        this.emailConstants = new EmailConstants();
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
            orderItems += "- " + order.getItems().get(i).getProduct().getName() + " - " + order.getItems().get(i).getProduct().getCount() + "db<br>";
        }
        return "Dear " + name + "!<br>Your order with number " + order.getId() + " was created in the webshops service.<br>Your order contains the fallowing items:<br>" + orderItems + "<br><br>Best regards,<br>E-commerce Webshop Company";
    }

    public String orderStatusChangedMessage(String name, Order order) {
        return "Dear " + name + "!<br>Your order's status with number " + order.getId() + " was updated to <strong>" + order.getStatus().getDisplayName() + "</strong>.<br><br>Best regards,<br>E-commerce Webshop Company";
    }

    public String verifyEmailMessage(String name, String token) {
        return emailConstants.getVerificationMessage(name,token);
    }
}
