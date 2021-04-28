/*
 * Class' name : ClientConnection
 *
 * Description : TODO
 *
 * Version     : 1.0
 *
 * Date        : 10/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package musichub.main;

import musichub.util.*;

/**
 * TODO
 *
 * @version 1.0
 *
 * @author Félicia Ionascu
 */
public class ClientConnection {

	/**
 	 * Main function of the JMusicHub Client Program
 	 *
 	 * @author		Félicia Ionascu
 	 */
	public static void main (String[] args) {
		Client c1 = new Client();
		c1.connect("localhost");
	}
}
