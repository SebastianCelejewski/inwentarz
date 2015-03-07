package sebcel.inwentarz.dao.dto;

import java.util.Date;
import java.util.Set;

import sebcel.inwentarz.dao.utils.ListElement;

public class BookCreationData {

    private Set<ListElement> autorzy;
    private String tytul;
    private Date dataWlaczenia;
    private String zrodloId;
    private Double cena;
    private Double wartosc;
    private String uwagi;

    public Set<ListElement> getAutorzy() {
	return autorzy;
    }

    public void setAutorzy(Set<ListElement> autorzy) {
	this.autorzy = autorzy;
    }

    public String getTytul() {
	return tytul;
    }

    public void setTytul(String tytul) {
	this.tytul = tytul;
    }

    public Date getDataWlaczenia() {
	return dataWlaczenia;
    }

    public void setDataWlaczenia(Date dataWlaczenia) {
	this.dataWlaczenia = dataWlaczenia;
    }

    public String getZrodloId() {
	return zrodloId;
    }

    public void setZrodlo(String zrodloId) {
	this.zrodloId = zrodloId;
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

    public String getUwagi() {
	return uwagi;
    }

    public void setUwagi(String uwagi) {
	this.uwagi = uwagi;
    }
}