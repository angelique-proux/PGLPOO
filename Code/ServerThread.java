/*
* Name of class : ServerThread
*
* Description   : Class which manages the server's thread for the client
*
* Date          : 03/01/2021
*
* Copyright     : Angélique & Gaël & Steve & Antonin
*/

package business;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
  private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

  public ServerThread(Socket socket) {
      this.socket = socket;
  }

  public void run() {
    try {
		     //create the streams that will handle the objects coming through the sockets
         input = new ObjectInputStream(socket.getInputStream());
		     output = new ObjectOutputStream(socket.getOutputStream());
         String text = (String) input.readObject();  //read the object received through the stream and deserialize it
         System.out.println("server received a text:" + text);

         String command = (String) input.readObject(); //Read the command choose by the client

         //Use a method wich add song, etc... thanks to command

         //Send data
         Song music = new Song(...);
         output.writeObject(music);		//serialize and write the Song object to the stream
    } catch (IOException ex) {
          System.out.println("Server exception: " + ex.getMessage());
          ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
          System.out.println("Server exception: " + ex.getMessage());
          ex.printStackTrace();
    } finally {
      try {
        output.close();
        input.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
		  }
    }
  }
}
