package sebcel.inwentarz.gui.components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

import sebcel.inwentarz.dao.utils.ListElement;
import sebcel.inwentarz.gui.authors.IAuthorPicker;

public class AuthorsBox extends JComponent {

    private static final long serialVersionUID = 1L;

    private AuthorsBoxListModel listModel = new AuthorsBoxListModel();

    private JList authorList = new JList();
    private JButton addButton = new JButton("+");
    private JButton removeButton = new JButton("X");
    private JPanel buttonPanel = new JPanel();

    private IAuthorPicker authorPicker;
    private Font f = new Font("Times", Font.PLAIN, 10);

    public AuthorsBox(IAuthorPicker authorPicker) {
        this.authorPicker = authorPicker;

        addButton.setFont(f);
        addButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setToolTipText("Dodaj autora do listy autorów książki");

        removeButton.setFont(f);
        removeButton.setMargin(new Insets(0, 0, 0, 0));
        removeButton.setToolTipText("Usuń zaznaczonego autora z listy autorów książki");

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEtchedBorder());

        authorList.setModel(listModel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));

        this.add(authorList, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAuthor();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAuthor();
            }
        });
    }

    public void setAuthors(Set<ListElement> authors) {
        listModel.setData(authors);
    }

    public Set<ListElement> getAuthors() {
        return listModel.getData();
    }

    private void addAuthor() {
        List<ListElement> newAuthors = authorPicker.selectAuthors();
        for (ListElement newAuthor : newAuthors) {
            listModel.add(newAuthor);
        }
    }

    private void removeAuthor() {
        Object[] selectedAuthors = authorList.getSelectedValues();
        if (selectedAuthors != null) {
            for (Object selectedAuthor : selectedAuthors) {
                listModel.remove((ListElement) selectedAuthor);
            }
        }
    }
}