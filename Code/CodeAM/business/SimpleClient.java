/*
* Name of class : SimpleClient
*
* Description   : Class which manages the client thread
*
* Date          : 03/01/2021
*
* Copyright     : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
*/

package business;

// Packages from java
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Collections;
import java.io.*;
import java.net.*;

public class SimpleClient {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;

	public void connect(String ip)
	{
		int port = 6666;
    try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
      input = new ObjectInputStream(socket.getInputStream());

			String textToSend = new String("send me the song info!");
			System.out.println("text sent to the server: " + textToSend);
			output.writeObject(textToSend);		//serialize and write the String to the stream

			Song music = (Song) input.readObject();	//deserialize and read the Song object from the stream
			System.out.println("Received music id: " + music.getID() + " and music title:" + music.getTitle() + " from server");

			Scanner sc = new Scanner(System.in);
			String selection;
			boolean stop;
			do {
				selection = sc.nextLine();
				output.writeObject(selection);
				stop = (boolean) input.readObject();
			}while(stop);

		} catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
