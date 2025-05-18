package hu.bme.ecommercebackend.service;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;
    @Value("${keycloak.admin-client-id}")
    private String clientId;

    @Value("${keycloak.client-username}")
    private String username;

    @Value("${keycloak.client-password}")
    private String password;

    @Value("${keycloak.admin-realm}")
    private String realm;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        keycloak = Keycloak.getInstance(
                keycloakUrl,
                realm,
                username,
                password,
                clientId
        );
    }

    @Transactional
    public String registerUser(String username, String firstName, String lastName, String email, String password) {
        UserRepresentation user = new UserRepresentation();

        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);
        user.setEmailVerified(false);

        List<CredentialRepresentation> credentials = new ArrayList<>();

        CredentialRepresentation cred = new CredentialRepresentation();

        cred.setTemporary(false);
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);

        credentials.add(cred);
        user.setCredentials(credentials);
        Response response = keycloak.realm("ecommerce").users().create(user);
        String userId = response.getHeaders().get("Location").get(0).toString().split("/users/")[1];
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return keycloak.realm("ecommerce").users().get(userId).toRepresentation().getId();
        } else {
            System.out.println(response.getStatus() + " " + response.readEntity(String.class));
            return null;
        }
    }
    @Transactional
    public void setNewPassword(String password, String userId) {
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);
        cred.setTemporary(false);

        keycloak.realm("ecommerce").users().get(userId).resetPassword(cred);
    }

    @Transactional
    public void validateEmail(String userId) {
        UserRepresentation userRepresentation = keycloak.realm("ecommerce").users().get(userId).toRepresentation();
        userRepresentation.setEmailVerified(true);
        keycloak.realm("ecommerce").users().get(userId).update(userRepresentation);
    }
}
