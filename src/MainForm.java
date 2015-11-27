import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
/*import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;*/
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.sql.Date;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
/*import org.eclipse.wb.swing.FocusTraversalOnArray;*/
import java.util.Observable;
import java.util.Observer;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class MainForm implements Observer{
	public static MainForm window;
	private JFrame mainFrame;
	private JTextField localLogin;
	private JTextField remlog;
	private JTextField remoteAddress;
	private JTextField msg;
	private CallListenerThread callListenerThread;
	private Caller caller;

	private DefaultListModel messageShowing;
	private JList list;
	private Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainForm();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the mainFrame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.getContentPane().setMinimumSize(new Dimension(454, 432));
		mainFrame.setMinimumSize(new Dimension(470, 470));
		mainFrame.setBounds(100, 100, 470, 470);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setName("Panel");
		panel.setBackground(new Color(255, 255, 255));
		panel.setOpaque(false);
		panel.setBounds(25, 25, 404, 382);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{75, 75, 13, 80, 75, 75, 0};
		gbl_panel_1.rowHeights = new int[]{20, 20, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel = new JLabel("local login");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		localLogin = new JTextField();
		GridBagConstraints gbc_loclog = new GridBagConstraints();
		gbc_loclog.fill = GridBagConstraints.BOTH;
		gbc_loclog.insets = new Insets(0, 0, 5, 5);
		gbc_loclog.gridx = 1;
		gbc_loclog.gridy = 0;
		panel_1.add(localLogin, gbc_loclog);
		localLogin.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("remote login");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		remlog = new JTextField();
		GridBagConstraints gbc_remlog = new GridBagConstraints();
		gbc_remlog.fill = GridBagConstraints.BOTH;
		gbc_remlog.insets = new Insets(0, 0, 5, 5);
		gbc_remlog.gridx = 4;
		gbc_remlog.gridy = 0;
		panel_1.add(remlog, gbc_remlog);
		remlog.setColumns(10);

		JButton DisconBut = new JButton("Disconnect");
		GridBagConstraints gbc_DisconBut = new GridBagConstraints();
		gbc_DisconBut.fill = GridBagConstraints.BOTH;
		gbc_DisconBut.insets = new Insets(0, 0, 5, 0);
		gbc_DisconBut.gridx = 5;
		gbc_DisconBut.gridy = 0;
		panel_1.add(DisconBut, gbc_DisconBut);

		JButton ApplyBut = new JButton("Apply");
		GridBagConstraints gbc_ApplyBut = new GridBagConstraints();
		gbc_ApplyBut.fill = GridBagConstraints.BOTH;
		gbc_ApplyBut.insets = new Insets(0, 0, 0, 5);
		gbc_ApplyBut.gridx = 0;
		gbc_ApplyBut.gridy = 1;
		panel_1.add(ApplyBut, gbc_ApplyBut);

		JLabel lblNewLabel_2 = new JLabel("remote addr");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 1;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

		remoteAddress = new JTextField();
		GridBagConstraints gbc_remadr = new GridBagConstraints();
		gbc_remadr.insets = new Insets(0, 0, 0, 5);
		gbc_remadr.fill = GridBagConstraints.BOTH;
		gbc_remadr.gridx = 4;
		gbc_remadr.gridy = 1;
		panel_1.add(remoteAddress, gbc_remadr);
		remoteAddress.setColumns(10);

		JButton ConBut = new JButton("Connect");
		GridBagConstraints gbc_ConBut = new GridBagConstraints();
		gbc_ConBut.fill = GridBagConstraints.BOTH;
		gbc_ConBut.gridx = 5;
		gbc_ConBut.gridy = 1;
		panel_1.add(ConBut, gbc_ConBut);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel.add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{331, 75, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		msg = new JTextField();
		GridBagConstraints gbc_msg = new GridBagConstraints();
		gbc_msg.fill = GridBagConstraints.BOTH;
		gbc_msg.insets = new Insets(0, 0, 0, 5);
		gbc_msg.gridx = 0;
		gbc_msg.gridy = 0;
		panel_2.add(msg, gbc_msg);
		msg.setColumns(10);

		JButton SendBut = new JButton("Send");
		GridBagConstraints gbc_SendBut = new GridBagConstraints();
		gbc_SendBut.fill = GridBagConstraints.BOTH;
		gbc_SendBut.gridx = 1;
		gbc_SendBut.gridy = 0;
		panel_2.add(SendBut, gbc_SendBut);

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel_3.setBorder(new EmptyBorder(5, 0, 5, 0));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.setViewportView(list);

		JPanel fonpanel = new JPanel();
		fonpanel.setName("fonpanel");
		fonpanel.setBackground(new Color(204, 255, 153));
		fonpanel.setBounds(0, 0, 454, 432);
		mainFrame.getContentPane().add(fonpanel);
		fonpanel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("src\\fon.png"));
		fonpanel.add(label, BorderLayout.SOUTH);

		JLayeredPane work = new JLayeredPane();
		work.setBounds(0, 0, 10, 10);
		//mainFrame.getContentPane().add(work);

		mainFrame.getContentPane().addComponentListener(new ComponentListener() {

				@Override
				public void componentResized(ComponentEvent e) {
					fonpanel.setSize(mainFrame.getContentPane().getWidth(), mainFrame.getContentPane().getHeight());
					panel.setSize(mainFrame.getContentPane().getWidth()-50, mainFrame.getContentPane().getHeight()-50);

				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub

				}
	        });

		mainFrame.addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent e) {
				fonpanel.setSize(mainFrame.getWidth() - 16, mainFrame.getHeight() - 38);
				panel.setSize(mainFrame.getWidth() - 66, mainFrame.getHeight() - 88);
			}

		});

		messageShowing = new DefaultListModel();
		SendBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (connection == null)
					JOptionPane.showMessageDialog(mainFrame, "No connection");
				else {
					printNickAndMessage(localLogin.getText(), msg.getText());

					try {
						connection.sendMessage(msg.getText());
						System.out.println("Sanded");
					} catch ( IOException | NullPointerException ex){
						System.out.println("No internet connection");
					}

				}
				msg.setText("");
				msg.requestFocus();
			}
		});

		msg.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					SendBut.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		//mainFrame.setLayeredPane(work);
		//work.add(fonpanel, new Integer(1));
		//work.add(panel,  new Integer(2));

		ApplyBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (callListenerThread == null) {
                    System.out.println("Added obs");
                    callListenerThread = new CallListenerThread(new CallListener(localLogin.getText()));
                    callListenerThread.addObserver(window);
                }
				else {
					callListenerThread.setLocalNick(localLogin.getText());
				}
			}
		});

		DisconBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connection != null)
					try {
						connection.disconnect();
					} catch (IOException ignored){
					}
				disconnect();
			}
		});

//-----------------Connect button options---------------------
		ConBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activateConnectButton();
			}
	    });
	}

	public void activateConnectButton(){
		if (localLoginAndRemoteAddressIsNotEmpty())
		{
			System.out.println("Local login: " + localLogin.getText());
			runCallThread();
		}
		else
		showNotEnoughParametersMessage();
	}

	private boolean localLoginAndRemoteAddressIsNotEmpty(){
		return !(localLogin.getText().equals("") && remoteAddress.getText().equals(""));
	}

	public void showNotEnoughParametersMessage(){
		JOptionPane.showMessageDialog(mainFrame, "Not enough actual parameters");
	}

	private void runCallThread(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				caller = new Caller(localLogin.getText(), remoteAddress.getText());
				connection = caller.call();
				checkCallStatus(caller.getCallStatus());
				System.out.println("Call status: " + caller.getCallStatus());
			}
		}).start();
	}

	private void checkCallStatus(Caller.CallStatus status) {
		switch (status) {
			case OK:
				remlog.setText(caller.getRemoteNick());
				notificationAboutSuccessConnection();
				break;

			case BUSY:
				showRemoteUserIsBusyNotification();
				break;

			case REJECTED:
				showRemoteUserIsRejectedYourCallNotification();
				break;

			case NOT_ACCESSIBLE:
				showRemoteUserIsNotAccessibleNotification();
				break;

			case NO_SERVICE:
				showRemoteUserDoesNoteExistNotification();
				break;
		}
	}

	private void showRemoteUserIsBusyNotification(){
		JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " is busy");
	}

	private void showRemoteUserIsRejectedYourCallNotification(){
		JOptionPane.showMessageDialog(mainFrame, "User " + caller.getRemoteNick() + " has declined your call.");
	}

	private void showRemoteUserIsNotAccessibleNotification(){
		JOptionPane.showMessageDialog(mainFrame, "User is not accessible");
	}

	private void showRemoteUserDoesNoteExistNotification(){
		JOptionPane.showMessageDialog(mainFrame, "User does not exist");
	}

	//------------End of connect button options-----------------------------
	public boolean acceptOrRejectMessage(String nick, String remoteAddress){
		Object[] options = {"Receive","Reject"};
		int dialogResult = JOptionPane.showOptionDialog(mainFrame,"User "+ nick + " with ip " + remoteAddress +
						" is trying to connect with you","Recive connection",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,options,options[0]);
		if(dialogResult == JOptionPane.YES_OPTION) {
			System.out.println("Receive");
			remlog.setText(nick);
			this.remoteAddress.setText(remoteAddress);
			return true; // Receive
		}
		System.out.println("Rejected");
			return false; //Reject

	}

	public void disconnect(){
		if (callListenerThread != null)
			callListenerThread.setBusy(false);

		remlog.setText("");
		remoteAddress.setText("");
		connection = null;
		caller = null;
	}

	public void getInsideCall(CallListener c){
		callListenerThread.suspend();
		boolean isReceiveCall = acceptOrRejectMessage(c.getRemoteNick(), c.getRemoteAddress());
		callListenerThread.setReceive(isReceiveCall);
		callListenerThread.resume();
	}

	public void setConnection(Connection connection){
		this.connection = connection;
	}

	public void printNickAndMessage(String nick, String message){
		messageShowing.addElement("<html>" + nick + " " + new Date(System.currentTimeMillis()).toLocaleString() + ":<br>" + message + " </span></html>");
		list.setModel(messageShowing);
	}

	public void showDisconnectMessage(){
		JOptionPane.showMessageDialog(mainFrame, "User " + remlog.getText() + " was disconnected");
	}

	public void notificationAboutSuccessConnection(){
		JOptionPane.showMessageDialog(mainFrame, "User " + remlog.getText() + " accept your call");
	}

	@Override
	public void update(Observable who, Object updateInfo)
	{
		if (updateInfo instanceof CallListener)
			getInsideCall((CallListener) updateInfo);

		else if (updateInfo instanceof Connection)
			setConnection((Connection) updateInfo);

		else if (updateInfo instanceof Command)
		{
			Command updateCommand = (Command) updateInfo;

			if (updateCommand instanceof MessageCommand)
				printNickAndMessage(remlog.getText(), updateInfo.toString());
			else if (updateCommand.toString().equals("DISCONNECT") || updateCommand.toString().equals("REJECT"))
			{
				showDisconnectMessage();
				disconnect();
			}
		}
	}
}