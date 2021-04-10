package util;

import util.logger.*;
import java.io.*;
import java.net.*;


/**
 * Server TODO
 *
 * Version : 1.0
 *
 * @see AbstractServer
 * @author TODO
 */
public class Server extends AbstractServer {

	/**
     * TODO
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
				// create a new thread to handle client socket
				new ServerThread(socket).start();
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
