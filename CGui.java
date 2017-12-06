import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class CGui{
	private JFrame window;		
	private JTextArea chat;
	private JTextField input;
	private JList clients;
	private CMenu menu;
	

	public CGui() {
		this.window = new JFrame("Client");
		
		this.input = new JTextField();
		this.input.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//need to edit
						if (e.getActionCommand().equals("")){
							return;
						}
						CGui.this.chat.append(e.getActionCommand());
						CGui.this.chat.append("\n");
						CGui.this.input.setText("");
					}
				} );
		
		this.chat = new JTextArea();
		this.chat.setEditable(false);
		CGui.this.chat.append("");
		
		this.clients = new JList<String>();
		this.clients.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.clients.setLayoutOrientation(JList.VERTICAL);
		this.clients.setVisibleRowCount(-1);
		
		this.menu = new CMenu();
		JMenuBar bar = new JMenuBar();
		this.window.setJMenuBar(bar);
		bar.add(menu.getFileMenu());
		bar.add(menu.getHelpMenu());
		
		
		
		this.window.setLayout(new BorderLayout());
		this.window.add(this.input, BorderLayout.SOUTH);
		this.window.add(new JScrollPane(this.chat), BorderLayout.CENTER);
		this.window.add(new JScrollPane(this.clients), BorderLayout.WEST);
		//this.window.pack();
		this.window.setSize(new Dimension (500, 500));
		this.window.setVisible(true);
	}
	
	
public static void main(String[] args) throws IOException {
    	CGui c = new CGui();
    	
    }
}
