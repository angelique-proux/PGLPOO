/*
 * Class' name : ServerConnection
 *
 * Description : Start the application JMusicHub for the server
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.main;

import musichub.business.*;
import musichub.util.*;
import musichub.util.server.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * ServerConnection Class Start the application JMusicHub for the server
 *
 * Version : 1.0
 *
 * @author	Félicia Ionascu
 */
public class ServerConnection
{
	/**
	 * Main of the JMusicHub server
	 *
	 * @param 	args arguments of the exec command
	 *
	 * @author	Félicia Ionascu and Steve Chauvreau-Manat and Gaël Lejeune
	 */
	public static void main (String[] args) {
		Scanner scanner = new Scanner (System.in);
		while(true) {
			System.out.println("Which mod do you want ? (active/passive/stop)");
			String command = scanner.nextLine();
			if(command.equals("active")) {
				JMusicHubModel model = new JMusicHubModel();
				JMusicHubController controller = new JMusicHubController(model);
			} else if(command.equals("passive")) {
				AbstractServer as = new Server();
				String ip = "localhost";
				as.connect(ip);
			} else {
				break;
			}
		}
	}
}
