package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private Client client;
	private ArrayList<ClientInfo> clients;
	private RSAKey rsa;
	private CGui gui;
	
	protected boolean isReady;
	
	public Main() {
		this.isReady = false;
	}
	
	public void onAddNewConnection(String id, String name, long kE, long kN) {
		System.err.println("% New Connection From " + id + " at index " +
				this.clients.size());
		
		this.clients.add(new ClientInfo(id, name, kE, kN));
		this.gui.addCList(name);
	}
	
	public void onDelOldConnection(String id) {
		System.err.println("% Lost Connection With " + id);
		
		for (int i = 0; i < this.clients.size(); ++i) {
			if (this.clients.get(i).getID().equals(id)) {
				this.clients.remove(i);
				this.gui.delList(i);
				return;
			}
		}
	}
	
	public void onSendNewMessage(String id, String msg) {
		this.client.send(id, msg);
		
		System.err.println("% Sent message \"" + msg + "\" to " + id);
	}
	
	public void onRecieveNewMessage(String id, String msg) {
		System.err.println("% Received message \"" + msg + "\" from " + id);
	}
	
	public void onServerDisconnect() {
		System.err.println("% Server Disconnected From Client");
	}
	
	public RSAKey getRSA(){
		return this.rsa;
	}
	
	public void setRSA(RSAKey r){
		this.rsa = r;
	}
	
	public void setClient(Client c) {
		this.client = c;
	}

	public static void main(String args[]) throws IOException {
		Main main = new Main();
		CGui c = new CGui(main);

		// Wait for user to enter their preferences
		while (!main.isReady) {
			
		}
		
		System.err.println("% Client Created " + main.client.getIP() +
				":" + main.client.getPort());
		System.err.println("% RSA Created " + main.rsa.getP() +
				", " + main.rsa.getQ());
		
		// On exit, disconnect from server
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        try {
					main.client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}));
		
		main.client.sendHandshake("ABC", 1234, 5678);
		main.client.recieveInitialClients(main.clients);
		main.client.setIsRunning(true);
		main.client.startListenThread();
	}
}
