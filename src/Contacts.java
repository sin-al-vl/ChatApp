/*package org.eclipse.wb.swing;*/

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Contacts extends JPanel{

	private JTextField nick;
	private JTextField ip;
	private JTextField search;
	private JLabel addNewButton;
	private JLabel deleteButton;
	private JLabel deleteAllButton;
	private JLabel connectButton;
	private JLabel textNick;
	private JLabel textIP;
	private JLabel text;
	private JList list;
	private int mode = 0;
	private DefaultListModel listModel = new DefaultListModel();

	/**
	 * Create the application.
	 */
	public Contacts() {
		initialize();
		react();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(0, 0, 594, 571);
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);

		JPanel connectPanel = new JPanel();
		add(connectPanel, BorderLayout.NORTH);
		GridBagLayout gbl_connectPanel = new GridBagLayout();
		gbl_connectPanel.columnWidths = new int[]{60, 90, 60, 90, 40, 0};
		gbl_connectPanel.rowHeights = new int[]{40, 0};
		gbl_connectPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_connectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		connectPanel.setOpaque(false);
		connectPanel.setLayout(gbl_connectPanel);

		textNick = new JLabel("Nick");
		textNick.setHorizontalAlignment(SwingConstants.CENTER);
		textNick.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_textNick = new GridBagConstraints();
		gbc_textNick.fill = GridBagConstraints.VERTICAL;
		gbc_textNick.insets = new Insets(0, 0, 0, 5);
		gbc_textNick.gridx = 0;
		gbc_textNick.gridy = 0;
		connectPanel.add(textNick, gbc_textNick);

		nick = new JTextField();
		GridBagConstraints gbc_nick = new GridBagConstraints();
		gbc_nick.fill = GridBagConstraints.BOTH;
		gbc_nick.insets = new Insets(0, 0, 0, 5);
		gbc_nick.gridx = 1;
		gbc_nick.gridy = 0;
		connectPanel.add(nick, gbc_nick);
		nick.setColumns(10);

		textIP = new JLabel("IP");
		GridBagConstraints gbc_textIP = new GridBagConstraints();
		gbc_textIP.fill = GridBagConstraints.VERTICAL;
		gbc_textIP.insets = new Insets(0, 0, 0, 5);
		gbc_textIP.gridx = 2;
		gbc_textIP.gridy = 0;
		connectPanel.add(textIP, gbc_textIP);

		ip = new JTextField();
		GridBagConstraints gbc_ip = new GridBagConstraints();
		gbc_ip.fill = GridBagConstraints.BOTH;
		gbc_ip.insets = new Insets(0, 0, 0, 5);
		gbc_ip.gridx = 3;
		gbc_ip.gridy = 0;
		connectPanel.add(ip, gbc_ip);
		ip.setColumns(10);

		connectButton = new JLabel("");
		connectButton.setIcon(new ImageIcon(Constants.graphicPath+Constants.folder[mode]+"\\enterbut.png"));
		connectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connectButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		connectButton.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_connectButton = new GridBagConstraints();
		gbc_connectButton.fill = GridBagConstraints.VERTICAL;
		gbc_connectButton.gridx = 4;
		gbc_connectButton.gridy = 0;
		connectPanel.add(connectButton, gbc_connectButton);

		JPanel contactListPanel = new JPanel();
		add(contactListPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contactListPanel = new GridBagLayout();
		gbl_contactListPanel.columnWidths = new int[]{434, 0};
		gbl_contactListPanel.rowHeights = new int[]{33, 0, 0, 0};
		gbl_contactListPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contactListPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contactListPanel.setOpaque(false);
		contactListPanel.setLayout(gbl_contactListPanel);

		text = new JLabel("Or you can chouse interlocutor from the list below");
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.fill = GridBagConstraints.VERTICAL;
		gbc_text.insets = new Insets(0, 0, 5, 0);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		contactListPanel.add(text, gbc_text);

		search = new JTextField();
		GridBagConstraints gbc_search = new GridBagConstraints();
		gbc_search.insets = new Insets(0, 0, 5, 0);
		gbc_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_search.gridx = 0;
		gbc_search.gridy = 1;
		contactListPanel.add(search, gbc_search);
		search.setColumns(10);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contactListPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		list = new JList();
		panel.add(list);

		JScrollPane scrollBar = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollBar, BorderLayout.CENTER);

		JPanel workWithContactsPanel = new JPanel();
		workWithContactsPanel.setOpaque(false);
		add(workWithContactsPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_workWithContactsPanel = new GridBagLayout();
		gbl_workWithContactsPanel.columnWidths = new int[]{42, 31, 44, 0};
		gbl_workWithContactsPanel.rowHeights = new int[]{40, 0};
		gbl_workWithContactsPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_workWithContactsPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		workWithContactsPanel.setLayout(gbl_workWithContactsPanel);

		addNewButton = new JLabel("");
		addNewButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		addNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_addNewButton = new GridBagConstraints();
		gbc_addNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_addNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_addNewButton.gridx = 0;
		gbc_addNewButton.gridy = 0;
		workWithContactsPanel.add(addNewButton, gbc_addNewButton);

		deleteButton = new JLabel("");
		deleteButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.fill = GridBagConstraints.VERTICAL;
		gbc_deleteButton.insets = new Insets(0, 0, 0, 5);
		gbc_deleteButton.gridx = 1;
		gbc_deleteButton.gridy = 0;
		workWithContactsPanel.add(deleteButton, gbc_deleteButton);

		deleteAllButton = new JLabel("");
		deleteAllButton.setIcon(new ImageIcon(Constants.graphicPath +Constants.folder[mode] + "\\enterbut.png"));
		deleteAllButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_deleteAllButton = new GridBagConstraints();
		gbc_deleteAllButton.fill = GridBagConstraints.BOTH;
		gbc_deleteAllButton.gridx = 2;
		gbc_deleteAllButton.gridy = 0;
		workWithContactsPanel.add(deleteAllButton, gbc_deleteAllButton);
	}

	private void react(){
		connectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainFormNew.contactsHide();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				connectButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				connectButton.setIcon(new ImageIcon(Constants.graphicPath+Constants.folder[mode]+"\\enterbut.png"));
			}
		});

		addNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (questionMessage("add")) {
					listModel.addElement(getNick()+" "+getIP());
					list.setModel(listModel);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				addNewButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				addNewButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
			}
		});

		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (questionMessage("delete")) {
					nick.setText("");
					ip.setText("");
					listModel.remove(list.getSelectedIndex());
					list.setModel(listModel);
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				deleteButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				deleteButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
			}
		});

		deleteAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (questionMessage("delete all")) {
					nick.setText("");
					ip.setText("");
					listModel.clear();
					list.setModel(listModel);
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				deleteAllButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				deleteAllButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
			}
		});

		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				boolean whatString = true;
				String nickFriend = "";
				String ipFriend = "";
				String friend = list.getSelectedValue().toString();
				for (int i=0;i<friend.length();i++){
					if (friend.charAt(i) == ' '){
						whatString = !whatString;
					}
					else {
						if (whatString){
							nickFriend = nickFriend + friend.charAt(i);
						}
						else {
							ipFriend = ipFriend + friend.charAt(i);
						}
					}
				}
				nick.setText(nickFriend);
				ip.setText(ipFriend);
			}

		});
	}

	public void setMode(int mode){
		this.mode = mode;
		connectButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		addNewButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		deleteButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		deleteAllButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		text.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		text.setForeground(Constants.fontColor[mode]);
		textNick.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		textNick.setForeground(Constants.fontColor[mode]);
		textIP.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		textIP.setForeground(Constants.fontColor[mode]);
		nick.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		nick.setForeground(Constants.fontColor[0]);
		ip.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		ip.setForeground(Constants.fontColor[0]);
		search.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		search.setForeground(Constants.fontColor[0]);
		list.setFont(new Font(Constants.font[mode], Font.PLAIN, Constants.fontSize[mode]-2));
		list.setForeground(Constants.fontColor[0]);
	}

	public boolean questionMessage(String action){
		String question = "";
		if (action.equals("add")) {question = "Would you like to add user "+getNick()+" with IP: "+getIP()+" to your friend list?";}
		if (action.equals("delete")) {question = "Would you like to delete user "+getNick()+" with IP: "+getIP()+" from your friend list?";}
		if (action.equals("delete all")) {question = "Would you like to remove all users from your friend list?";}
		boolean ans = false;
		ImageIcon icon = new ImageIcon("Constants.graphicPath\\questionImg.png");
		Object[] options = {"Yes", "No"};
		int dialogResult = JOptionPane.showOptionDialog(this, question, "Adding a new friend", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
		if (dialogResult == JOptionPane.YES_OPTION){ans=true;}
		return ans;
	}

	public String getNick(){
		return nick.getText();
	}

	public String getIP(){
		return ip.getText();
	}

	public void setNick(String nick){
		this.nick.setText(nick);
	}

	public void setIP(String ip){
		this.ip.setText(ip);
	}
}
