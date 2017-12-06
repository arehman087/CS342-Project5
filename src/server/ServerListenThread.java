package server;

import java.io.IOException;
import java.net.Socket;

public class ServerListenThread implements Runnable {
	private Server server;
	
	/**
	 * Initializes the thread.
	 * @param server The server reference.
	 */
	public ServerListenThread(Server server) {
		this.server = server;
	}
	
	/**
	 * Listens for new connections and notifies server when a
	 * connection is established.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Socket client = this.server.getSocket().accept();
				this.server.addConnection(client);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
