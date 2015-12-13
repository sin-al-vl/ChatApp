import java.awt.*;
import java.awt.event.*;
import java.util.Observer;
import javax.swing.*;

public class MainForm {
	private LogicManager logicManager;
	public static JFrame mainFrame;
	public static JTabbedPane tabbedPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                MainForm window = new MainForm();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
				System.exit(0);
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
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("res\\chat_icon.png");
		mainFrame.setIconImage(img);
		mainFrame.getContentPane().setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);
		mainFrame.setMinimumSize(Constants.MINIMAL_PROGRAM_DIMENSION);

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					logicManager.getServerConnection().goOffline();
					logicManager.getServerConnection().disconnect();
				} catch (NullPointerException ignored){}

				stopProgram();
			}

			private void stopProgram() {
				System.exit(0);
            }
        });
		mainFrame.getContentPane().setLayout(new BorderLayout());
	}

	private void initializeTabbedPane(){
		tabbedPane = new JTabbedPane();
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setOpaque(false);
	}

	private void initializeLogic() {
        logicManager = new LogicManager(new PopUpWindowGenerator(mainFrame), new ServerConnection(Constants.SERVER_ADDRESS_PATH));
    }

	private void initializeDialogPanel(){
		DialogPanel dialogPanel = new DialogPanel(logicManager);
		tabbedPane.addTab("Dialog", dialogPanel);
	}

	private void initializeContacts(){
		Contacts contacts = new Contacts(logicManager);
		tabbedPane.addTab("Contacts", contacts);
	}

	private void setFon(){
		//TODO write method, that change fon
	}
}