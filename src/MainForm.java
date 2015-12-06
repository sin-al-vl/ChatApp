import java.awt.*;
import javax.swing.*;

public class MainForm {
	private Logic logic;
	private JFrame mainFrame;
	public static JTabbedPane tabbedPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                MainForm window = new MainForm();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	public MainForm() {
		initialize();
	}

	private void initialize() {
		initializeMainFrame();
		initializeLogic();
		initializeTabbedPane();

		initializeDialogPanel();
		initializeContacts();
	}

	private void initializeMainFrame(){
		mainFrame = new JFrame();
		mainFrame.setTitle("ChatApp 2015. SunRoSon");
		mainFrame.getContentPane().setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);
		mainFrame.setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
	}

	private void initializeTabbedPane(){
		tabbedPane = new JTabbedPane();
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setOpaque(false);
	}

	private void initializeLogic(){
		logic = new Logic(new PopUpWindowGenerator(mainFrame));
	}

	private void initializeDialogPanel(){
		DialogPanel dialogPanel = new DialogPanel(logic);
		tabbedPane.addTab("Dialog", dialogPanel);
	}

	private void initializeContacts(){
		Contacts contacts = new Contacts(logic);
		tabbedPane.addTab("Contacts", contacts);
	}

	private void setFon(){
		//TODO write method, that change fon
	}
}