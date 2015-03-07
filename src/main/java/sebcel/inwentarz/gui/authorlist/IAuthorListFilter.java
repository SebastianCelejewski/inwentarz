package sebcel.inwentarz.gui.authorlist;

import sebcel.inwentarz.gui.list.IFilterListener;

public interface IAuthorListFilter {
    
    public void addFilterListener(IFilterListener filterListener);
    
    public AuthorListFilterModel getFilter();
}