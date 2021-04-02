package main;

import business.*;
import util.*;
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
