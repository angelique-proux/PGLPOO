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
							case "1": //Receive and shows all Elements
								System.out.println((String) input.readObject());
								LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
								for(int i=0;i<audios.size();i++) {
									System.out.println(i+"- "+audios.get(i)+"\n");
								}
								System.out.println((String) input.readObject());
								Audio oneAudio = audios.get(Integer.parseInt(scan.nextLine()));
								System.out.println(input.readObject());
								output.writeObject(scan.nextLine());
								if(((boolean) input.readObject())) {
									playMusicFacade.setContent(oneAudio.getContent());
									playMusicFacade.play();
								} else {
									System.out.println(oneAudio);
								}
								break;

							case "2" : //Receive and show all albums
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

							case "3" : //Receive and show playlists
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

							case "4" : //Receive and show a selected album
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									Album album = (Album) input.readObject();
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

							case "5" : //Receive and show a selected playlist
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									Playlist playlist = (Playlist) input.readObject();
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

							case "6" : //Receive and show all songs of the selected artist
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									LinkedList<Song> artistSongs = (LinkedList<Song>) input.readObject();
									for(int i=0;i<artistSongs.size();i++) {
										System.out.println(i+"- "+artistSongs.get(i)+"\n");
									}
									System.out.println((String) input.readObject());
									Song song = artistSongs.get(Integer.parseInt(scan.nextLine()));
									System.out.println(input.readObject());
									output.writeObject(scan.nextLine());
									if(((boolean) input.readObject())) {
										playMusicFacade.setContent(song.getContent());
										playMusicFacade.play();
									} else {
										System.out.println(song);
									}
								}
								break;

							case "7" : //Receive and show all seleted author's audiobooks
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									LinkedList<AudioBook> audiobooks = (LinkedList<AudioBook>) input.readObject();
									for(int i=0;i<audiobooks.size();i++) {
										System.out.println(i+"- "+audiobooks.get(i)+"\n");
									}
									System.out.println((String) input.readObject());
									AudioBook audiobook = audiobooks.get(Integer.parseInt(scan.nextLine()));
									System.out.println((String) input.readObject());
									output.writeObject(scan.nextLine());
									if(((boolean) input.readObject())) {
										playMusicFacade.setContent(audiobook.getContent());
										playMusicFacade.play();
									} else {
										System.out.println(audiobook);
									}
								}
								break;

							case "8" : //Receive and show albums release by date
								System.out.println((String) input.readObject());
								LinkedList<Album> albumsByDate = (LinkedList<Album>) input.readObject();
								for (int i = 0; i < albumsByDate.size(); i++) {
									System.out.println(i+"- "+albumsByDate.get(i) + "\n");
								}
								System.out.println((String) input.readObject());
								Album one_Album = albumsByDate.get(Integer.parseInt(scan.nextLine()));
								System.out.println(input.readObject());
								output.writeObject(scan.nextLine());
								if(((boolean) input.readObject())) {
									LinkedList<Song> songs = one_Album.getSongs();
									for(int i=0;i<songs.size();i++) {
										playMusicFacade.setContent(songs.get(i).getContent());
										playMusicFacade.play();
									}
								} else {
									System.out.println(one_Album);
								}
								break;

							case "9" : //Receive and show all songs sorted by genre
								System.out.println((String) input.readObject());
								output.writeObject(scan.nextLine());  /* Album title entered by the user */
								if(input.readObject() instanceof String) {
									System.out.println((String) input.readObject());
								} else {
									LinkedList<Song> songs = (LinkedList<Song>) input.readObject();
									System.out.println("---"+songs.get(0).getGenre()+"---\n");
									for(int i=0;i<songs.size();i++) {
										System.out.println(i+"- "+songs.get(i)+"\n");
										if(songs.get(i).getGenre()!=songs.get(i+1).getGenre()) {
											System.out.println("---"+songs.get(i+1).getGenre()+"---\n");
										}
									}
									System.out.println((String) input.readObject());
									Song song = songs.get(Integer.parseInt(scan.nextLine()));
									System.out.println((String) input.readObject());
									output.writeObject(scan.nextLine());
									if(((boolean) input.readObject())) {
										playMusicFacade.setContent(song.getContent());
										playMusicFacade.play();
									} else {
										System.out.println(song);
									}
								}
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
