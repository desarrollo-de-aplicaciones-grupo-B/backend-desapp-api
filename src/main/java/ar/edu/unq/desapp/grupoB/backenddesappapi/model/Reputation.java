package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

public abstract class Reputation {

        public static Integer penalizationPoints() {
            return 20;
        }

        public static Integer pointsToAdd(Long minutes) {
            if(minutes<=30){
                return 10;
            } else {
                return 5;
            }
        }

        public static Double calculate(Integer processedTransactions, Integer points) {
            if(processedTransactions > 0) {
                return (double) points / (double) processedTransactions;
            } else return 0.0;
        }
}
