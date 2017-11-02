package sebcel.inwentarz.gui.booklist;

public interface IBookListEventListener {

    public void addNewElement();

    public void viewSelectedElementDetails();

    public void editSelectedElement();

    public void deleteSelectedElement();

    public void viewSelectedElementRegister();

    public void printSelectedElement();

    public void hardDeleteBook();

    public void lendBook();

    public void returnBook();

    public void verifyBook();

}