import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;



public class CGui{
	private JFrame window;		
	private JPanel buttonWindow;
	private JTextArea chat;
	private JTextField input;
	private JList clients;
	private CMenu menu;
	
	private JTextField rsaVals;
	private boolean choose;
	
	private RSAKey key;
	
	JButton yes;
	JButton no;
	
//	private long p;
//	private long q;
	
	
	

	public CGui() {
		
		
		this.window = new JFrame("Client");
		
		this.input = new JTextField();
		this.input.setEditable(false);
		this.input.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						if (e.getActionCommand().equals("")){
							return;
						}
						CGui.this.chat.append(e.getActionCommand());
						CGui.this.chat.append("\n");
						CGui.this.input.setText("");
					}
				} );
		
		this.yes = new JButton("YES");
		this.yes.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CGui.this.rsaVals.setEditable(true);
						CGui.this.yes.setEnabled(false);
						CGui.this.no.setEnabled(false);
					}
				});
		this.no = new JButton("NO");
		this.no.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						CGui.this.key = RSAKey.generateRSAKey();
						CGui.this.yes.setEnabled(false);
						CGui.this.no.setEnabled(false);
						CGui.this.input.setEditable(true);
						System.out.println("P AND Q " + CGui.this.key.getP() +" "+ CGui.this.key.getQ());
					}
				});
		
		this.rsaVals = new JTextField();
		this.rsaVals.setEditable(false);
		this.rsaVals.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						// TODO this needs work...
						if (e.getActionCommand().equals("")){
							return;
						}
						CGui.this.chat.append(e.getActionCommand());
						CGui.this.chat.append("\n");
						CGui.this.input.setText("");
					}
				} );
		
		this.buttonWindow = new JPanel();
		JPanel buttonsAndTxtbox = new JPanel();
		buttonsAndTxtbox.setLayout(new FlowLayout());
		this.buttonWindow.setLayout(new BorderLayout());
		this.buttonWindow.add(this.yes, BorderLayout.WEST);
		this.buttonWindow.add(this.no, BorderLayout.EAST);
		this.buttonWindow.add(this.rsaVals, BorderLayout.SOUTH);
		
		buttonsAndTxtbox.add(this.buttonWindow);
		this.buttonWindow.add(this.input);
		
		
		this.chat = new JTextArea();
		this.chat.setEditable(false);
		CGui.this.chat.append("Would you like to choose your own p and q values?\n"+"yes/no\n");
		
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
		this.window.add(this.buttonWindow, BorderLayout.SOUTH);
		//this.window.add(this.input, BorderLayout.SOUTH);
		
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
