/*package org.eclipse.wb.swing;*/

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;

public class MainFormNew {

	public static JFrame frame;
	private static HelloWindow helloPanel = new HelloWindow();
	private static Contacts contactsPanel = new Contacts();
	private static Dialog dialogPanel = new Dialog();
	private static Background backgroundPanel = new Background();
	private static int mode = helloPanel.getMode();
	private static JPanel afterPanel = new JPanel(); //this is panel that appears after initialization and choosing mode.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFormNew window = new MainFormNew();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFormNew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		helloPanel.setOpaque(false);
		helloPanel.setBounds(0, 0, 594, 571);
		frame.getContentPane().add(helloPanel);
		dialogPanel.setOpaque(false);
		dialogPanel.setBounds(0, 0, 594, 571);
		frame.getContentPane().add(dialogPanel);
		contactsPanel.setOpaque(false);
		contactsPanel.setBounds(0, 0, 594, 571);
		frame.getContentPane().add(contactsPanel);
		backgroundPanel.setBounds(0, 0, 594, 571);
		frame.getContentPane().add(backgroundPanel);
		dialogPanel.setVisible(false);
		contactsPanel.setVisible(false);
	}

	public static void setMode(int model){
		mode = model;
		backgroundPanel.setMode(model);
		contactsPanel.setMode(model);
		dialogPanel.setMode(model);
	}
	
	public static void helloHide(){
		helloPanel.setVisible(false);
		contactsPanel.setVisible(true);
	}
	
	public static void contactsHide(){
		contactsPanel.setVisible(false);
		dialogPanel.setVisible(true);
	}
}
