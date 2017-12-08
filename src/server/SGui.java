package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Client;

public class SGui {
	
	private JFrame window;	
	private JPanel buttonWindow;
	private JLabel machineInfo;
	private JLabel portInfo;
	
	private JButton start;
	private SMenu menu;
	
	private JList<String> clients;
	private boolean isRunning;
	
	private Server server;
	
	
	public SGui() {
		this.isRunning = false;
		this.window = new JFrame("Server");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.buttonWindow = new JPanel();
		this.buttonWindow.setLayout(new FlowLayout());
		
		this.start = new JButton("Start Listening");
		this.start.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if (!SGui.this.isRunning) {
							SGui.this.start.setEnabled(false);
							
							SGui.this.start.setText("Start Listening");
							try {
								SGui.this.server.setIsRunning(false);
								
								SGui.this.server.getSocket().close();
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							try {
								SGui.this.server.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
		
		this.buttonWindow.add(this.start);
		this.start.setEnabled(false);

		//get and displa the info for the server.
		String serverAddress = null;
		
		 try
	      {  
			 //TODO replace with sever stuffs 
	        InetAddress addr = InetAddress.getLocalHost();
	        serverAddress = addr.getHostAddress();
	      }
	      catch (UnknownHostException e)
	      {
	        serverAddress = "127.0.0.1";
	      }
		 this.machineInfo = new JLabel(serverAddress);
		 this.buttonWindow.add(this.machineInfo);
		 this.portInfo = new JLabel("NONE");
		 this.buttonWindow.add(this.portInfo);
		 
		 
		 
		 DefaultListModel<String> model = new DefaultListModel<String>();
		 this.clients = new JList<String>(model);
		 model.addElement("mas");
		 model.addElement("bien");
		 this.clients.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		 this.clients.setLayoutOrientation(JList.VERTICAL);
		 this.clients.setVisibleRowCount(-1);
		 
		 
		 this.window.setLayout(new BorderLayout());
		 this.window.add(this.buttonWindow, BorderLayout.NORTH);
		 this.window.add(new JScrollPane(this.clients), BorderLayout.CENTER);
		
		 this.menu = new SMenu();
		 JMenuBar bar = new JMenuBar();
		 this.window.setJMenuBar(bar);
		 bar.add(menu.getFileMenu());
		 bar.add(menu.getHelpMenu());		 
		 Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		 Dimension dim = toolkit.getScreenSize();
		 
		 this.window.setSize(dim.width/2,dim.height/2);
		 this.window.setVisible(true);
		 
		 while (true) {
			String port = JOptionPane.showInputDialog(
					 "Enter the port number",
					 "");
			try {
				this.server = new Server(Integer.valueOf(port));
				this.portInfo.setText(port);
				this.start.setEnabled(true);
				this.start.setText("Stop Listening");
				this.isRunning = true;
				break;
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		SGui sg = new SGui();
		
		// On exit, disconnect from server
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        try {
					sg.server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}));
	}
}
