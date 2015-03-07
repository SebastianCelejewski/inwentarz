package sebcel.inwentarz.logic.implementation;

import static sebcel.inwentarz.dao.dto.BookStatus.DOSTEPNA;
import static sebcel.inwentarz.dao.dto.BookStatus.USUNIETA;
import static sebcel.inwentarz.dao.dto.BookStatus.WYCOFANA;
import static sebcel.inwentarz.dao.dto.BookStatus.WYPOZYCZONA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class BookLifecycleManager implements ILifecycleManager<BookStatus> {

    private Map<BookStatus, List<BookStatus>> allowedTransitions = new HashMap<BookStatus, List<BookStatus>>();

    public BookLifecycleManager() {
	initialize();
    }

    private void initialize() {
	allowedTransitions.put(DOSTEPNA, Arrays.asList(WYPOZYCZONA, WYCOFANA, USUNIETA));
	allowedTransitions.put(WYPOZYCZONA, Arrays.asList(DOSTEPNA, WYCOFANA, USUNIETA));
	allowedTransitions.put(WYCOFANA, Arrays.asList(USUNIETA));
	allowedTransitions.put(USUNIETA, new ArrayList<BookStatus>());
    }

    @Override
    public List<BookStatus> getAllowedTransitions(BookStatus bookStatus) {
	return allowedTransitions.get(bookStatus);
    }

    @Override
    public List<BookStatus> getAllStatuses() {
	return Arrays.asList(BookStatus.values());
    }
}