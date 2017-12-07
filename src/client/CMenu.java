package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class CMenu extends JFrame{
	private JMenu fileMenu;
	private JMenu helpMenu;
	
	private CGui cgui;
	
	public CMenu() {
		
		
		this.fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		this.fileMenu.add(exitItem);
		exitItem.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
		
		this.helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About...");
		this.helpMenu.add(aboutItem);
		aboutItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e){
				 JOptionPane.showMessageDialog(null, "Team Members: \n Anatoly"
				 		+ " Tverdovsky - atverd2 \n Abdul Rehman - arehma7"
				 		, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
	}
	
	/**
	 * @return gets the file menu contents
	 */
	public JMenu getFileMenu(){
		return this.fileMenu;
	}
	/**
	 * @return gets the help menu contents
	 */
	public JMenu getHelpMenu(){
		return this.helpMenu;
	}

}
