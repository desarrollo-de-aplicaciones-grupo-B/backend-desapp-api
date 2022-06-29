package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

public class BinanceCotization {

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String symbol;

    public Double price;

    public BinanceCotization(String symbol, Double price){
        this.price = price;
        this.symbol = symbol;
    }
}
