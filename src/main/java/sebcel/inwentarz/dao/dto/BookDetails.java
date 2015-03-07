package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class BookDetails {

    private int id;
    private String autorzy;
    private String tytul;
    private String status;
    private String zrodlo;
    private String uwagi;
    private Double cena;
    private Double wartosc;
    private Date dataWlaczenia;
    private Date dataOstatniejWeryfikacji;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getAutorzy() {
	return autorzy;
    }

    public void setAutorzy(String autorzy) {
	this.autorzy = autorzy;
    }

    public String getTytul() {
	return tytul;
    }

    public void setTytul(String tytul) {
	this.tytul = tytul;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getZrodlo() {
	return zrodlo;
    }

    public void setZrodlo(String zrodlo) {
	this.zrodlo = zrodlo;
    }

    public String getUwagi() {
	return uwagi;
    }

    public void setUwagi(String uwagi) {
	this.uwagi = uwagi;
    }

    public Double getCena() {
	return cena;
    }

    public void setCena(Double cena) {
	this.cena = cena;
    }

    public Double getWartosc() {
	return wartosc;
    }

    public void setWartosc(Double wartosc) {
	this.wartosc = wartosc;
    }

    public Date getDataWlaczenia() {
	return dataWlaczenia;
    }

    public void setDataWlaczenia(Date dataWlaczenia) {
	this.dataWlaczenia = dataWlaczenia;
    }

    public Date getDataOstatniejWeryfikacji() {
	return dataOstatniejWeryfikacji;
    }

    public void setDataOstatniejWeryfikacji(Date dataOstatniejWeryfikacji) {
	this.dataOstatniejWeryfikacji = dataOstatniejWeryfikacji;
    }
}