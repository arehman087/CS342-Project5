import java.net.*; 
import java.io.*; 

public class Server 
{ 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(10007); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10007."); 
         System.exit(1); 
        } 

    Socket clientSocket = null; 

    try { 
         System.out.println ("Waiting for Client");
         clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Accept failed."); 
         System.exit(1); 
        } 

//    ObjectOutputStream out = new ObjectOutputStream(
//                                     clientSocket.getOutputStream()); 
//    ObjectInputStream in = new ObjectInputStream( 
//                                     clientSocket.getInputStream()); 
    
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

    String pt3 = null;
    String pt4 = null;
    do{
    try {
         pt3 = (String) in.readLine();
        }
    catch (Exception ex)
        {
         System.out.println (ex.getMessage());
        }


    System.out.println ("Server recieved point: " + pt3 + " from Client");

    pt4 = "Server Received '" + pt3 +"'" ;
    System.out.println ("Server sending point: " + pt4 + " to Client");
    out.println(pt4); 
    out.flush();
    }while (!pt3.equals("DIE"));

    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
   } 
} 