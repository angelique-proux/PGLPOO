/*
 * Class' names : Client
 *
 * Description 	: Thread to manage the server/client software
 *
 * Version      : 1.0
 *
 * Date         : 13/04/2021
 *
 * Copyright    : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Class to establish a connection with the client
 *
 * @version 1.0
 *
 * @author Félicia Ionascu and Steve Chauvreau-Manat
 */
public class Client {
	/**
	 * Connection socket between the server and the client.
	 */
	private Socket socket;

	/**
	 * Connects the client to the server and initialize the console view
	 *
	 * @param	ip Server's ip
	 *
	 * @author	Félicia Ionascu and Steve Chauvreau-Manat
	 */
	public void connect(String ip) {
		try {
			int port = 6666;
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			this.socket = new Socket(ip, port);
			View clientView = new JMusicHubClientView(this.socket,ip,port);
			clientView.display();
		} catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
