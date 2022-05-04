package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions;

public class WrongEmailFormatException extends Exception{
    public WrongEmailFormatException(String errorMessage) {
        super(errorMessage);
    }
}
