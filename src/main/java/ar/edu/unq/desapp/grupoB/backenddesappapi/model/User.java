package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;

@Entity
@Table(name="user_table")
public class User {

    public User(Integer id, String name, String lastname, String email, String address, String password, String cvu, String userWallet) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.userWallet = userWallet;
    }

    public User(){  }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_name", nullable = false, length = 30)
    private String name;

    @Column(name="user_last_name", nullable = false, length = 30)
    private String lastname;

    @Id
    @Column(name="user_email",nullable = false,length = 80)
    private String email;

    @Column(name="user_address", nullable = false, length = 30)
    private String address;

    @Column(name="user_password", nullable = false, length = 20)
    private String password;

    @Column(name="user_cvu", nullable = false, length = 22)
    private String cvu;

    @Column(name="user_wallet",nullable = false, length = 8)
    private String userWallet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setAddress(String adress) {
        this.address = adress;
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