package sebcel.inwentarz.gui.books;

import java.awt.Image;

import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.gui.booklist.IBookPrinter;
import sebcel.inwentarz.gui.booklist.IBookPrinterFactory;

public class BookPrinterFactory implements IBookPrinterFactory {

    private IBookDao bookDao;
    private Image icon;
    private int lastLocation = 0;

    public BookPrinterFactory(IBookDao bookDao) {
	this.bookDao = bookDao;
    }

    @Override
    public IBookPrinter newBookPrinter() {
	BookPrintFrame printer = new BookPrintFrame(bookDao);
	printer.setIconImage(icon);
	printer.setLocation(++lastLocation * printer.getWidth() / 10, lastLocation * printer.getHeight() / 10);
	return printer;
    }

    public void setIconImage(Image icon) {
	this.icon = icon;
    }

    @Override
    public void newSet() {
	lastLocation = 0;
    }
}