package util;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

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

			System.out.println("\n\nWelcome in JMusicHub,");
			System.out.println("Connection to the server...\n\n");
			System.out.println((String) input.readObject());
			System.out.println("Reading library...\n\n");
			System.out.println("Type any command to begin using jMusicHub\nType \"h\" for help\n");
			Scanner scanner = new Scanner (System.in);
			while(true) {
					String command = scanner.nextLine();
					switch (command) {
							case "1" : // Show albums
								System.out.println((String) input.readObject());
								break;

							case "2" : // Show songs
								Scanner scanner = new Scanner (System.in);
								System.out.println((String) input.readObject());
								output.writeObject(scanner.nextLine());  /* Album title entered by the user */
								System.out.println((String) input.readObject());
								break;

							case "3" : // Show audiobooks
								System.out.println((String) input.readObject());
								break;

							case "4" : // Show playlists
								System.out.println((String) input.readObject());
								break;

							case "5" : // Select an album
								Scanner scanner = new Scanner (System.in);
								System.out.println((String) input.readObject());
								output.writeObject(scanner.nextLine());  /* Album title entered by the user */
								System.out.println((String) input.readObject());
								break;

							case "6" : // Select a playlist
								Scanner scanner = new Scanner (System.in);
								System.out.println((String) input.readObject());
								output.writeObject(scanner.nextLine());  /* Album title entered by the user */
								System.out.println((String) input.readObject());
								break;

							case "7" : // Select all the song of an artist
								//TODO
								// selectArtist7(util, sc);
								break;

							case "8" : // Select all the song of an author
								//TODO
								// selectAuthor8(util, sc);
								break;

							case "10" :// Quit the application
								System.out.println((String) input.readObject());
								System.exit(0);
								break;

							case "h" ://Display the help
								jMusicHub.help();
								break;

							default:
								System.out.println((String) input.readObject());
								break;
					}
			}

	  } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
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
