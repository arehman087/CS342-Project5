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
	
	Main main;
	
	private DefaultListModel<String> model;
	
	private String selectedClient;

	public CGui(Main m) {
		this.main = m;
		this.window = new JFrame("Client");
		
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.input = new JTextField();
		this.input.setEditable(true);
		this.input.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if (e.getActionCommand().equals("") ||
								selectedClient == null){
							return;
						} else {
							CGui.this.addToChatboxOutgoing(selectedClient, e.getActionCommand());
						}
					}
				} );
		
		this.model = new DefaultListModel<String>();
		
		this.clients = new JList<String>(this.model);
		
		this.clients.addListSelectionListener( 
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {
						selectedClient = CGui.this.clients.getSelectedValue();
					}
				});
		this.clients.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.clients.setLayoutOrientation(JList.VERTICAL);
		this.clients.setVisibleRowCount(-1);
		
		this.buttonWindow = new JPanel();
		JPanel buttonsAndTxtbox = new JPanel();
		buttonsAndTxtbox.setLayout(new FlowLayout());
		this.buttonWindow.setLayout(new BorderLayout());
		
		buttonsAndTxtbox.add(this.buttonWindow);
		this.buttonWindow.add(this.input);
		
		
		this.chat = new JTextArea();
		this.chat.setEditable(false);
		
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
		
		while (true) {
			String address = JOptionPane.showInputDialog(
					 "Enter the IP address and port, separated by a space",
					 "");
			try {
				String[] addressSplit = address.split("\\s+");

				Client client = new Client(this.main,
						addressSplit[0], Integer.valueOf(addressSplit[1]));
				this.main.setClient(client);
				
				break;
			} catch (Exception e) {
				continue;
			}
		}
		
		int useCustom = JOptionPane.showConfirmDialog(null, "Use custom P & Q values?");
		if (useCustom == JOptionPane.YES_OPTION) {
			while (true) {
				String values = JOptionPane.showInputDialog( 
						 "Enter the p and q values, separated by a space",
						 "");
				try {
					String[] valuesSplit = values.split("\\s+");
				
					RSAKey rsa = RSAKey.generateRSAKey(Long.valueOf(valuesSplit[0]), 
							Long.valueOf(valuesSplit[1]));
					this.main.setRSA(rsa);
					
					break;
				} catch (Exception e) {
					continue;
				}
			}
		} else {
			this.main.setRSA(RSAKey.generateRSAKey());
		}
		
		String name = JOptionPane.showInputDialog(
				"Please enter your name",
				"");
		this.main.setName(name);
		
		this.main.isReady = true;
	}
	
	public void addToChatboxOutgoing(String recp, String msg) {
		int idStart = selectedClient.indexOf('(');
		int idEnd = selectedClient.indexOf(')');
		String clientName = selectedClient.substring(0, idStart - 1);
		String clientID = selectedClient.substring(idStart + 1, idEnd);
		
		this.chat.append(CGui.this.main.getName() + 
				" (for " + clientName + "): ");
		this.chat.append(msg);
		this.chat.append("\n");
		this.input.setText("");
		
		this.main.onSendNewMessage(clientID, msg);
	}
	
	public void addToChatboxIncoming(String sender, String msg) {
		this.chat.append(sender + ": " + msg);
		this.chat.append("\n");
	}
	
	public void addCList(String a){
		this.model.addElement(a);
	}
	
	public void delList(String a){
		this.model.removeElement(a);
	}
}
