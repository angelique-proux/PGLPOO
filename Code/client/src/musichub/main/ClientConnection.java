package main;

import util.*;

/**
 * TODO
 *
 * @version 1.0
 *
 * @author TODO
 */
public class ClientConnection {

	/**
 	 * Main function of the JMusicHub Client Program
 	 *
 	 * @author		TODO
 	 */
	public static void main (String[] args) {
		Client c1 = new Client();
		c1.connect("localhost");
	}
}
