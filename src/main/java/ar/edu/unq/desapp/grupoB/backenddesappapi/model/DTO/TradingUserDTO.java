package ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO;

import java.time.LocalDateTime;

public class TradingUserDTO {
    private LocalDateTime creationDate;
    private String cryptocurrency;
    private Double cryptoAmount;
    private Double cotization;
    private Double operationAmount;
    private String userName;
    private Integer successfulOperations;
    private Double reputation;


    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public void setCryptoAmount(Double cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public void setCotization(Double cotization) {
        this.cotization = cotization;
    }

    public void setOperationAmount(Double operationAmount) {
        this.operationAmount = operationAmount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSuccessfulOperations(Integer operations){
        this.successfulOperations = operations;
    }

    public void setReputation(Double reputation) {
        this.reputation = reputation;
    }
}
