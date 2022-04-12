package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="user_table")
public class User {

    public User(Integer userId, String name, String lastname, String email, String adress, String password, String cvu, String userWallet) {
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.cvu = cvu;
        this.userWallet = userWallet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="user_name", nullable = false, length = 30)
    private String name;

    @Column(name="user_last_name", nullable = false, length = 30)
    private String lastname;

    @Column(name="user_email",nullable = false,length = 80)
    private String email;

    @Column(name="user_adress", nullable = false, length = 30)
    private String adress;

    @Column(name="user_password", nullable = false, length = 20)
    private String password;

    @Column(name="user_cvu", nullable = false, length = 22)
    private String cvu;

    @Column(name="user_wallet",nullable = false, length = 8)
    private String userWallet;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

}