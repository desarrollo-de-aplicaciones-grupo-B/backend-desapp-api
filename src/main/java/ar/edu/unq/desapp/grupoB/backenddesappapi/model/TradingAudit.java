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

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "cryptoId",nullable = false)
    private Integer cryptoId;

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

    public TradingAudit(LocalDateTime hour, Integer cryptoId, Double cryptoAmount, Double cotization, String user, Double operationAmount, String shippingAddress, Integer actionType) {
        this.hour = hour;
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.user = user;
        this.operationAmount = operationAmount;
        this.shippingAddress = shippingAddress;
        this.actionType = actionType;
    }

    @Column(name = "actionType",nullable = false)
    private Integer actionType;

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

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

}
