import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CommThread extends Thread{
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ArrayList<Integer> Test;
	
	public CommThread(Socket c) {
		this.client = c;
	}
	
	public void run() {
		try{
			this.out = new ObjectOutputStream(this.client.getOutputStream());
			this.in = new ObjectInputStream(this.client.getInputStream());
			while (true){
				this.Test = (ArrayList<Integer>) this.in.readObject();
				System.out.println(Test);
				this.addArr();
				this.out.writeObject(Test);
			}
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void addArr(){
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		int n = reader.nextInt();
		this.Test.add(n);
	}
}
