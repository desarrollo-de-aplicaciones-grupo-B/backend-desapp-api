package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import java.util.regex.Pattern;

public class EmailFormatAuthentication {
    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }
}
