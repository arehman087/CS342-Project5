package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class TestClientListenThread implements Runnable {
	public TestClient client;
	
	/**
	 * Initializes the client listening thread.
	 * @param c The client.
	 */
	public TestClientListenThread(TestClient c) {
		this.client = c;
	}
	
	/**
	 * Runs the listening thread.
	 */
	public void run() {
		while (this.client.doRun) {
			try {
				if (this.client.in.ready()) {
					String msgType = this.client.in.readLine();
					System.out.println("received: " + msgType);
					
					// Server exit
					if (Integer.valueOf(msgType) == -1) {
						System.err.println("% Server Disconnected");
					}
					// New client connected
					else if (Integer.valueOf(msgType) == 1) {
						String clientID = this.client.in.readLine();
						String clientName = this.client.in.readLine();
						String clientKeyE = this.client.in.readLine();
						String clientKeyN = this.client.in.readLine();
						
						System.err.println("% New Client Connected: " +
								"ID: " + clientID + "; Name:" + clientName + 
								"; Key E: " + clientKeyE +
								"; Key N: " + clientKeyN);
						
						ConnectedClient cC = new ConnectedClient();
						cC.id = clientID;
						cC.name = clientName;
						cC.keyE = Integer.valueOf(clientKeyE);
						cC.keyN = Integer.valueOf(clientKeyN);
						this.client.connectClients.add(cC);
					} 
					// Client disconnect
					else if (Integer.valueOf(msgType) == 2) {
						String clientID = this.client.in.readLine();
						
						System.err.println("% Client Disconnected: " +
								clientID);
					}
					else if (Integer.valueOf(msgType) == 3) {
						String clientID = this.client.in.readLine();
						String clientMsg = this.client.in.readLine();
						
						System.err.println("% Received Message: \"" +
								clientMsg + "\" from " + clientID); 
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}

class ConnectedClient {
	public String id;
	public String name;
	public int keyE;
	public int keyN;
}

public class TestClient {
	public Socket socket;
	
	public PrintWriter out;
	public BufferedReader in;
	
	boolean doRun;
	
	ArrayList<ConnectedClient> connectClients = new ArrayList<ConnectedClient>();
	
	public TestClient(){
		this.doRun = false;
		
		try{
			this.socket = new Socket("localhost",10002);
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
		} catch(IOException  e){
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) throws IOException, InterruptedException {
    	TestClient client = new TestClient();
    	
    	int keyE = 12345;
    	int keyN = 56789;
    	
    	{ // Send my name and key
    		client.out.println("Test1");
    		client.out.println(keyE);
    		client.out.println(keyN);
    	}
    	
    	{ // Receive all of the connection information
    		int numConnections = Integer.valueOf(client.in.readLine());
    		
    		System.out.println("% Initial Number of Connections: " + numConnections);
    		for (int i = 0; i < numConnections; ++i) {
				String clientID = client.in.readLine();
				String clientName = client.in.readLine();
				String clientKeyE = client.in.readLine();
				String clientKeyN = client.in.readLine();
				
				System.err.println("% Initial Client Connected: " +
						"ID: " + clientID + "; Name:" + clientName + 
						"; Key E: " + clientKeyE +
						"; Key N: " + clientKeyN);
				
				ConnectedClient cC = new ConnectedClient();
				cC.id = clientID;
				cC.name = clientName;
				cC.keyE = Integer.valueOf(clientKeyE);
				cC.keyN = Integer.valueOf(clientKeyN);
				client.connectClients.add(cC);
    		}
    	}
    	
    	{ // Wait for OK from Server
    		client.in.readLine();
    	}
    	
    	Thread t = new Thread(new TestClientListenThread(client));
    	{ // Spawn thread for continual listening
    		client.doRun = true;
    		t.start();
    	}
    	
    	// Wait for new messages to be inputed by user
    	Scanner sC = new Scanner(System.in);
    	while (true) {
    		
    		if (sC.hasNextLine()) {
	    		String recipient = sC.nextLine();
	    		if (recipient.equals("EXIT")) {
	    			break;
	    		}
	    		String message = sC.nextLine();
    		
	    		client.out.println(client.connectClients.get(Integer.valueOf(recipient)).id);
	    		client.out.println(message);
    		}
    		
    	}
    	sC.close();
    	
    	{ // Close sockets
    		client.doRun = false;
    		t.join();
    		client.out.println("DISCONNECT");
    		
    		client.out.close();
			client.in.close();
    	}
    }
}
