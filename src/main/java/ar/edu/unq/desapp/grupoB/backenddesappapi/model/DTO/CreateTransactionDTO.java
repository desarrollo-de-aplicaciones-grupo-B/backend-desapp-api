package ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO;

public class CreateTransactionDTO {

    private Integer cryptoId;
    private Double cryptoAmount;
    private Double cotization ;
    private Double operationAmount;

    public CreateTransactionDTO(Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount) {
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cotization = cotization;
        this.operationAmount = operationAmount;
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

    @Override
    public String toString(){
        return "{\"cryptoId\": \"" + this.cryptoId + "\", \"cryptoAmount\": \"" + this.cryptoAmount + "\", \"cotization\": \"" + this.cotization + "\", \"operationAmount\": \"" + this.operationAmount + "\"}";
    }

}
