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
	
	/**
	 * Instantiates the server.
	 */
	public Server() {
		clients = new HashMap<Integer, Client>();
		
		try {
			this.server = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.serverListenThread = new Thread(new ServerListenThread(this));
		this.serverListenThread.start();
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
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			
			String name = in.readLine();
			int kD = Integer.valueOf(in.readLine());
			int kN = Integer.valueOf(in.readLine());
			
			Client client = new Client(sock, name, kD, kN, NEXT_ID++);
			this.clients.put(client.getID(), client);
			
			Thread connThread = new Thread(
					new ClientConnectionThread(this, client));
			connThread.start();
			out.println("OK");
			
			System.err.println("% Received New Connection: " + client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the specified client from the server.
	 * @param client The client.
	 */
	public void removeConnection(Client client) {
		this.clients.remove(client.getID());
		System.err.println("% Lost Connection: " + client);
	}
	
	/**
	 * Processes the specified message.
	 * @param client The client who sent the message.
	 * @param recv The designated recipie`nt of the message.
	 * @param msg The message to be forwarded to the recipient.
	 */
	public void processMessage(Client client, String recv, String msg) {
		System.err.println("% Received message: \""  + msg + "\" from " +
				client.getName() + " for " + recv);
	}
	
	public static void main(String[] args){
		Server S = new Server();
	}
	
} 

