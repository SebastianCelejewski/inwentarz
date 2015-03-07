package sebcel.inwentarz.dao.dto;

import java.util.Date;

public class BookRegisterListElement {

    private int id;
    private Date dataOperacji;
    private String rodzajOperacji;
    private String szczegoly;

    public BookRegisterListElement(int id, Date dataOperacji, String rodzajOperacji, String szczegoly) {
	this.id = id;
	this.dataOperacji = dataOperacji;
	this.rodzajOperacji = rodzajOperacji;
	this.szczegoly = szczegoly;
    }

    public int getId() {
	return id;
    }

    public Date getDataOperacji() {
	return dataOperacji;
    }

    public String getRodzajOperacji() {
	return rodzajOperacji;
    }

    public String getSzczegoly() {
	return szczegoly;
    }
}