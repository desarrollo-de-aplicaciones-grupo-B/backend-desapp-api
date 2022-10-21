package ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO;

import javax.validation.constraints.*;


public class RegisterDTO {

    @NotEmpty(message = "The name must not be empty")
    private String name;

    @NotEmpty(message = "The lastname must not be empty")
    private String lastname;

    @NotEmpty(message = "The email must not be empty")
    private String email;

    @NotEmpty(message = "The address must not be empty")
    private String address;

    @NotEmpty(message = "The password must not be empty")
    private String password;

    @NotEmpty(message = "The cvu must not be empty")
    private String cvu;

    @NotEmpty(message = "The userWallet must not be empty")
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
