package server;

import java.net.Socket;

public class Client {
	private Socket socket;
	private String name;

	private long keyE;
	private long keyN;
	
	/**
	 * Creates new client.
	 * @param sock The socket.
	 * @param name The name.
	 * @param kD The public key E value.
	 * @param kN The public key N value.
	 * @param id The ID of the client.
	 */
	public Client(Socket sock, String name, long kE, long kN) {
		this.socket = sock;
		this.name = name;
		
		this.keyE = kE;
		this.keyN = kN;
	}
	
	/**
	 * @return The public key E value.
	 */
	public long getKeyE() {
		return this.keyE;
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
	 * Returns a string representation of the Client.
	 */
	public String toString() {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Name: " + this.name);
		sB.append("; Key E: " + this.keyE);
		sB.append("; Key N: " + this.keyN);
		
		return sB.toString();
	}
}
