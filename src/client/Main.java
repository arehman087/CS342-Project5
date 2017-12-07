package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {
	private Client client;
	private ArrayList<ClientInfo> clients;
	
	public Main() throws UnknownHostException, IOException {
		this.client = new Client(this, "localhost", 10002);
		this.clients = new ArrayList<ClientInfo>();
	}
	
	public void onAddNewConnection(String id, String name, long kE, long kN) {
		System.err.println("% New Connection From " + id);
	}
	
	public void onDelOldConnection(String id) {
		System.err.println("% Lost Connection With " + id);
	}
	
	public void onSendNewMessage() {
		
	}
	
	public void onRecieveNewMessage() {
		
	}
	
	public void onServerDisconnect() {
		System.err.println("% Server Disconnected From Client");
	}
	
	public static void main(String args[]) throws IOException {
		Main main = new Main();
		main.client.sendHandshake("ABC", 1234, 5678);
		main.client.recieveInitialClients(main.clients);
		main.client.close();
	}
}
