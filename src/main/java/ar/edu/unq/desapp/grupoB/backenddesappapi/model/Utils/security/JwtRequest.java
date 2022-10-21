package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotEmpty(message = "The username must not be empty")
    private String username;

    @NotEmpty(message = "The password must not be empty")
    private String password;

    public JwtRequest(){}

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"username\": \"" + this.username + "\", \"password\": \"" + this.password + "\"}";
    }

}
