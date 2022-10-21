package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.OutOfRangeCotizationException;
import io.swagger.models.auth.In;

public enum DefinedError {

    ERROR_EMAIL_IS_IN_USE {
        @Override
        public Integer getErrorCode(){return 409;}

        @Override
        public String getErrorMessage(){return "The email is already in use, please choose another";}
    },
    ERROR_NAME_IS_IN_USE {
        @Override
        public Integer getErrorCode(){ return 409;}

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
        public Integer getErrorCode(){ return 401;}

        @Override
        public String getErrorMessage() {return "Invalid Credentials";}
    },USER_UNAUTHORIZED {
        @Override
        public Integer getErrorCode(){ return 401;}

        @Override
        public String getErrorMessage() {return "User not authorized to cancel the trading";}
    },
    OUT_OF_RANGE_COTIZATION {
        @Override
        public Integer getErrorCode(){ return 409;}

        @Override
        public String getErrorMessage() {return "The price is below or above system cotization by more than 5%, the trading has been cancelled";}
    },
    FORBIDDEN_ACTION {
        @Override
        public Integer getErrorCode() { return 403;}

        @Override
        public String getErrorMessage() { return "Unauthorized to perform this action";}
    },
    NOT_FOUND {
        @Override
        public Integer getErrorCode(){ return 404;}

        @Override
        public String getErrorMessage() {return " not found";}
    };

    public abstract Integer getErrorCode();

    public abstract String getErrorMessage();
}
