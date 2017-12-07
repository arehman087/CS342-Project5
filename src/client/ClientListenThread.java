package client;

import java.io.IOException;

public class ClientListenThread implements Runnable {
	private Client client;
	
	private static final int RECV_ON_EXIT = -1;
	private static final int RECV_ON_CLIENT_CONN = 1;
	private static final int RECV_ON_CLIENT_DISC = 2;
	private static final int RECV_ON_CLIENT_MSG = 3; 
	
	/**
	 * Initializes the Client Listening Thread.
	 * @param client The client for which the listen thread is to run.
	 */
	public ClientListenThread(Client client) {
		this.client = client;
	}
	
	/**
	 * Continually listens for, and processes new messages.
	 */
	public void run() {
		try {
			while (this.client.getIsRunning()) {
				if (this.client.getIn().ready()) {
					String messageType = this.client.getIn().readLine();
					
					if (messageType.equals(RECV_ON_EXIT)) {
						this.client.getMainRef().onServerDisconnect();
					} else if (messageType.equals(RECV_ON_CLIENT_CONN)) {
						String id = this.client.getIn().readLine();
						String name = this.client.getIn().readLine();
						long e = Long.valueOf(this.client.getIn().readLine());
						long n = Long.valueOf(this.client.getIn().readLine());
						
						this.client.getMainRef().onAddNewConnection(
								id, name, e, n);
					} else if (messageType.equals(RECV_ON_CLIENT_DISC)) {
						String id = this.client.getIn().readLine();
						
						this.client.getMainRef().onDelOldConnection(id);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
