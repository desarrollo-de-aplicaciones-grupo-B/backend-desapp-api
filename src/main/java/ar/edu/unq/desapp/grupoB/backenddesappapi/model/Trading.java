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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Trading(Integer idOperation, Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount, Integer userId, OperationType operationType) {
        this.idOperation = idOperation;
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.operationAmount = operationAmount;
        this.userId = userId;
        this.operationType = operationType;
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

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operationType", nullable = false, length = 1)
    private OperationType operationType;

    @Column(columnDefinition = "TIMESTAMP", name="creation_date")
    private LocalDateTime creationDate;
}
