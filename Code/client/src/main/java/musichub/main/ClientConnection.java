/*
 * Class' name : ClientConnection
 *
 * Description : Start the application JMusicHub
 *
 * Version     : 1.0
 *
 * Date        : 10/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.main;

import musichub.util.*;

/**
 * Start the application JMusicHub
 *
 * @version 1.0
 *
 * @author Félicia Ionascu
 */
public class ClientConnection {

	/**
 	 * Main function of the JMusicHub Client Program
 	 *
 	 * @param 		args arguments of the exec command
 	 *
 	 * @author		Félicia Ionascu
 	 */
	public static void main (String[] args) {
		Client c1 = new Client();
		c1.connect("localhost");
	}
}
