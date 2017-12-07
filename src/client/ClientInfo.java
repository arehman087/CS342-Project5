package client;

public class ClientInfo {
	private String clientID;
	private String clientName;
	private String clientKeyE;
	private String clientKeyN;
	
	/**
	 * 
	 * @param ID - takes in the ID of the client
	 * @param name - takes in the name of the client
	 * @param keyE - takes in the public key e
	 * @param keyN - takes in the public key n
	 */
	public ClientInfo(String ID, String name, String keyE, String keyN) {
		this.clientID = ID;
		this.clientName = name;
		this.clientKeyE = keyE;
		this.clientKeyN = keyN;
	}
	
	/**
	 * 
	 * @return - the client ID
	 */
	public String getID(){
		return this.clientID;
	}
	
	/**
	 * 
	 * @return - the clients name
	 */
	public String getName(){
		return this.clientName;
	}
	
	/**
	 * 
	 * @return - the clients key e
	 */
	public long getKeyE(){
		return Long.valueOf(this.clientKeyE);
	}
	
	/**
	 * 
	 * @return - the clients key e
	 */
	public long getKeyN(){
		return Long.valueOf(this.clientKeyN);
	}
}
