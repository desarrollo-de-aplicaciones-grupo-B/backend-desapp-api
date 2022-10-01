package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Reputation;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {
    User pepe;

    User goodUser;

    MockedStatic<Reputation> reputation;
    private User anyUser() throws Exception {
        return new User(43, "asdsaf",
                    "asda",
                    "any@mail.com",
                    "lakosdksa",
                    "13134",
                    "95431685",
                    "12345678910");
    }

   private User goodReputationUser() throws Exception {
       return new User(87, "good",
               "user",
               "good@mail.com",
               "qweq",
               "42kjh8df",
               "87578422",
               "73465821465");
   }

    private Long lessThanHalfHour() {
        return 25L;
    }

    private Long moreThanHalfHour(){
        return 45L;
    }


    @BeforeEach
    void setUp() throws Exception {
        pepe = anyUser();
        goodUser = goodReputationUser();
        reputation = mockStatic(Reputation.class);
        reputation.when(() -> Reputation.calculate(1,15)).thenCallRealMethod();
        reputation.when(()-> Reputation.pointsToAdd(lessThanHalfHour())).thenCallRealMethod();
        reputation.when(()-> Reputation.pointsToAdd(moreThanHalfHour())).thenCallRealMethod();
        reputation.when(Reputation::penalizationPoints).thenCallRealMethod();
    }

    @Test
    void userAddsSuccessfulOperation(){
        goodUser.successfulTrading(lessThanHalfHour());
        reputation.verify(
                () -> Reputation.pointsToAdd(lessThanHalfHour())
        );
        assertTrue(goodUser.getSuccessfulOperations()>0);
    }

    @Test
    void newUserHas0ReputationPoints(){
        assertEquals(0.0, pepe.getReputation());
    }
    @Test
    void whenUserAddsSuccesfulTradingInLessThanHalfHourGets10Points(){
        goodUser.successfulTrading(lessThanHalfHour());
        goodUser.getReputation();
        reputation.verify(
                ()-> Reputation.calculate(1,10)
        );
    }
    @Test
    void whenUserAddsSuccesfulTradingInLMoreThanHalfHourGets5Points(){
        goodUser.successfulTrading(moreThanHalfHour());
        goodUser.getReputation();
        reputation.verify(
                ()-> Reputation.calculate(1,5)
        );
    }

    @Test
    void penalizedUserLose20Points(){
        pepe.successfulTrading(lessThanHalfHour());
        pepe.successfulTrading(lessThanHalfHour());
        pepe.penalize();
        reputation.verify(
                Reputation::penalizationPoints
        );
        assertEquals(0.0, pepe.getReputation());
    }
    @Test
    void userCantLoseMorePointsThanCurrentPoints(){
        pepe.penalize();
        reputation.verify(
                Reputation::penalizationPoints
        );
        assertEquals(0.0, goodUser.getReputation());
    }
}
