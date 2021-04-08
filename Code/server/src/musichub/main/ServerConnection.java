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
