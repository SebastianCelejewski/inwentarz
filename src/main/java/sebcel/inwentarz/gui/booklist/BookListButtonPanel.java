package sebcel.inwentarz.gui.booklist;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import sebcel.gui.list.SelectionStatus;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.gui.components.ActionButton;
import sebcel.inwentarz.gui.components.LifecycleButton;
import sebcel.inwentarz.logic.definition.ILifecycleManager;

public class BookListButtonPanel extends JPanel implements ISelectionListener<BookStatus> {

    private static final long serialVersionUID = 1L;

    private ActionButton<BookStatus> newBookButton;
    private ActionButton<BookStatus> viewBookButton;
    private ActionButton<BookStatus> editBookButton;
    private ActionButton<BookStatus> printBookButton;
    private ActionButton<BookStatus> showRegisterButton;

    private LifecycleButton<BookStatus> lendBookButton;
    private LifecycleButton<BookStatus> returnBookButton;
    private LifecycleButton<BookStatus> deleteBookButton;
    private LifecycleButton<BookStatus> hardDeleteBookButton;
    private ActionButton<BookStatus> verifyButton;

    private JPanel leftButtonPanel = new JPanel();
    private JPanel rightButtonPanel = new JPanel();

    private IBookListEventListener listener;

    public BookListButtonPanel(ILifecycleManager<BookStatus> bookLifecycleManager) {
        this.setLayout(new GridLayout());

        leftButtonPanel.setLayout(new GridLayout());
        leftButtonPanel.setBorder(BorderFactory.createTitledBorder("Zarządzanie"));

        rightButtonPanel.setLayout(new GridLayout());
        rightButtonPanel.setBorder(BorderFactory.createTitledBorder("Operacje"));

        newBookButton = new ActionButton<BookStatus>("Dodaj", "Dodanie nowej książki", SelectionRequirement.ANY);
        viewBookButton = new ActionButton<BookStatus>("Szczegóły", "Szczegóły książki", SelectionRequirement.SINGLE);
        editBookButton = new ActionButton<BookStatus>("Edytuj", "Edycja danych książki", SelectionRequirement.SINGLE);
        printBookButton = new ActionButton<BookStatus>("Drukuj", "Wydrukowanie karteczki do ksiki", SelectionRequirement.MULTIPLE);
        showRegisterButton = new ActionButton<BookStatus>("Rejestr", "Przeglądanie rejestru operacji książki", SelectionRequirement.SINGLE);

        lendBookButton = new LifecycleButton<BookStatus>("Wypożycz", "Zmiana statusu książki na 'Wypożyczona'", BookStatus.WYPOZYCZONA, bookLifecycleManager);
        returnBookButton = new LifecycleButton<BookStatus>("Zwróć", "Zmiana statusu książki na 'Dostępna'", BookStatus.DOSTEPNA, bookLifecycleManager);
        deleteBookButton = new LifecycleButton<BookStatus>("Ubytkuj", "Wycofanie książki", BookStatus.WYCOFANA, bookLifecycleManager);
        hardDeleteBookButton = new LifecycleButton<BookStatus>("Kasuj", "Całkowite skasowanie danych książki", BookStatus.USUNIETA, bookLifecycleManager);
        verifyButton = new ActionButton<BookStatus>("Weryfikuj", "Weryfikacja fizycznego istnienia książki", SelectionRequirement.SINGLE);

        leftButtonPanel.add(newBookButton);
        leftButtonPanel.add(viewBookButton);
        leftButtonPanel.add(editBookButton);
        leftButtonPanel.add(printBookButton);
        leftButtonPanel.add(showRegisterButton);

        rightButtonPanel.add(lendBookButton);
        rightButtonPanel.add(returnBookButton);
        rightButtonPanel.add(deleteBookButton);
        rightButtonPanel.add(hardDeleteBookButton);
        rightButtonPanel.add(verifyButton);

        this.add(leftButtonPanel);
        this.add(rightButtonPanel);

        newBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.addNewElement();
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.deleteSelectedElement();
            }
        });

        viewBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.viewSelectedElementDetails();
            }
        });

        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.editSelectedElement();
            }
        });

        printBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.printSelectedElement();
            }
        });

        showRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.viewSelectedElementRegister();
            }
        });

        hardDeleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.hardDeleteBook();
            }
        });

        lendBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.lendBook();
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.returnBook();
            }
        });

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.verifyBook();
            }
        });
    }

    public void setBookListEventListener(IBookListEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void statusChanged(SelectionStatus<BookStatus> selectionStatus) {

        newBookButton.statusChanged(selectionStatus);
        viewBookButton.statusChanged(selectionStatus);
        editBookButton.statusChanged(selectionStatus);
        printBookButton.statusChanged(selectionStatus);
        showRegisterButton.statusChanged(selectionStatus);

        lendBookButton.statusChanged(selectionStatus);
        returnBookButton.statusChanged(selectionStatus);
        deleteBookButton.statusChanged(selectionStatus);
        hardDeleteBookButton.statusChanged(selectionStatus);
        verifyButton.statusChanged(selectionStatus);
    }
}