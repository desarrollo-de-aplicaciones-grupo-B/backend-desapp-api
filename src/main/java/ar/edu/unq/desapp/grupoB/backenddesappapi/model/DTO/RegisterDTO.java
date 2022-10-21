package ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class RegisterDTO {

    @NotNull(message = "The extras is required.")
    @NotEmpty(message = "name shoulnd]t null")
    //@NotBlank
    private String name;

    @NotNull
    private String lastname;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String userWallet;

    public RegisterDTO(String name, String lastname, String email, String address, String password, String cvu, String userWallet) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.userWallet = userWallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(String userWallet) {
        this.userWallet = userWallet;
    }

    @Override
    public String toString(){
        return "{\"name\": \"" + this.name + "\", \"lastname\": \"" + this.lastname + "\", \"email\": \"" + this.email + "\", \"address\": \"" + this.address + "\", " + "\"password\": \"" + this.password + "\", " +
                "\"cvu\": \"" + this.cvu + "\" , " +
                "\"userWallet\": \"" + this.userWallet + "\"}";
    }

}
