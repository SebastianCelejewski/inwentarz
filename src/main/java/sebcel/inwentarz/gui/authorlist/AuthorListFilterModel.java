package sebcel.inwentarz.gui.authorlist;

public class AuthorListFilterModel {

    private String authorNameExpression;

    public AuthorListFilterModel(String authorNameExpression) {
        this.authorNameExpression = authorNameExpression;
    }

    public String getAuthorNameExpression() {
        return authorNameExpression;
    }
}