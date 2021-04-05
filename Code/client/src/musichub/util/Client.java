package util;

import business.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.LinkedList;

public class Client {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;

	public void connect(String ip)
	{
		int port = 6666;
    try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
    	input = new ObjectInputStream(socket.getInputStream());

			System.out.println("\n\nWelcome in JMusicHub,");
			System.out.println("Connection to the server...\n\n");
			System.out.println((String) input.readObject());
			Scanner scan = new Scanner (System.in);

			PlayMusicFacade playMusicFacade = new PlayMusicFacade();

			while(true) {
					String command = scan.nextLine();
					output.writeObject(command);
					switch (command) {
							case "1" : // Show albums
								System.out.println((String) input.readObject());
								LinkedList<Album> albums = (LinkedList<Album>) input.readObject();
								for(int i=0;i<albums.size();i++) {
									System.out.println(i+"- "+albums.get(i)+"\n");
								}
								System.out.println((String) input.readObject());
								Album oneAlbum = albums.get(Integer.parseInt(scan.nextLine()));
								System.out.println(input.readObject());
								output.writeObject(scan.nextLine());
								if(((boolean) input.readObject())) {
									LinkedList<Song> song = oneAlbum.getSongs();
									for(int i=0;i<song.size();i++) {
										playMusicFacade.setContent(song.get(i).getContent());
										playMusicFacade.play();
									}
								} else {
									System.out.println(oneAlbum);
								}
								break;

							case "2" : // Show songs
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								System.out.println((String) input.readObject());
								break;

							case "3" : // Show audiobooks
								System.out.println((String) input.readObject());
								break;

							case "4" : // Show playlists
								System.out.println((String) input.readObject());
								LinkedList<Playlist> playlists = (LinkedList<Playlist>) input.readObject();
								for (int i = 0; i < playlists.size(); i++) {
									System.out.println(i+"- "+playlists.get(i) + "\n");
				        }
								System.out.println((String) input.readObject());
								Playlist onePlaylist = playlists.get(Integer.parseInt(scan.nextLine()));
								System.out.println(input.readObject());
								output.writeObject(scan.nextLine());
								if(((boolean) input.readObject())) {
									LinkedList<Audio> audio = onePlaylist.getAudios();
									for(int i=0;i<audio.size();i++) {
										playMusicFacade.setContent(audio.get(i).getContent());
										playMusicFacade.play();
									}
								} else {
									System.out.println(onePlaylist);
								}
								break;

							case "5" : // Select an album
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									Album album = (Album) input.readObject();
									System.out.println(album);
									System.out.println(input.readObject());
									output.writeObject(scan.nextLine());
									if(((boolean) input.readObject())) {
										LinkedList<Song> song = album.getSongs();
										for(int i=0;i<song.size();i++) {
											playMusicFacade.setContent(song.get(i).getContent());
											playMusicFacade.play();
										}
									} else {
										System.out.println(album);
									}
								}
								break;

							case "6" : // Select a playlist
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									Playlist playlist = (Playlist) input.readObject();
									System.out.println(playlist);
									System.out.println(input.readObject());
									output.writeObject(scan.nextLine());
									if(((boolean) input.readObject())) {
										LinkedList<Audio> audio = playlist.getAudios();
										for(int i=0;i<audio.size();i++) {
											playMusicFacade.setContent(audio.get(i).getContent());
											playMusicFacade.play();
										}
									} else {
										System.out.println(playlist);
									}
								}
								break;

							case "7" : // Select all the song of an artist
								//TODO
								// selectArtist7(util, sc);
								break;

							case "8" : // Select all the song of an author
								//TODO
								// selectAuthor8(util, sc);
								break;

							case "10" :// Quit the application
								System.out.println((String) input.readObject());
								System.exit(0);
								break;

							case "h" ://Display the help
								System.out.println((String) input.readObject());
								break;

							default:
								System.out.println((String) input.readObject());
								break;
					}
			}

	  } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
