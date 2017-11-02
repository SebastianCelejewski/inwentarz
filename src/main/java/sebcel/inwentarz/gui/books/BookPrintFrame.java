package sebcel.inwentarz.gui.books;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.dto.BookPrintData;
import sebcel.inwentarz.gui.booklist.IBookPrinter;

public class BookPrintFrame extends JFrame implements IBookPrinter {

    private static final long serialVersionUID = 1L;

    private IBookDao bookDao;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private JEditorPane text = new JEditorPane();
    private JPanel buttonPanel = new JPanel();
    private JButton printButton = new JButton("Drukuj");
    private JButton closeButton = new JButton("Zamknij");

    public BookPrintFrame(IBookDao bookDao) {
        this.bookDao = bookDao;

        this.setLayout(new BorderLayout());
        text.setBorder(BorderFactory.createEtchedBorder());

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        this.setTitle("Podgl¹d wydruku");
        this.setSize(350, 450);
        this.setLayout(new BorderLayout());
        this.add(text, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print();
                close();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    @Override
    public void printBook(int id) {
        load(id);
        this.setVisible(true);
    }

    private void load(int id) {
        String template = loadTemplate("template.html");
        String html = insertBookData(template, id);
        text.setContentType("text/html");
        text.setText(html);
    }

    private String loadTemplate(String filename) {
        StringBuilder template = new StringBuilder();
        try {
            java.net.URL url = new File(filename).toURI().toURL();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while (br.ready()) {
                template.append(br.readLine() + "\n");
            }
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nie potrafię znaleźć pliku z szablonem wydruku 'template.html'.", "Iwentarz - b³¹d", JOptionPane.ERROR_MESSAGE);
            System.exit(255);
        }
        return template.toString();
    }

    private String insertBookData(String template, int bookId) {
        BookPrintData bookData = bookDao.getBookPrintData(bookId);
        String html = template.replace((CharSequence) "${autor}", (CharSequence) bookData.getAutorzy());
        html = html.replace((CharSequence) "${tytul}", (CharSequence) bookData.getTytul());
        html = html.replace((CharSequence) "${id}", (CharSequence) Integer.toString(bookData.getId()));
        if (bookData.getDataWlaczenia() != null) {
            html = html.replace((CharSequence) "${dataWprowadzenia}", (CharSequence) df.format(bookData.getDataWlaczenia()));
        } else {
            html = html.replace((CharSequence) "${dataWprowadzenia}", (CharSequence) "");
        }

        return html;
    }

    private void close() {
        this.setVisible(false);
    }

    private void print() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = new PageFormat();
        Book book = new Book();
        book.append(new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D g = (Graphics2D) graphics;
                g.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                getTextPane().printAll(g);
                return Printable.PAGE_EXISTS;
            }

        }, pageFormat);

        printerJob.setPageable(book);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public JEditorPane getTextPane() {
        return text;
    }
}