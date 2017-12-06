package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientConnectionThread implements Runnable {
	private Server server;
	private Client client;
	
	/**
	 * Initializes the connection thread.
	 * @param server The server to be used for the client connection.
	 * @param client The client to be used for the client connection.
	 */
	public ClientConnectionThread(Server server, Client client) {
		this.server = server;
		this.client = client;
	}
	
	/**
	 * Continually waits for new messages from the client, and sends out any
	 * queued up messages to the client.
	 */
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					this.client.getSocket().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		while (true) {
			try {
				if (in.ready()) {
					String recv = in.readLine();
					String msg = in.readLine();
					
					if (Integer.valueOf(recv) == -1) {
						this.server.removeConnection(this.client);
						return;
					} else {
						this.server.processMessage(this.client, recv, msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
