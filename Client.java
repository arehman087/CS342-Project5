import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
	private ArrayList<Integer> Test = new ArrayList<Integer>();
	private Socket cSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Client(){
		try{
			this.cSocket = new Socket("localHost",4454);
			this.setStream();
			while(true){
		    	this.chatBack();
	    	}
		} catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
			try {
				this.cSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void setStream() throws IOException {
		this.out = new ObjectOutputStream(cSocket.getOutputStream());
		this.in = new ObjectInputStream(cSocket.getInputStream());
	}
	
	private void chatBack() throws IOException, ClassNotFoundException{
		Test.add(1);
		out.writeObject(Test);
		ArrayList<Integer> Test2 = (ArrayList<Integer>) in.readObject();
		System.out.print(Test2);
	}
	
    public static void main(String[] args) throws IOException {
    	
    	Client c = new Client();
    	//cSocket.close();
    	
    }
}
