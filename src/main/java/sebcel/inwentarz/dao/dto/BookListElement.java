package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class BookListElement {

    private int id;
    private String autorzy;
    private String tytul;
    private BookStatus status;
    private Date dataWlaczenia;
    private String zrodlo;
    private Double cena;
    private Double wartosc;
    private Boolean verified;

    public BookListElement(int id, String autorzy, String tytul, BookStatus status, Date dataWlaczenia, String zrodlo, Double cena, Double wartosc, Boolean verified) {
        this.id = id;
        this.autorzy = autorzy;
        this.tytul = tytul;
        this.status = status;
        this.dataWlaczenia = dataWlaczenia;
        this.zrodlo = zrodlo;
        this.cena = cena;
        this.wartosc = wartosc;
        this.verified = verified;
    }

    public int getId() {
        return id;
    }

    public String getAutorzy() {
        return autorzy;
    }

    public String getTytul() {
        return tytul;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Date getDataWlaczenia() {
        return dataWlaczenia;
    }

    public String getZrodlo() {
        return zrodlo;
    }

    public Double getCena() {
        return cena;
    }

    public Double getWartosc() {
        return wartosc;
    }

    public void setAutorzy(String autorzy) {
        this.autorzy = autorzy;
    }

    public Boolean isVerified() {
        return verified;
    }

    public String toString() {
        return Integer.toString(id) + " " + autorzy + " " + tytul;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}