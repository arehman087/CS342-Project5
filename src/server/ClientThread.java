package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientThread extends Thread {
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public ClientThread(Socket c) {
		this.client = c;
	}
	
	public void run() {

	}
	
	public void addArr(){

	}
}
