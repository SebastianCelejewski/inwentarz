package sebcel.inwentarz.dao.utils;

public enum TypOperacji {

    Utworzenie (1),
    ZmianaDanych (2),
    Wycofanie (3),
    Wypozyczenie (4),
    Zwrot (5),
    Weryfikacja (6),
    Skasowanie (98),
    Import (99);
    
    private int id;
    
    TypOperacji(int id) {
	this.id = id;
    }
    
    public int getId() {
	return id;
    }
    
    public static TypOperacji valueOf(int id) {
	for (TypOperacji typOperacji : TypOperacji.values()) {
	    if (typOperacji.getId() == id) {
		return typOperacji;
	    }
	}
	throw new RuntimeException("Nieznany id typu operacji: "+id);
    }
}