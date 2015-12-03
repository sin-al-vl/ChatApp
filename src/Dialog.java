/*package org.eclipse.wb.swing;*/

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class Dialog extends JPanel{

	private JTextField textMessage;
	private JLabel sendButton;
	private JLabel disconnectButton;
	private JLabel text;
	private JList list;
	private int mode = 0;

	/**
	 * Create the application.
	 */
	public Dialog() {
		initialize();
		react();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setBounds(0, 0, 594, 571);
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		JPanel disconnectPanel = new JPanel();
		disconnectPanel.setOpaque(false);
		add(disconnectPanel, BorderLayout.NORTH);
		GridBagLayout gbl_disconnectPanel = new GridBagLayout();
		gbl_disconnectPanel.columnWidths = new int[]{46, 0, 0};
		gbl_disconnectPanel.rowHeights = new int[]{14, 0};
		gbl_disconnectPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_disconnectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		disconnectPanel.setLayout(gbl_disconnectPanel);
		
		text = new JLabel("Now you talking with user ...");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.insets = new Insets(0, 0, 0, 5);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		disconnectPanel.add(text, gbc_text);
		
		disconnectButton = new JLabel("");
		disconnectButton.setIcon(new ImageIcon(Constants.graphicPath + "cute\\enterbut.png"));
		GridBagConstraints gbc_disconnectButton = new GridBagConstraints();
		gbc_disconnectButton.gridx = 1;
		gbc_disconnectButton.gridy = 0;
		disconnectPanel.add(disconnectButton, gbc_disconnectButton);
		
		JPanel sendingPanel = new JPanel();
		sendingPanel.setOpaque(false);
		add(sendingPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_sendingPanel = new GridBagLayout();
		gbl_sendingPanel.columnWidths = new int[]{86, 0, 0};
		gbl_sendingPanel.rowHeights = new int[]{20, 0};
		gbl_sendingPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_sendingPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		sendingPanel.setLayout(gbl_sendingPanel);
		
		textMessage = new JTextField();
		GridBagConstraints gbc_textMessage = new GridBagConstraints();
		gbc_textMessage.fill = GridBagConstraints.BOTH;
		gbc_textMessage.insets = new Insets(0, 0, 0, 5);
		gbc_textMessage.gridx = 0;
		gbc_textMessage.gridy = 0;
		sendingPanel.add(textMessage, gbc_textMessage);
		textMessage.setColumns(10);
		
		sendButton = new JLabel("");
		sendButton.setIcon(new ImageIcon(Constants.graphicPath + "cute\\enterbut.png"));
		GridBagConstraints gbc_sendButton = new GridBagConstraints();
		gbc_sendButton.gridx = 1;
		gbc_sendButton.gridy = 0;
		sendingPanel.add(sendButton, gbc_sendButton);
		
		list = new JList();
		add(list, BorderLayout.CENTER);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setVisible(false);
		add(scrollBar, BorderLayout.EAST);
	}

	private void react(){
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				sendButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				sendButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
			}
		});
		
		disconnectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				disconnectButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbutchoise.png"));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				disconnectButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
			}
		});
	}
	
	public void setMode(int mode){
		this.mode = mode;
		sendButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		disconnectButton.setIcon(new ImageIcon(Constants.graphicPath + Constants.folder[mode]+"\\enterbut.png"));
		text.setFont(new Font(Constants.font[mode], Font.BOLD, Constants.fontSize[mode]));
		text.setForeground(Constants.fontColor[mode]);
		textMessage.setFont(new Font(Constants.font[mode], Font.BOLD, Constants.fontSize[mode]));
		textMessage.setForeground(Constants.fontColor[mode]);
		list.setFont(new Font(Constants.font[mode], Font.BOLD, Constants.fontSize[mode]));
		list.setForeground(Constants.fontColor[mode]);
	}
}
