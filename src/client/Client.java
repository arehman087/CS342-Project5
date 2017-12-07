package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private Main mainRef;
	private Thread listenThread;
	
	private String ip;
	private int port;
	
	private boolean isRunning;
	
	/**
	 * Instantiates a new Client.
	 * @param main The main reference.
	 * @param ip The IP address.
	 * @param port The port number.
	 * @throws IOException If socket connection fails.
	 * @throws UnknownHostException If socket connection fails
	 */
	public Client(Main main, String ip, int port) throws
			UnknownHostException, IOException {
		this.mainRef = main;
		this.ip = ip;
		this.port = port;
		
		this.socket = new Socket(this.ip, this.port);
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(
				this.socket.getInputStream()));
		
		this.isRunning = false;
		
		System.err.println("% Established Connection with Server");
	}
	
	/**
	 * Closes the connection.
	 * @throws IOException If failed to close connection.
	 */
	public void close() throws IOException {
		this.out.println("DISCONNECT");
		this.socket.close();
	}
	
	/**
	 * @return The in buffer.
	 */
	public BufferedReader getIn() {
		return this.in;
	}
	
	/**
	 * @return True if the client is running, false otherwise.
	 */
	public boolean getIsRunning() {
		return this.isRunning;
	}
	
	/**
	 * @return The reference to the main instance.
	 */
	public Main getMainRef() {
		return this.mainRef;
	}
	
	/**
	 * @return The out buffer.
	 */
	public PrintWriter getOut() {
		return this.out;
	}
	
	/**
	 * Sends the name, key E and key N values of this client to the server.
	 * This should be called right after the client server connection is
	 * established.
	 * @param name The name of the client.
	 * @param keyE The encryption key of the client.
	 * @param keyN The n key value of the client.
	 */
	public void sendHandshake(String name, long keyE, long keyN) {
		this.out.println(name);
		this.out.println(keyE);
		this.out.println(keyN);
		
		System.err.println("% Completed Handshake with Server");
	}
	
	/**
	 * Receives the initial clients from the server. This should be called
	 * right after the handshake is complete.
	 * @param clients The list of clients to be populated with the clients.
	 * @throws IOException On a server connection error.
	 * @throws NumberFormatException On a failed server message conversion. 
	 */
	public void recieveInitialClients(ArrayList<ClientInfo> clients)
			throws NumberFormatException, IOException {
		int numConnections = Integer.valueOf(this.in.readLine());
		
		for (int i = 0; i < numConnections; ++i) {
			String id = this.in.readLine();
			String name = this.in.readLine();
			String kE = this.in.readLine();
			String kN = this.in.readLine();
			
			System.err.println("% Initial Client Connected: " +
					"ID: " + id + "; Name:" + name + 
					"; Key E: " + kE + "; Key N: " + kN + ", at index " +
					clients.size());
			
			ClientInfo client = new ClientInfo(
					id, name,
					Long.valueOf(kE), Long.valueOf(kN));
			clients.add(client);
		}

		System.out.println("% Initial Connections Made: " + numConnections);
		
		this.in.readLine();
	}
	
	/**
	 * Sets the isRunning field.
	 * @param isR Should the server run?
	 */
	public void setIsRunning(boolean isR) {
		this.isRunning = isR;
	}
	
	/**
	 * Sends the specified message to the specified ID.
	 * @param id The ID of the client.
	 * @param msg The message.
	 */
	public void send(String id, String msg) {
		this.out.println(id);
		this.out.println(msg);
	}
	
	/**
	 * Starts the listening thread.
	 */
	public void startListenThread() {
		if (this.listenThread == null) {
			this.listenThread = new Thread(new ClientListenThread(this));
			this.listenThread.start();
		}
	}
}
