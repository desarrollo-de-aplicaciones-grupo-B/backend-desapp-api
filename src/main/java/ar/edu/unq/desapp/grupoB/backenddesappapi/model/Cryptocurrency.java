package ar.edu.unq.desapp.grupoB.backenddesappapi.model;

import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cryptocurrency")
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "crypto_name", nullable = false, length = 30)
    private String cryptoName;

    @Column(name = "nomenclature", nullable = false, length = 10)
    private String nomenclature;

    @Column(name = "date_last_cotization", nullable = false)
    private Date dateLastCotization;

    @Column(name = "cotization", nullable = false)
    private Double cotization;

    public Cryptocurrency(){}

    public Cryptocurrency(Integer id, String cryptoName, String nomenclature, Date dateLastCotization, Double cotization) {
        this.id = id;
        this.cryptoName = cryptoName;
        this.nomenclature = nomenclature;
        this.dateLastCotization = dateLastCotization;
        this.cotization = cotization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Date getDateLastCotization() {
        return dateLastCotization;
    }

    public void setDateLastCotization(Date dateLastCotization) {
        this.dateLastCotization = dateLastCotization;
    }

    public Double getCotization() {
        return cotization;
    }

    public void setCotization(Double cotization) {
        this.cotization = cotization;
    }
}
