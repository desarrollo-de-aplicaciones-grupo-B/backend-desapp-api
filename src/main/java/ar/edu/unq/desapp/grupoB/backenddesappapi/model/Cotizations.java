package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cotizations")
public class Cotizations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_cotization", nullable = false)
    private Date dateCotization;

    @Column(name = "price_cotization", nullable = false)
    private Double priceCotization;

    public Cotizations(Integer id, Date dateCotization, Double priceCotization) {
        this.id = id;
        this.dateCotization = dateCotization;
        this.priceCotization = priceCotization;
    }

    public Cotizations(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCotization() {
        return dateCotization;
    }

    public void setDateCotization(Date dateCotization) {
        this.dateCotization = dateCotization;
    }

    public Double getPriceCotization() {
        return priceCotization;
    }

    public void setPriceCotization(Double priceCotization) {
        this.priceCotization = priceCotization;
    }
}
