package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.WrongEmailFormatException;

import javax.persistence.*;

import java.util.ArrayList;

import static ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.EmailFormatAuthentication.patternMatches;

@Entity
@Table(name="user_table")
public class User {

    public User(Integer id, String name, String lastname, String email, String address, String password, String cvu, String userWallet) throws Exception {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.userWallet = userWallet;
        if(patternMatches(email)){
            this.email = email;
        }
        else{
            throw new WrongEmailFormatException("The email entered does not comply with the format");
        }
    }

    public User(){  }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_name", nullable = false, length = 30)
    private String name;

    @Column(name="user_last_name", nullable = false, length = 30)
    private String lastname;

    @Column(name="user_email",nullable = false,length = 80)
    private String email;

    @Column(name="user_address", nullable = false, length = 30)
    private String address;

    @Column(name="user_password", nullable = false, length = 60)
    private String password;

    @Column(name="user_cvu", nullable = false, length = 22)
    private String cvu;

    @Column(name="user_wallet",nullable = false, length = 8)
    private String userWallet;

    @Column(name="reputation_points")
    private Integer points = 0;

    @Column(name = "successful_operations")
    private Integer successfulOperations = 0;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public <E> User(String name, String password, ArrayList<E> es) {
    }

    public Integer getSuccessfulOperations() {
        return successfulOperations;
    }

    public void setSuccessfulOperations(Integer successfulOperations) {
        this.successfulOperations = successfulOperations;
    }


    public void setReputation(Integer points) {
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getPoints() {
        return points;
    }

    public void addPoints(Integer points){
        this.points += points;
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



    public Double getReputation() {
        if(this.getSuccessfulOperations()>0){
            return Reputation.calculate(this.getSuccessfulOperations(), this.getPoints());
        } else{
            return 0.0;
        }
    }

    public void penalize() {
        this.points = Math.max(this.getPoints() - Reputation.penalizationPoints(), 0);
    }

    public void successfulTrading(Long timeDifference) {
        this.addPoints(Reputation.pointsToAdd(timeDifference));
        this.addSuccessfulOperation();
    }

    private void addSuccessfulOperation() {
        this.successfulOperations++;
    }

}