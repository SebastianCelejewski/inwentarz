package sebcel.inwentarz.dao.dto;

public enum ScontrumStatus {

    ROZPOCZETE("Rozpocz�te"), ZAKONCZONE("Zako�czone");

    private String name;

    ScontrumStatus(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }
}
