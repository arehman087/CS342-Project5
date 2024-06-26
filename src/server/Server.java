package server;

import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*; 

public class Server { 
	private static final int PORT = 10002;
	
	private static final int SEND_ON_EXIT = -1;
	private static final int SEND_ON_CLIENT_CONN = 1;
	private static final int SEND_ON_CLIENT_DISC = 2;
	private static final int SEND_ON_CLIENT_MSG = 3;
	
	private ServerSocket server;
	
	private SGui gui;
	
	private Thread serverListenThread;
	
	private HashMap <String, Client> clients;
	
	private boolean isRunning;
	/**
	 * Instantiates the server.
	 */
	public Server(SGui gui, int port) throws IOException {
		clients = new HashMap<String, Client>();
		this.server = new ServerSocket(port);
		this.gui = gui;
		
		this.isRunning = true;
		this.serverListenThread = new Thread(new ServerListenThread(this));
		this.serverListenThread.start();
	}
	
	/**
	 * Closes the server.
	 * @throws IOException 
	 */
	public void close() throws IOException {
		// Notify all connected clients of close
		for (Client c : this.clients.values()) {
			PrintWriter outC = new PrintWriter(
					c.getSocket().getOutputStream(), true);
			
			outC.println(SEND_ON_EXIT);
		}
		
		System.err.println("% Closed Server");
	}
	
	/**
	 * Gets the server socket.
	 * @return The socket.
	 */
	public ServerSocket getSocket() {
		return this.server;
	}
	
	public void setIsRunning(boolean a){
		this.isRunning = a;
	}
	
	public boolean getIsRunning(){
		return this.isRunning;
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
			int kE = Integer.valueOf(in.readLine());
			int kN = Integer.valueOf(in.readLine());
			
			// Notify all connected clients of the new connection
			for (Client c : this.clients.values()) {
				PrintWriter outC = new PrintWriter(
						c.getSocket().getOutputStream(), true);
				
				outC.println(SEND_ON_CLIENT_CONN);
				outC.println(sock.toString());
				outC.println(name);
				outC.println(kE);
				outC.println(kN);
			}
			
			// Notify the client of all of the currently connected clients
			out.println(this.clients.values().size());
			for (Client c : this.clients.values()) {
				out.println(c.getSocket().toString());
				out.println(c.getName());
				out.println(c.getKeyE());
				out.println(c.getKeyN());
			}
							
			Client client = new Client(sock, name, kE, kN);
			this.clients.put(client.getSocket().toString(), client);
			
			Thread connThread = new Thread(
					new ClientConnectionThread(this, client));
			connThread.start();
			out.println("OK");
			
			System.err.println("% Received New Connection: " + client);
			this.gui.addConnection(name + " (" + client.getSocket() + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the specified client from the server.
	 * @param client The client.
	 */
	public void removeConnection(Client client) {
		String socketName = client.getSocket().toString();
		this.clients.remove(client.getSocket().toString());
		
		// Notify all connected clients of the disconnection
		for (Client c : this.clients.values()) {
			try {
				PrintWriter outC = new PrintWriter(
						c.getSocket().getOutputStream(), true);
				
				outC.println(SEND_ON_CLIENT_DISC);
				outC.println(client.getSocket().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.err.println("% Lost Connection: " + client);
		this.gui.delConnection(client.getName() + " (" + socketName + ")");
	}
	
	/**
	 * Processes the specified message.
	 * @param client The client who sent the message.
	 * @param recv The designated recipient of the message.
	 * @param msg The message to be forwarded to the recipient.
	 */
	public void processMessage(Client client, String recv, String msg) {
		System.err.println("% Received message: \""  + msg + "\" from " +
				client.getName() + " for " + recv);
		
		Client recvClient = this.clients.get(recv);
		try {
			PrintWriter recvClientOut = new PrintWriter(
					recvClient.getSocket().getOutputStream(), true);
			
			recvClientOut.println(SEND_ON_CLIENT_MSG);
			recvClientOut.println(client.getSocket());
			recvClientOut.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
} 

