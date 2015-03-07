package sebcel.inwentarz.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import sebcel.inwentarz.dao.definition.IAuthorDao;
import sebcel.inwentarz.dao.definition.IBookDao;
import sebcel.inwentarz.dao.definition.IDictionaryDao;
import sebcel.inwentarz.dao.definition.IScontrumDao;
import sebcel.inwentarz.dao.definition.IStatisticsDao;
import sebcel.inwentarz.dao.dto.BookStatus;
import sebcel.inwentarz.dao.dto.ScontrumStatus;
import sebcel.inwentarz.dao.jdbc.AuthorDao;
import sebcel.inwentarz.dao.jdbc.BookDao;
import sebcel.inwentarz.dao.jdbc.ConnectionFactory;
import sebcel.inwentarz.dao.jdbc.DictionaryDao;
import sebcel.inwentarz.dao.jdbc.ScontrumDao;
import sebcel.inwentarz.dao.jdbc.StatisticsDao;
import sebcel.inwentarz.event.DataChangeManager;
import sebcel.inwentarz.gui.authorlist.AuthorListButtonPanel;
import sebcel.inwentarz.gui.authorlist.AuthorListFilterPanel;
import sebcel.inwentarz.gui.authorlist.AuthorListPanel;
import sebcel.inwentarz.gui.authorlist.AuthorListTablePanel;
import sebcel.inwentarz.gui.authors.AuthorDeleteFrame;
import sebcel.inwentarz.gui.authors.AuthorEditFrame;
import sebcel.inwentarz.gui.authors.AuthorPicker;
import sebcel.inwentarz.gui.booklist.BookListButtonPanel;
import sebcel.inwentarz.gui.booklist.BookListFilterPanel;
import sebcel.inwentarz.gui.booklist.BookListPanel;
import sebcel.inwentarz.gui.booklist.BookListTablePanel;
import sebcel.inwentarz.gui.books.BookDeleteFrame;
import sebcel.inwentarz.gui.books.BookDetailsFrame;
import sebcel.inwentarz.gui.books.BookEditFrame;
import sebcel.inwentarz.gui.books.BookLenderFrame;
import sebcel.inwentarz.gui.books.BookPrinterFactory;
import sebcel.inwentarz.gui.books.BookReturnFrame;
import sebcel.inwentarz.gui.books.BookVerificationFrame;
import sebcel.inwentarz.gui.books.BookViewRegisterFrame;
import sebcel.inwentarz.gui.comparators.ComparatorFactory;
import sebcel.inwentarz.gui.comparators.IComparatorFactory;
import sebcel.inwentarz.gui.components.AuthorsBox;
import sebcel.inwentarz.gui.scontrum.ScontrumPanel;
import sebcel.inwentarz.gui.scontrum.ScontrumViewFrame;
import sebcel.inwentarz.gui.scontrumlist.IScontrumViewer;
import sebcel.inwentarz.gui.scontrumlist.ScontrumListPanel;
import sebcel.inwentarz.gui.splash.Splash;
import sebcel.inwentarz.gui.statistics.StatisticsPanel;
import sebcel.inwentarz.gui.status.StatusPanel;
import sebcel.inwentarz.gui.utils.GuiTools;
import sebcel.inwentarz.logic.definition.ILifecycleManager;
import sebcel.inwentarz.logic.implementation.BookLifecycleManager;
import sebcel.inwentarz.logic.implementation.ScontrumLifecycleManager;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 3646966981291881166L;
    private static final Image icon = new ImageIcon("inwentarz.gif").getImage();
    private static final String APPLICATION_TITLE = "Inwentarz 1.2.4";

    public MainFrame(JComponent mainComponent) {
	this.setTitle(APPLICATION_TITLE);
	this.setLayout(new BorderLayout());
	this.setIconImage(icon);
	this.add(mainComponent, BorderLayout.CENTER);
    }

    public final static void main(String[] args) {
	Splash splash = new Splash();
	splash.show(APPLICATION_TITLE);

	Configuration configuration = loadConfiguration();

	JComponent gui = buildApplicationGUI(configuration.getDatabaseDriver(), configuration.getConnectionString());
	JFrame mainFrame = createMainFrame(gui);

	splash.hide();
	mainFrame.setVisible(true);
	mainFrame.toFront();
    }

    private static Configuration loadConfiguration() {
	try {
	    Properties properties = new Properties();
	    properties.load(new FileInputStream("config.properties"));
	    String databaseDriver = properties.getProperty("databaseDriver");
	    String connectionString = properties.getProperty("connectionString");
	    
	    System.out.println("databaseDriver: "+databaseDriver);
	    System.out.println("connectionString: "+connectionString);
	    
	    Configuration config = new Configuration();
	    config.setDatabaseDriver(databaseDriver);
	    config.setConnectionString(connectionString);
	    return config;
	} catch (Exception ex) {
	    throw new RuntimeException("Failed to load configuration from config.properties file: " + ex.getMessage(), ex);
	}
    }

    private static JComponent buildApplicationGUI(String driverClass, String databaseConnectionUrl) {
	final DataChangeManager dataChangeManager = new DataChangeManager();

	ConnectionFactory connectionFactory = new ConnectionFactory(driverClass, databaseConnectionUrl);

	IComparatorFactory comparatorFactory = new ComparatorFactory();

	final ILifecycleManager<BookStatus> bookLifecycleManager = new BookLifecycleManager();
	final ILifecycleManager<ScontrumStatus> scontrumLifecycleManager = new ScontrumLifecycleManager();

	final IScontrumDao scontrumDao = new ScontrumDao(connectionFactory);
	final IBookDao bookDao = new BookDao(connectionFactory, scontrumDao, dataChangeManager);
	final IAuthorDao authorDao = new AuthorDao(connectionFactory);
	final IDictionaryDao dictionaryDao = new DictionaryDao(connectionFactory);
	final IStatisticsDao statisticsDao = new StatisticsDao(connectionFactory);

	BookListPanel bookListPanel = buildBookListPanel(bookDao, dictionaryDao, bookLifecycleManager, authorDao, dataChangeManager);
	AuthorListPanel authorListPanel = buildAuthorListPanel(authorDao);
	StatisticsPanel statisticsPanel = new StatisticsPanel(statisticsDao);
	IScontrumViewer scontrumViewer = new ScontrumViewFrame(scontrumDao);
	ScontrumListPanel listPanel = new ScontrumListPanel(scontrumDao, comparatorFactory, scontrumViewer, scontrumLifecycleManager);
	ScontrumPanel scontrumPanel = new ScontrumPanel(scontrumDao, comparatorFactory, listPanel);
	listPanel.setDataChangeListener(scontrumPanel);
	StatusPanel statusPanel = buildStatusPanel(statisticsDao, dataChangeManager);

	JTabbedPane tabbedPane = new JTabbedPane();
	tabbedPane.addTab("Lista ksi¹¿ek", bookListPanel);
	tabbedPane.addTab("Lista autorów", authorListPanel);
	tabbedPane.addTab("Statystyka", statisticsPanel);
	tabbedPane.addTab("Skontrum", scontrumPanel);

	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(tabbedPane, BorderLayout.CENTER);
	mainPanel.add(statusPanel, BorderLayout.SOUTH);

	return mainPanel;
    }

    private static StatusPanel buildStatusPanel(IStatisticsDao statisticsDao, DataChangeManager dataChangeManager) {
	StatusPanel statusPanel = new StatusPanel(statisticsDao);
	dataChangeManager.addDataChangeListener(statusPanel);
	return statusPanel;
    }

    private static AuthorListPanel buildAuthorListPanel(IAuthorDao authorDao) {
	AuthorListTablePanel authorListTablePanel = new AuthorListTablePanel(authorDao);
	AuthorListFilterPanel authorListFilterPanel = new AuthorListFilterPanel();
	AuthorListButtonPanel authorListButtonPanel = new AuthorListButtonPanel();

	AuthorEditFrame authorCreator = new AuthorEditFrame(authorDao);
	AuthorEditFrame authorEditor = new AuthorEditFrame(authorDao);
	AuthorDeleteFrame authorDeletor = new AuthorDeleteFrame(authorDao);

	authorCreator.setIconImage(icon);
	authorEditor.setIconImage(icon);
	authorDeletor.setIconImage(icon);

	return new AuthorListPanel(authorListFilterPanel, authorListButtonPanel, authorListTablePanel, authorCreator, authorEditor, authorDeletor);
    }

    private static BookListPanel buildBookListPanel(IBookDao bookDao, IDictionaryDao dictionaryDao, ILifecycleManager<BookStatus> bookLifecycleManager, IAuthorDao authorDao, DataChangeManager dataChangeManager) {
	AuthorEditFrame authorPickerAuthorCreator = new AuthorEditFrame(authorDao);

	AuthorPicker authorPicker = new AuthorPicker(authorDao, authorPickerAuthorCreator);
	AuthorsBox bookEditorAuthorsBox = new AuthorsBox(authorPicker);
	AuthorsBox bookCreatorAuthorsBox = new AuthorsBox(authorPicker);

	IComparatorFactory comparatorFactory = new ComparatorFactory();

	BookListTablePanel bookListTablePanel = new BookListTablePanel(bookDao, comparatorFactory);
	BookListFilterPanel bookListFilterPanel = new BookListFilterPanel();
	BookListButtonPanel bookListButtonPanel = new BookListButtonPanel(bookLifecycleManager);

	BookEditFrame bookCreator = new BookEditFrame(bookDao, dictionaryDao, bookLifecycleManager, bookCreatorAuthorsBox);
	BookEditFrame bookEditor = new BookEditFrame(bookDao, dictionaryDao, bookLifecycleManager, bookEditorAuthorsBox);
	BookDeleteFrame bookDeletor = new BookDeleteFrame(bookDao);
	BookDetailsFrame bookDetailsViewer = new BookDetailsFrame(bookDao, dictionaryDao);
	BookViewRegisterFrame bookRegisterViewer = new BookViewRegisterFrame(bookDao);
	BookPrinterFactory bookPrinterFactory = new BookPrinterFactory(bookDao);
	BookLenderFrame bookLender = new BookLenderFrame(bookDao);
	BookReturnFrame bookReturner = new BookReturnFrame(bookDao);
	BookVerificationFrame bookVerifier = new BookVerificationFrame(bookDao);
	BookListPanel bookListPanel = new BookListPanel(bookListFilterPanel, bookListButtonPanel, bookListTablePanel, bookCreator, bookEditor, bookDeletor, bookDetailsViewer, bookRegisterViewer, bookPrinterFactory, bookLender,
		bookReturner, bookVerifier);

	bookCreator.setIconImage(icon);
	bookEditor.setIconImage(icon);
	bookDeletor.setIconImage(icon);
	bookDetailsViewer.setIconImage(icon);
	bookRegisterViewer.setIconImage(icon);
	bookLender.setIconImage(icon);
	bookReturner.setIconImage(icon);
	bookPrinterFactory.setIconImage(icon);
	authorPicker.setIconImage(icon);
	authorPickerAuthorCreator.setIconImage(icon);

	dataChangeManager.addDataChangeListener(bookListPanel);

	return bookListPanel;
    }

    private static JFrame createMainFrame(JComponent component) {
	JFrame mainFrame = new MainFrame(component);
	mainFrame.setSize(1200, 600);

	GuiTools.centerWindow(mainFrame);
	GuiTools.addDefaultWindowListener(mainFrame);

	return mainFrame;
    }
}