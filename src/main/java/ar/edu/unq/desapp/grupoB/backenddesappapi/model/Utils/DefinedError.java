package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

public enum DefinedError {

    ERROR_EMAIL_IS_IN_USE {
        @Override
        public Integer getErrorCode(){return 101;}

        @Override
        public String getErrorMessage(){return "The email is already in use, please choose another";}
    };

    public abstract Integer getErrorCode();

    public abstract String getErrorMessage();
}
