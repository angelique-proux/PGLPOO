package main;

import business.*;
import util.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerConnection
{
	public static void main (String[] args) {
		Scanner scanner = new Scanner (System.in);
		System.out.println("Which mod do you want ? active/passive");
		String command = scanner.nextLine();
		if(command=="active") {
			
		} else {
			AbstractServer as = new Server();
			String ip = "localhost";
			as.connect(ip);
		}
	}
}
