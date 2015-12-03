package org.eclipse.wb.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Background extends JPanel{
	
	private int mode;
	private JLabel pictureLabel;
	
	public Background(){
		pictureLabel = new JLabel("");
		pictureLabel.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\fon.png"));
		add(pictureLabel);
	}
    
    public void setMode(int mode){
    	this.mode = mode;
    	pictureLabel.setIcon(new ImageIcon("D:\\Sofia\\Eclipse\\GH\\src\\org\\eclipse\\wb\\swing\\grafic\\"+Mode.folder[mode]+"\\fon.png"));
    }
    
}
