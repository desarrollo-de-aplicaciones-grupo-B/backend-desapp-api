package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class UserTest {
    User pepe;

    User goodUser;

    MockedStatic<Reputation> reputation;
    private User anyUser() {
        try {
            return new User(43, "asdsaf",
                    "asda",
                    "any@mail.com",
                    "lakosdksa",
                    "13134",
                    "95431685",
                    "12345678910");
        } catch (Exception e){
            return new User();
        }
    }

   private User goodReputationUser() {
       try {
           return new User(87, "good",
                   "user",
                   "good@mail.com",
                   "qweq",
                   "42kjh8df",
                   "87578422",
                   "73465821465");
       } catch (Exception e) {
           return new User();
       }
   }

    private Long lessThanHalfHour() {
        return 25L;
    }

    private Long moreThanHalfHour(){
        return 45L;
    }
    private Integer nPoints() {
        return 15;
    }
    private Integer nTransactions() {
        return 1;
    }

    @BeforeEach
     void setUp() {
        pepe = anyUser();
        goodUser = goodReputationUser();
        reputation = mockStatic(Reputation.class);
        reputation.when(() -> Reputation.calculate(nTransactions(),nPoints())).thenCallRealMethod();
        reputation.when(()-> Reputation.pointsToAdd(lessThanHalfHour())).thenCallRealMethod();
        reputation.when(()-> Reputation.pointsToAdd(moreThanHalfHour())).thenCallRealMethod();
        reputation.when(Reputation::penalizationPoints).thenCallRealMethod();
    }




    @AfterEach
    void tearDown(){
        reputation.close();
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
    void whenUserAddsSuccesfulTradingInLessThanHalfHourGets10PointsAnd1SuccessfulOperation(){
        goodUser.successfulTrading(lessThanHalfHour());
        goodUser.getReputation();
        reputation.verify(
                ()-> Reputation.calculate(1,10)
        );
    }
    @Test
    void whenUserAddsSuccesfulTradingInLMoreThanHalfHourGets5PointsAnd1SuccessfulOperation(){
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
