package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class BookPrintData {

    private int id;
    private String autorzy;
    private String tytul;
    private Date dataWlaczenia;

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

    public Date getDataWlaczenia() {
        return dataWlaczenia;
    }

    public void setDataWlaczenia(Date dataWlaczenia) {
        this.dataWlaczenia = dataWlaczenia;
    }
}