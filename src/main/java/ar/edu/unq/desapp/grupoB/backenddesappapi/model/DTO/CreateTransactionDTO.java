package ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateTransactionDTO {


    @NotNull(message = "The cryptoId is required")
    private Integer cryptoId;

    @NotNull(message = "The cryptoAmount is required")
    private Double cryptoAmount;

    @NotNull(message = "The cotization is required")
    private Double cotization ;

    @NotNull(message = "The operationAmount is required")
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
