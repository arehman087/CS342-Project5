package server;

import java.net.*;
import java.util.HashMap;
import java.io.*; 

public class Server { 
	private static final int PORT = 10002;
	private static int NEXT_ID = 0;
	
	private ServerSocket server;
	
	private Thread serverListenThread;
	
	private HashMap <Integer, Client> clients;
	
	public Server() {
		clients = new HashMap<Integer, Client>();
		
		try {
			this.server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.serverListenThread = new Thread(new ServerListenThread(this));
		this.serverListenThread.run();
	}
	
	/**
	 * Gets the server socket.
	 * @return The socket.
	 */
	public ServerSocket getSocket() {
		return this.server;
	}
	
	/**
	 * Adds the specified socket to the server.
	 * @param sock The socket.
	 */
	public void addConnection(Socket sock) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			
			String name = in.readLine();
			int kD = Integer.valueOf(in.readLine());
			int kN = Integer.valueOf(in.readLine());
			
			Client client = new Client(sock, name, kD, kN, NEXT_ID++);
			this.clients.put(client.getID(), client);

			System.err.println("Received New Connection: " + client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Server S = new Server();
	}
	
} 

