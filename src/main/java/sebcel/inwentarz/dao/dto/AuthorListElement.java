package sebcel.inwentarz.dao.dto;

public class AuthorListElement {

    private int id;
    private String imiona;
    private String nazwisko;

    public AuthorListElement(int id, String imiona, String nazwisko) {
	this.id = id;
	this.imiona = imiona;
	this.nazwisko = nazwisko;
    }

    public int getId() {
	return id;
    }

    public String getImiona() {
	return imiona;
    }

    public String getNazwisko() {
	return nazwisko;
    }
}