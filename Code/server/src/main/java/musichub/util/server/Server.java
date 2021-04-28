/*
 * Class' name : Server
 *
 * Description : Server start the connection with the client and open a thread for every client
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.server;

import musichub.util.logger.*;
import java.io.*;
import java.net.*;

/**
 * Server start the connection with the client and open a thread for every client
 *
 * Version : 1.0
 *
 * @see AbstractServer
 * @author Félicia Ionascu and Gaël Lejeune
 */
public class Server extends AbstractServer {

	/**
   * Server's ip
   */
	private String ip = "localhost";

	/**
   * TODO
   */
	private ServerSocket ss;

	public void connect(String ip) {
		try {
			//the server socket is defined only by a port (its IP is localhost)
			ss = new ServerSocket(6666);
			System.out.println("Server waiting for connection...");
			while (true) {
				Socket socket = ss.accept(); //establishes connection
				System.out.println("Connected as " + ip);
				ILogger logger = SingletonFileLogger.getInstance();
	      logger.write(Level.INFO, "Connected to " + ip);
				//create a new thread to handle client socket
				new ServerThread(socket,6666).start();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			//if IOException close the server socket
			if (ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}
