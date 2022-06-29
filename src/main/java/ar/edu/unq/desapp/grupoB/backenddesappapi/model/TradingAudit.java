package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class TradingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradingAuditId;

    @Column(columnDefinition = "TIMESTAMP", name = "hour_trading_audit",nullable = false)
    private LocalDateTime hour;

    @Column(name = "cryptoId",nullable = false)
    private String cryptocurrency;

    @Column(name = "cryptoAmount",nullable = false)
    private Double cryptoAmount;

    @Column(name = "cotization",nullable = false)
    private Double cotization;

    @Column(name = "user_audit",nullable = false)
    private String user; //nombre-apellido

    @Column(name = "operationAmount",nullable = false)
    private Double operationAmount;

    @Column(name = "shippingAddress",nullable = false)
    private String shippingAddress;

    @Column(name = "userOperations",nullable = false)
    private Integer userOperations;

    @Column(name ="userReputation", nullable = false)
    private Double userReputation;

    public TradingAudit(LocalDateTime hour,String cryptocurrency, Double cryptoAmount, Double cotization, String user, Double operationAmount, String shippingAddress, Integer userOperations, Double userReputation) {
        this.hour = hour;
        this.cryptocurrency = cryptocurrency;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.user = user;
        this.operationAmount = operationAmount;
        this.shippingAddress = shippingAddress;
        this.userOperations = userOperations;
        this.userReputation = userReputation;
    }

    public TradingAudit() {}

    public Integer getTradingAuditId() {
        return tradingAuditId;
    }

    public void setTradingAuditId(Integer tradingAuditId) {
        this.tradingAuditId = tradingAuditId;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String crypto) {
        this.cryptocurrency = crypto;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Double operationAmount) {
        this.operationAmount = operationAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getUserOperations() {
        return userOperations;
    }

    public void setUserOperations(Integer userOperations) {
        this.userOperations = userOperations;
    }

    public Double getUserReputation() {
        return userReputation;
    }

    public void setUserReputation(Double userReputation) {
        this.userReputation = userReputation;
    }


}
