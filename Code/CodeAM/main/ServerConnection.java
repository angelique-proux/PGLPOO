/*
* Name of class : ServerConnection
*
* Description   : Class which manages the execution of the application at the server side
*
* Date          : 03/01/2021
*
* Copyright     : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
*/

package main;

//our packages
import business.*;
import util.*;

// Packages from java
import java.io.*;
import java.net.*;

public class ServerConnection
{
	public static void main (String[] args) {
		AbstractServer as = new Server();
		String ip = "localhost";
		as.connect(ip);
	}
}
