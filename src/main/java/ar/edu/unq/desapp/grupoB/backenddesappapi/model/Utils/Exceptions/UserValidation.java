package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions;

public class UserValidation extends RuntimeException{
    private Integer errorCode;

    public UserValidation(Integer code, String message){
        super(message);
        this.errorCode = code;
    }

    public Integer getErrorCode(){ return errorCode;}
}
