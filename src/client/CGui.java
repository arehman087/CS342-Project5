package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class CGui{
	private JFrame window;		
	private JPanel buttonWindow;
	private JTextArea chat;
	private JTextField input;
	private JList<String> clients;
	private CMenu menu;
	
	private JTextField rsaVals;
	private boolean choose;
	
	Main main;
	
	JButton yes;
	JButton no;
	
	private boolean chooseIP;
	private boolean chooseName;
	
	private DefaultListModel<String> model;
	

	public CGui() {
		
		
		this.chooseIP = false;
		this.chooseName = false;
		this.window = new JFrame("Client");
		
		this.input = new JTextField();
		this.input.setEditable(false);
		this.input.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//onmessage
						
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
						CGui.this.main.setRSA(RSAKey.generateRSAKey());
						CGui.this.yes.setEnabled(false);
						CGui.this.no.setEnabled(false);
						CGui.this.rsaVals.setEditable(true);
						System.out.println("P AND Q " + CGui.this.main.getRSA().getP() +" "+ CGui.this.main.getRSA().getQ());

					}
				});
		
		this.model = new DefaultListModel<String>();
		
		this.clients = new JList<String>(this.model);
		
		this.clients.addListSelectionListener( 
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							//add the stuff for the list box
							CGui.this.chat.append("HELLO\n");
							System.out.println("hello");
					    }
					}
					
				});
		this.clients.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.clients.setLayoutOrientation(JList.VERTICAL);
		this.clients.setVisibleRowCount(-1);
		
		this.choose = false;
		
		this.yes.setEnabled(false);
		this.no.setEnabled(false);
		//get all info needed
		this.rsaVals = new JTextField();
		this.rsaVals.setEditable(true);
		this.rsaVals.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
						if (!CGui.this.chooseIP){
							
							//connect then store ip and port
							
							
							//TODO
							String ipp = e.getActionCommand().toString();
							String[] ipPort = ipp.split(" ");
							if (ipPort.length!=2){
								CGui.this.chat.append("Please enter the IP and the Port seperated by a space.\n");
								return;
							}
							try{
								CGui.this.main = new Main(CGui.this,ipPort[0], Integer.valueOf(ipPort[1]));
							} catch(Exception x){
								CGui.this.chat.append("Please enter the IP and the Port seperated by a space.\n");
								return;								
							}
							
							CGui.this.chooseIP = true;
							CGui.this.rsaVals.setText("");
							CGui.this.yes.setEnabled(true);
							CGui.this.no.setEnabled(true);
							
							
							
							CGui.this.chat.append("Client is connected\n");
							CGui.this.chat.append("please enter a name");
							return;
						}
						
						
											
						
						
						CGui.this.chat.append("please enter two Prime numbers with a space in between\n");
						
						
						String num = e.getActionCommand().toString();
						String[] nums = num.split(" ");
						if (nums.length!=2){
							
							return;
						}
						try{
							
							CGui.this.main.setRSA( RSAKey.generateRSAKey(Long.parseLong(nums[0]), Long.parseLong(nums[1])));
							CGui.this.choose = true;
						} catch(IllegalArgumentException i){
							CGui.this.rsaVals.setText("");	
							CGui.this.choose = false;
						}
						if (!choose){
							CGui.this.chat.append("Please try again \n");

							return;
						}
						else{
							CGui.this.choose = false;
							CGui.this.yes.setEnabled(false);
							CGui.this.no.setEnabled(false);
							CGui.this.input.setEditable(true);
							CGui.this.rsaVals.setEditable(false);
							CGui.this.chat.append("P AND Q " + CGui.this.main.getRSA().getP() +" "+ CGui.this.main.getRSA().getQ());
							CGui.this.rsaVals.setText("");	
						}
						
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
		this.chat.append("Please enter the IP and the Port seperated by a space.\n");
		
		this.menu = new CMenu();
		JMenuBar bar = new JMenuBar();
		this.window.setJMenuBar(bar);
		bar.add(menu.getFileMenu());
		bar.add(menu.getHelpMenu());
		
		
		
		this.window.setLayout(new BorderLayout());
		this.window.add(this.buttonWindow, BorderLayout.SOUTH);
		
		
		this.window.add(new JScrollPane(this.chat), BorderLayout.CENTER);
		this.window.add(new JScrollPane(this.clients), BorderLayout.WEST);
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		this.window.setSize(dim.width/2,dim.height/2);//here frame is your container 
		this.window.setVisible(true);
	}
	
	public void addCList(String a){
		this.model.addElement(a);
	}
	
	public void delList(int i){
		this.model.remove(i);
	}
}
