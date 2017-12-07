package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private Client client;
	private ArrayList<ClientInfo> clients;
	private RSAKey rsa;
	private CGui c;
	public Main(CGui c, String s, int p) throws UnknownHostException, IOException {
		this.client = new Client(this, s, p);
		this.clients = new ArrayList<ClientInfo>();
		this.c = c;
	}
	
	public void onAddNewConnection(String id, String name, long kE, long kN) {
		System.err.println("% New Connection From " + id + " at index " +
				this.clients.size());
		
		this.clients.add(new ClientInfo(id, name, kE, kN));
		this.c.addCList(name);
	}
	
	public void onDelOldConnection(String id) {
		System.err.println("% Lost Connection With " + id);
		
		for (int i = 0; i < this.clients.size(); ++i) {
			if (this.clients.get(i).getID().equals(id)) {
				this.clients.remove(i);
				this.c.delList(i);
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
	
	public static void main(String args[]) throws IOException {
		
		CGui c = new CGui();
		
//		Main main = new Main("localHost", 1000);
//		main.client.sendHandshake("ABC", 1234, 5678);
//		main.client.recieveInitialClients(main.clients);
//		main.client.setIsRunning(true);
//		main.client.startListenThread();
//		
//		// Wait for new messages to be inputed by user
//    	Scanner sC = new Scanner(System.in);
//    	while (true) {
//    		
//    		if (sC.hasNextLine()) {
//	    		String recipient = sC.nextLine();
//	    		if (recipient.equals("EXIT")) {
//	    			break;
//	    		}
//	    		String message = sC.nextLine();
//    		
//	    		main.client.getOut().println(
//	    				main.clients.get(Integer.valueOf(recipient)).getID());
//	    		main.client.getOut().println(message);
//    		}
//    		
//    	}
//    	sC.close();
//		
//		main.client.close();
	}
}
