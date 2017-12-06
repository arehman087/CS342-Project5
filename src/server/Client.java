package server;

import java.net.Socket;

public class Client {
	private Socket socket;
	private String name;
	private int id;
	
	private long keyD;
	private long keyN;
	
	/**
	 * Creates new client.
	 * @param sock The socket.
	 * @param name The name.
	 * @param kD The public key D value.
	 * @param kN The public key N value.
	 * @param id The ID of the client.
	 */
	public Client(Socket sock, String name, long kD, long kN, int id) {
		this.socket = sock;
		this.name = name;
		
		this.keyD = kD;
		this.keyN = kN;
		
		this.id = id;
	}
	
	/**
	 * @return The public key D value.
	 */
	public long getKeyD() {
		return this.keyD;
	}
	
	/**
	 * @return The public key N value.
	 */
	public long getKeyN() {
		return this.keyN;
	}
	
	/**
	 * @return The socket.
	 */
	public Socket getSocket() {
		return this.socket;
	}
	
	/**
	 * @return The name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return The ID.
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Returns a string representation of the Client.
	 */
	public String toString() {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Name: " + this.name);
		sB.append("; Key D: " + this.keyD);
		sB.append("; Key N: " + this.keyN);
		
		return sB.toString();
	}
}
