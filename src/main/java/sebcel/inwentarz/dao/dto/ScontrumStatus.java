package sebcel.inwentarz.dao.dto;

public enum ScontrumStatus {

    ROZPOCZETE("Rozpoczęte"), ZAKONCZONE("Zakończone");

    private String name;

    ScontrumStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
