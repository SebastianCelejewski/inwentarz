package sebcel.inwentarz.gui.booklist;

public class BookListFilterModel {

    public enum ScontrumFilterValue {
	NONE, VERIFIED, NOT_VERIFIED
    };

    private Integer bookId;

    private String authorNameExpression;

    private String bookTitleExpression;

    private ScontrumFilterValue scontrumFilter;

    public BookListFilterModel(Integer bookId, String authorNameExpression, String bookTitleExpression, ScontrumFilterValue scontrumFilter) {
	this.bookId = bookId;
	this.authorNameExpression = authorNameExpression;
	this.bookTitleExpression = bookTitleExpression;
	this.scontrumFilter = scontrumFilter;
    }

    public Integer getBookId() {
	return bookId;
    }

    public String getAuthorNameExpression() {
	return authorNameExpression;
    }

    public String getBookTitleExpression() {
	return bookTitleExpression;
    }

    public ScontrumFilterValue getScontrumFilter() {
	return scontrumFilter;
    }
}