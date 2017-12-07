import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SGui {
	
	private JFrame window;	
	private JPanel buttonWindow;
	private JLabel machineInfo;
	private JLabel portInfo;
	
	private JButton start;
	private SMenu menu;
	
	private JList<String> clients;
	private boolean isRunning;
	
	
	public SGui() {
		this.isRunning = false;
		this.window = new JFrame("Server");
		
		this.buttonWindow = new JPanel();
		this.buttonWindow.setLayout(new FlowLayout());
		
		this.start = new JButton("Start Listening");
		this.start.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//begin connection to the server-client here
						SGui.this.start.setText("Stop Listening");
						if (SGui.this.isRunning){
							SGui.this.isRunning = false;
						}
						else{
							SGui.this.start.setText("Start Listening");
							SGui.this.isRunning = true;
						}
							
					}
				});
		
		this.buttonWindow.add(this.start);
		
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
		 this.clients.addListSelectionListener( 
				 new ListSelectionListener(){
					 
					 public void valueChanged(ListSelectionEvent e) {
							if (!e.getValueIsAdjusting()) {
								//need to see if this is needed for ANYTHING
								System.out.println("ClientName: " + SGui.this.clients.getSelectedValue());
							}
						}
				 });
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
		 
		 this.window.setSize(new Dimension (500, 500));
		 this.window.setVisible(true);
		
	}
	
	public static void main(String[] args) throws IOException {
    	SGui s = new SGui();
	}
}
