import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.*; 

public class Server 
{ 
	private ServerSocket central;
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ArrayList<Integer> Test;
	
	private HashMap <String, Socket> cList;
	
	public Server() {
		cList = new HashMap<String, Socket>();
		try {
			this.central = new ServerSocket(4454);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("WAITING");
		while(true){
			this.acceptConnect();
			
		}
		
	}
	
	
	private void acceptConnect(){
		try {
			this.client = this.central.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.cList.put(this.client.getInetAddress().getHostName(), this.client);
		
		new CommThread(this.client).start();
	}
	
	
	public static void main(String[] args){
		Server S = new Server();
	}
	
} 

