package sebcel.inwentarz.dao.definition;

import java.util.List;

import sebcel.inwentarz.dao.dto.BookCreationData;
import sebcel.inwentarz.dao.dto.BookDetails;
import sebcel.inwentarz.dao.dto.BookEditData;
import sebcel.inwentarz.dao.dto.BookListElement;
import sebcel.inwentarz.dao.dto.BookPrintData;
import sebcel.inwentarz.dao.dto.BookRegisterListElement;

public interface IBookDao {

    public List<BookListElement> getBookList();

    public String getAuthorNamesForBook(int bookId);

    public BookEditData getBookEditData(int bookId);
    
    public BookDetails getBookDetails(int bookId);

    public int createBook(BookCreationData bookCreationData);

    public void updateBook(BookEditData modifiedEditData);

    public List<BookRegisterListElement> getBookRegisterList(int bookId);

    public BookPrintData getBookPrintData(int id);

    public void lendBook(int bookId);

    public void returnBook(int bookId);

    public void deleteBook(int bookId);

    public void hardDeleteBook(int id);
    
    public void verifyBookExistence(int id);

    public boolean getBookVerification(int id);
}