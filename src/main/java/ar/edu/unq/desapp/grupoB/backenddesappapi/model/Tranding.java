package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;

@Entity
@Table(name="trading")
public class Tranding {

    public Tranding(){

    }

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

    public void setOperationAmount(Double operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Tranding(Integer idOperation, Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount, Integer userId, Integer operationType) {
        this.idOperation = idOperation;
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.operationAmount = operationAmount;
        this.userId = userId;
        this.operationType = operationType;
    }

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

    @Column(name = "operationType", nullable = false, length = 1)
    private Integer operationType;
}
