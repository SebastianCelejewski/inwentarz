package sebcel.inwentarz.dao.dto;

public enum BookStatus {

    DOSTEPNA(0, "Dost�pna"), WYPOZYCZONA(1, "Wypo�yczona"), WYCOFANA(2, "Wycofana"), USUNIETA(99, "Usuni�ta");

    private int id;
    private String name;

    BookStatus(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public static BookStatus parse(int statusId) {
	BookStatus[] values = BookStatus.values();
	for (int i = 0; i < values.length; i++) {
	    if (values[i].getId() == statusId) {
		return values[i];
	    }
	}
	throw new RuntimeException("Invalid status id: " + statusId);
    }

    @Override
    public String toString() {
	return name;
    }
}
