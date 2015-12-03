package org.eclipse.wb.swing;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class HelloWindow  extends JPanel{

	private JTextField nick;
	private JPasswordField password;
	private JLabel enterButton;
	private JLabel registrationButton;
	private JLabel textNick;
	private JLabel textPassword;
	private JLabel text;
	private int mode = 0;
	private JRadioButton StandartRB;
	private JRadioButton DarkRB;
	private JRadioButton CuteRB; 

	/**
	 * Create the panel.
	 */
	public HelloWindow() {
		initialize();
		react();
	}

	/**
	 * Initialize the contents of the panel.
	 */
	private void initialize() {
		setBounds(0, 0, 594, 571);
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		
		text = new JLabel("<html><div style=\"text-align: center;\">Hello! Welcome to our program. It is an app for chating with your friends. Before starting, please chouse the them of the program below. Then autorize. If you here at first, create your nick and password and press the registrate button. Have a nice day)</html>");
		text.setHorizontalTextPosition(SwingConstants.CENTER);
		text.setFont(new Font(Mode.font[mode], Font.BOLD, Mode.fontSize[mode]));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text, BorderLayout.NORTH);
		
		JPanel panelWork = new JPanel();
		add(panelWork, BorderLayout.SOUTH);
		GridBagLayout gbl_panelWork = new GridBagLayout();
		gbl_panelWork.columnWidths = new int[]{152, 200, 0, 0};
		gbl_panelWork.rowHeights = new int[]{0, 0, 0};
		gbl_panelWork.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelWork.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelWork.setLayout(gbl_panelWork);
		panelWork.setOpaque(false);
		
		textNick = new JLabel("Nick");
		textNick.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_textNick = new GridBagConstraints();
		gbc_textNick.insets = new Insets(0, 0, 5, 5);
		gbc_textNick.gridx = 0;
		gbc_textNick.gridy = 0;
		panelWork.add(textNick, gbc_textNick);
		
		nick = new JTextField();
		nick.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_nick = new GridBagConstraints();
		gbc_nick.insets = new Insets(0, 0, 5, 5);
		gbc_nick.fill = GridBagConstraints.BOTH;
		gbc_nick.gridx = 1;
		gbc_nick.gridy = 0;
		panelWork.add(nick, gbc_nick);
		nick.setColumns(10);
		
		enterButton = new JLabel("");
		enterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enterButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\green\\enterbut.png"));
		GridBagConstraints gbc_enterButton = new GridBagConstraints();
		gbc_enterButton.insets = new Insets(0, 0, 5, 0);
		gbc_enterButton.gridx = 2;
		gbc_enterButton.gridy = 0;
		panelWork.add(enterButton, gbc_enterButton);
		
		textPassword = new JLabel("Password");
		textPassword.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_textPassword = new GridBagConstraints();
		gbc_textPassword.insets = new Insets(0, 0, 0, 5);
		gbc_textPassword.gridx = 0;
		gbc_textPassword.gridy = 1;
		panelWork.add(textPassword, gbc_textPassword);
		
		password = new JPasswordField();
		password.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 0, 5);
		gbc_password.fill = GridBagConstraints.BOTH;
		gbc_password.gridx = 1;
		gbc_password.gridy = 1;
		panelWork.add(password, gbc_password);
		
		registrationButton = new JLabel("");
		registrationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registrationButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\green\\regbut.png"));
		GridBagConstraints gbc_registrationButton = new GridBagConstraints();
		gbc_registrationButton.gridx = 2;
		gbc_registrationButton.gridy = 1;
		panelWork.add(registrationButton, gbc_registrationButton);
		
		JPanel panelChoosingStyle = new JPanel();
		add(panelChoosingStyle, BorderLayout.CENTER);
		GridBagLayout gbl_panelChoosingStyle = new GridBagLayout();
		gbl_panelChoosingStyle.columnWidths = new int[]{151, 93, 89, 0};
		gbl_panelChoosingStyle.rowHeights = new int[]{51, 0};
		gbl_panelChoosingStyle.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelChoosingStyle.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelChoosingStyle.setLayout(gbl_panelChoosingStyle);
		panelChoosingStyle.setOpaque(false);
		
		StandartRB = new JRadioButton("Standart");
		StandartRB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		StandartRB.setSelected(true);
		StandartRB.setOpaque(false);
		StandartRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_StandartRB = new GridBagConstraints();
		gbc_StandartRB.insets = new Insets(0, 0, 0, 5);
		gbc_StandartRB.gridx = 0;
		gbc_StandartRB.gridy = 0;
		panelChoosingStyle.add(StandartRB, gbc_StandartRB);
		
		
		DarkRB = new JRadioButton("Dark");
		DarkRB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DarkRB.setOpaque(false);
		DarkRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_DarkRB = new GridBagConstraints();
		gbc_DarkRB.insets = new Insets(0, 0, 0, 5);
		gbc_DarkRB.gridx = 1;
		gbc_DarkRB.gridy = 0;
		panelChoosingStyle.add(DarkRB, gbc_DarkRB);
		
		
		CuteRB = new JRadioButton("Cute");
		CuteRB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CuteRB.setOpaque(false);
		CuteRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		GridBagConstraints gbc_CuteRB = new GridBagConstraints();
		gbc_CuteRB.gridx = 2;
		gbc_CuteRB.gridy = 0;
		panelChoosingStyle.add(CuteRB, gbc_CuteRB);
		
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(StandartRB);
	    group.add(DarkRB);
	    group.add(CuteRB);
	    
	}

	
	/**
	 * Make the panel react on changing.
	 */
	private void react(){

		StandartRB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (StandartRB.isSelected()) {setMode(0);}
				else {errorMessage();}
			}
		});
		
		DarkRB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (DarkRB.isSelected()) {setMode(1);}
				else {errorMessage();}
			}
		});
		
		CuteRB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (CuteRB.isSelected()) {setMode(2);}
				else {errorMessage();}
			}
		});
		
		enterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ((!getNick().equals("")) && (!getPassword().equals(""))){
					MainFormNew.helloHide();
					MainFormNew.setMode(mode);
				}
				else{errorMessage();}
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				enterButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				enterButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\enterbut.png"));
			}
		});
		
		registrationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ((!getNick().equals("")) && (!getPassword().equals(""))){
					MainFormNew.helloHide();
					MainFormNew.setMode(mode);
				}
				else{errorMessage();}
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				registrationButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\regbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				registrationButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\regbut.png"));
			}
		});
		
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		enterButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\enterbut.png"));
		registrationButton.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\regbut.png"));
		text.setFont(new Font(Mode.font[mode], Font.BOLD, Mode.fontSize[mode]));
		text.setForeground(Mode.fontColor[mode]);
		textNick.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		textNick.setForeground(Mode.fontColor[mode]);
		nick.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		nick.setForeground(Mode.fontColor[0]);
		textPassword.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		textPassword.setForeground(Mode.fontColor[mode]);
		password.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		password.setForeground(Mode.fontColor[0]);
		StandartRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		StandartRB.setForeground(Mode.fontColor[mode]);
		DarkRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		DarkRB.setForeground(Mode.fontColor[mode]);
		CuteRB.setFont(new Font(Mode.font[mode], Font.PLAIN, Mode.fontSize[mode]));
		CuteRB.setForeground(Mode.fontColor[mode]);
		MainFormNew.setMode(mode);
	}
	
	public String getNick() {
		return nick.getText();
	}
	
	public void errorMessage(){
		ImageIcon icon = new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\sorryImg.png");
		JOptionPane.showMessageDialog(this,
		    "<html>Not enough data for entering.<br>Please, write your nick and password.</html>",
		    "Oops",
		    JOptionPane.INFORMATION_MESSAGE,icon);
	}
	
	public String getPassword() {
		String pass = new String();
		for (int i=0;i<password.getPassword().length;i++){
			pass = pass + password.getPassword()[i];
		}
		return pass;
	}
	
}
