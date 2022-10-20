package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import io.swagger.models.auth.In;

public enum DefinedError {

    ERROR_EMAIL_IS_IN_USE {
        @Override
        public Integer getErrorCode(){return 101;}

        @Override
        public String getErrorMessage(){return "The email is already in use, please choose another";}
    },
    ERROR_NAME_IS_IN_USE {
        @Override
        public Integer getErrorCode(){ return 102;}

        @Override
        public String getErrorMessage(){return "The username is already in use, please choose another";}
    },
    USER_DISABLED {
        @Override
        public Integer getErrorCode(){ return 103;}

        @Override
        public String getErrorMessage() {return "The user is disable";}
    },
    INVALID_CREDENTIALS {
        @Override
        public Integer getErrorCode(){ return 104;}

        @Override
        public String getErrorMessage() {return "Invalid Credentials";}
    },USER_UNAUTHORIZED {
        @Override
        public Integer getErrorCode(){ return 105;}

        @Override
        public String getErrorMessage() {return "User not authorized to cancel the trading";}
    };

    public abstract Integer getErrorCode();

    public abstract String getErrorMessage();
}
