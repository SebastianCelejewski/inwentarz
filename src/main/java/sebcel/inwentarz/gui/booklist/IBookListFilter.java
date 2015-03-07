package sebcel.inwentarz.gui.booklist;

import sebcel.inwentarz.gui.list.IFilterListener;

public interface IBookListFilter {
    
    public void addFilterListener(IFilterListener filterListener);
    
    public BookListFilterModel getFilter();
}