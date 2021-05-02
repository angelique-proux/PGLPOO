/*
 * Class' name : AbstractServer
 *
 * Description : Abstract definition of a server
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.server;

/**
 * Abstract definition of a server
 *
 * Version : 1.0
 *
 * @author	Félicia Ionascu
 */
public abstract class AbstractServer
{
	/**
	 * Start port listening, waiting for a client connection
	 *
	 * @param     ip Server's ip
	 *
	 * @author   	Félicia Ionascu
	 */
	public abstract void connect(String ip);
}
