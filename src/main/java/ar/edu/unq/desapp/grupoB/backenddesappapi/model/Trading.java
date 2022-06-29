package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="trading")
public class Trading {


    public Integer getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Integer idOperation) {
        this.idOperation = idOperation;
    }

    public Integer getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Integer cryptoId) {
        this.cryptoId = cryptoId;
    }

    public Double getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(Double cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public Double getCotization() {
        return cotization;
    }

    public void setCotization(Double cotization) {
        this.cotization = cotization;
    }

    public Double getOperationAmount() {
        return operationAmount;
    }

    public LocalDateTime getCreationDate() { return creationDate; }

    public void setOperationAmount(Double operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBuyerId() { return buyerId; }

    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }

    public boolean isTransferConfirmed() {
        return transferConfirmed;
    }

    public Trading(Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount, Integer sellerId) {
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.operationAmount = operationAmount;
        this.sellerId = sellerId;
        this.creationDate = LocalDateTime.now();
        this.transferConfirmed = false;
        this.buyerId = null;
    }

    public Trading(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOperation;

    @Column(name = "cryptoId", nullable = false)
    private Integer cryptoId;

    @Column(name = "cryptoAmount", nullable = false)
    private Double cryptoAmount;

    @Column(name = "cotization", nullable = false)
    private Double cotization;

    @Column(name = "operationAmount", nullable = false)
    private Double operationAmount;

    @Column(name = "sellerId")
    private Integer sellerId;


    @Column(name = "buyerId")
    private Integer buyerId;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name = "transferConfirmed")
    private boolean transferConfirmed;

    public void confirmTransfer(Integer buyerId) {
        if(this.buyerId.equals(buyerId)) {
            this.transferConfirmed = true;
        } //TODO throw error?
    }
}
