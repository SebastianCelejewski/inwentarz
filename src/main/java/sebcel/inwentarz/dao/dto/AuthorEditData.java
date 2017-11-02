package sebcel.inwentarz.dao.dto;

public class AuthorEditData {

    private int id;
    private String imiona;
    private String nazwisko;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImiona() {
        return imiona;
    }

    public void setImiona(String imiona) {
        this.imiona = imiona;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

}