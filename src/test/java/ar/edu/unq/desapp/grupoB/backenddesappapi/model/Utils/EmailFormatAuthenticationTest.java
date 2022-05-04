package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailFormatAuthenticationTest {

    @Test
    void testEmailCorrectFormat() {
        String email = "user@gmail.com";
        assertTrue(EmailFormatAuthentication.patternMatches(email));
    }
}