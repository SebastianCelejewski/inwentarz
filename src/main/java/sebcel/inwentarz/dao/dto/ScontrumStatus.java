package sebcel.inwentarz.dao.dto;

public enum ScontrumStatus {

    ROZPOCZETE("Rozpoczête"), ZAKONCZONE("Zakoñczone");

    private String name;

    ScontrumStatus(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }
}
