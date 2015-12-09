import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
		mainFrame = new JFrame();
		initializeLogic();
		initializeMainFrame();
		initializeTabbedPane();

		initializeDialogPanel();
		initializeContacts();
	}

	private void initializeMainFrame(){
		mainFrame.setTitle("ChatApp 2015. SunRoSon");
		mainFrame.getContentPane().setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);
		mainFrame.setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logic.getServerConnection().goOffline();
				logic.getServerConnection().disconnect();

				stopProgram();
			}

			private void stopProgram() {
				System.exit(0);
            }
        });
		//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
	}

	private void initializeTabbedPane(){
		tabbedPane = new JTabbedPane();
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setOpaque(false);
	}

	private void initializeLogic() {
        logic = new Logic(new PopUpWindowGenerator(mainFrame), new ServerConnection(Constants.SERVER_ADDRESS_PATH));
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