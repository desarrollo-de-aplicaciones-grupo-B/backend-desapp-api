package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

public abstract class Reputation {

        public static Integer penalizationPoints() {
            return 20;
        }

        public static Integer addPoints(Long timeDifference) {
            if(timeDifference<=30){
                return 10;
            } else {
                return 5;
            }
        }

        public static Double calculate(Integer processedTransactions, Integer points) {
            return (double)points/(double)processedTransactions;
        }
}
