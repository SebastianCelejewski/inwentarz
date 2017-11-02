package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class ScontrumListElement {

    private int id;
    private Date dataRozpoczecia;
    private Date dataZakonczenia;
    private ScontrumStatus status;
    private int liczbaZweryfikowanychKsiazek;
    private int liczbaNiezweryfikowanychKsiazek;
    private int liczbaPosiadanychKsiazek;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public void setDataRozpoczecia(Date dataRozpoczecia) {
        this.dataRozpoczecia = dataRozpoczecia;
    }

    public Date getDataZakonczenia() {
        return dataZakonczenia;
    }

    public void setDataZakonczenia(Date dataZakonczenia) {
        this.dataZakonczenia = dataZakonczenia;
    }

    public ScontrumStatus getStatus() {
        return status;
    }

    public void setStatus(ScontrumStatus status) {
        this.status = status;
    }

    public int getLiczbaZweryfikowanychKsiazek() {
        return liczbaZweryfikowanychKsiazek;
    }

    public void setLiczbaZweryfikowanychKsiazek(int liczbaZweryfikowanychKsiazek) {
        this.liczbaZweryfikowanychKsiazek = liczbaZweryfikowanychKsiazek;
    }

    public int getLiczbaNiezweryfikowanychKsiazek() {
        return liczbaNiezweryfikowanychKsiazek;
    }

    public void setLiczbaNiezweryfikowanychKsiazek(int liczbaNiezweryfikowanychKsiazek) {
        this.liczbaNiezweryfikowanychKsiazek = liczbaNiezweryfikowanychKsiazek;
    }

    public int getLiczbaPosiadanychKsiazek() {
        return liczbaPosiadanychKsiazek;
    }

    public void setLiczbaPosiadanychKsiazek(int liczbaPosiadanychKsiazek) {
        this.liczbaPosiadanychKsiazek = liczbaPosiadanychKsiazek;
    }

}