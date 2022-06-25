package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table
public class TradingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradingAuditId;

    @Column(name = "hour_trading_audit",nullable = false)
    private Time hour;

    @Column(name = "cryptoId",nullable = false)
    private Integer cryptoId;

    @Column(name = "cryptoAmount",nullable = false)
    private Double cryptoAmount;

    @Column(name = "cotization",nullable = false)
    private Double cotization;

    @Column(name = "transactionAmount",nullable = false)
    private Double transactionAmount;

    @Column(name = "seller_audit",nullable = false)
    private String seller; //nombre-apellido

    @Column(name = "buyer_audit",nullable = false)
    private String buyer;

    @Column(name = "operationAmount",nullable = false)
    private String operationAmount; //preguntar que es

    @Column(name = "reputation",nullable = false)
    private Double reputation;

    @Column(name = "shippingAddress",nullable = false)
    private String shippingAddress;

    public TradingAudit(Integer tradingAuditId, Time hour, Integer cryptoId, Double cryptoAmount, Double cotization, Double transactionAmount, String seller, String buyer, String operationAmount, Double reputation, String shippingAddress, Integer actionType) {
        this.tradingAuditId = tradingAuditId;
        this.hour = hour;
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.transactionAmount = transactionAmount;
        this.seller = seller;
        this.buyer = buyer;
        this.operationAmount = operationAmount;
        this.reputation = reputation;
        this.shippingAddress = shippingAddress;
        this.actionType = actionType;
    }

    @Column(name = "actionType",nullable = false)
    private Integer actionType;

    public TradingAudit() {

    }

    public Integer getTradingAuditId() {
        return tradingAuditId;
    }

    public void setTradingAuditId(Integer tradingAuditId) {
        this.tradingAuditId = tradingAuditId;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
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

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(String operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Double getReputation() {
        return reputation;
    }

    public void setReputation(Double reputation) {
        this.reputation = reputation;
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
