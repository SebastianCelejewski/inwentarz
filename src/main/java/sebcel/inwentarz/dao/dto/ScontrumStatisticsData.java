package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class ScontrumStatisticsData {

    private int id;
    private Date dataRozpoczecia;
    private Date dataZakonczenia;
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

    @Override
    public String toString() {
        return "ScontrumStatisticsData [dataRozpoczecia=" + dataRozpoczecia + ", dataZakonczenia=" + dataZakonczenia + ", id=" + id + ", liczbaNiezweryfikowanychKsiazek=" + liczbaNiezweryfikowanychKsiazek + ", liczbaPosiadanychKsiazek="
                + liczbaPosiadanychKsiazek + ", liczbaZweryfikowanychKsiazek=" + liczbaZweryfikowanychKsiazek + "]";
    }

}