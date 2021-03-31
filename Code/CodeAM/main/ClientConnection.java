/*
* Name of class : ClientConnection
*
* Description   : Class which manages the execution of the application at the client side
*
* Date          : 03/01/2021
*
* Copyright     : Angélique & Gaël & Steve & Antonin
*/

package main;

//our packages
import business.*;
import util.*;

public class ClientConnection
{
	public static void main (String[] args)
	{
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
}
