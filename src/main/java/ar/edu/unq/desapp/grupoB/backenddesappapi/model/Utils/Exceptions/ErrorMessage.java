package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    @JsonAlias({"codigo"})
    private Integer errorCode;

    @JsonAlias({"error"})
    private String errorMessage;

    public ErrorMessage(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public ErrorMessage(){}

    public ErrorMessage(String message) {
        this.errorMessage = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}