package sebcel.inwentarz.dao.definition;

import java.util.List;

import sebcel.inwentarz.dao.dto.AuthorCreationData;
import sebcel.inwentarz.dao.dto.AuthorEditData;
import sebcel.inwentarz.dao.dto.AuthorListElement;
import sebcel.inwentarz.dao.utils.ListElement;

public interface IAuthorDao {

    public List<ListElement> getAuthorList(String string);

    public List<AuthorListElement> getAuthorList();

    public void deleteAuthor(int id);

    public void updateAuthor(AuthorEditData modifiedEditData);

    public int createAuthor(AuthorCreationData authorCreationData);

    public AuthorEditData getAuthorEditData(int id);
}