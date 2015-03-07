package sebcel.inwentarz.dao.utils;

public class ListElement {

    private int id;
    private String description;

    public ListElement(int id, String description) {
	this.id = id;
	this.description = description;
    }

    public int getId() {
	return id;
    }

    public String getDescription() {
	return description;
    }

    public String toString() {
	return description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final ListElement other = (ListElement) obj;
	if (id != other.id) {
	    return false;
	}
	return true;
    }

}