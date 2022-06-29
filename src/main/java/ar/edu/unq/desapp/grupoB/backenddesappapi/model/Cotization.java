package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "cotizations")
public class Cotization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_cotization", nullable = false)
    private LocalDateTime dateCotization;

    @Column(name = "price_cotization", nullable = false)
    private Double priceCotization;

    @Column(name = "crypto_nomenclature", nullable = false)
    private String nomenclature;

    public Cotization(Integer id, LocalDateTime dateCotization, Double priceCotization, String nomenclature) {
        this.id = id;
        this.dateCotization = dateCotization;
        this.priceCotization = priceCotization;
        this.nomenclature = nomenclature;
    }

    public Cotization(){}

    public Cotization(LocalDateTime now, double parseDouble, String symbol) throws ParseException {
        this.dateCotization = now;
        this.priceCotization = parseDouble;
        this.nomenclature = symbol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateCotization() {
        return dateCotization;
    }

    public void setDateCotization(LocalDateTime dateCotization) {
        this.dateCotization = dateCotization;
    }

    public Double getPriceCotization() {
        return priceCotization;
    }

    public void setPriceCotization(Double priceCotization) {
        this.priceCotization = priceCotization;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }
}
