package util;

import business.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * TODO
 *
 * @version 1.0
 *
 * @author TODO
 */
public class Client {

	/**
	 * TODO
	 */
	private ObjectOutputStream output;

	/**
	 * TODO
	 */
	private ObjectInputStream input;

	/**
	 * TODO
	 */
	private Socket socket;
	private SingletonMusic music;

	/**
	 * description de la méthode.
	 *
	 * @param	ip TODO
	 *
	 * @author	TODO
	 */
	public void connect(String ip) {
		int port = 6666;
		try {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			System.out.println("\n\nWelcome in JMusicHub,");
			System.out.println("Connection to the server...\n\n");
			System.out.println((String) input.readObject());
			Scanner scan = new Scanner(System.in);
			int choice;

			while(true) {
<<<<<<< HEAD
					String command = scan.nextLine();
					output.writeObject(command);
					switch(command) {
							case "1": //Receive and shows all Elements
								System.out.println((String) input.readObject());
								LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
								for(int i=0;i<audios.size();i++) {
									System.out.println(i+"- "+audios.get(i)+"\n");
								}
								System.out.println((String) input.readObject());
								choice = Integer.parseInt(scan.nextLine());
								Audio oneAudio = audios.get(choice);
								output.writeObject(choice);
								System.out.println(input.readObject());
								output.writeObject(scan.nextLine());
								if(((boolean) input.readObject())) {
									music = SingletonMusic.getInstance(ip,6668,this.socket);
									//music.startMusic();
									do {
										System.out.println("\nPlease wait a few moments before the music starts\n");
										System.out.println("Enter a command : (play/pause/stop)");
										switch(scan.nextLine()) {
											case "pause":
												music.pauseMusic();
												break;
											case "play":
												music.restartMusic();
												break;
											case "stop":
												music.stopMusic();
												break;
											default:
												System.out.println("This is not a command");
												break;
										}
									} while(music.isRunning());
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
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
											//music = SingletonMusic.getInstance();
											//music.playMusic(ip,6668);
										}
									} else {
										System.out.println(album);
									}
								}
=======
				String command = scan.nextLine();
				output.writeObject(command);
				switch(command) {
					case "1": //Receive and shows all Elements
					System.out.println((String) input.readObject());
					LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
					for(int i=0;i<audios.size();i++) {
						System.out.println(i+"- "+audios.get(i)+"\n");
					}
					System.out.println((String) input.readObject());
					choice = Integer.parseInt(scan.nextLine());
					Audio oneAudio = audios.get(choice);
					output.writeObject(choice);
					System.out.println(input.readObject());
					output.writeObject(scan.nextLine());
					if(((boolean) input.readObject())) {
						music = SingletonMusic.getInstance();
						music.startMusic(ip,6668,this.socket);
						do {
							System.out.println("Enter a command : (play/pause/stop)");
							switch(scan.nextLine()) {
								case "pause":
								music.pauseMusic();
								break;
								case "play":
								music.restartMusic();
								break;
								case "stop":
								music.stopMusic();
								break;
								default:
								System.out.println("This is not a command");
>>>>>>> c47376083fa319fdf7c81d0b3fe403e04ac2d905
								break;
							}
						} while(music.isRunning());
					} else {
						System.out.println(oneAudio);
					}
					break;

<<<<<<< HEAD
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
											//music = SingletonMusic.getInstance();
											//music.playMusic(ip,6668);
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
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
										//music = SingletonMusic.getInstance();
										//music.playMusic(ip,6668);
									} else {
										System.out.println(song);
									}
								}
								break;

							case "10": //Show all Artists
								System.out.println((String) input.readObject());
								LinkedList<String> artistsName = (LinkedList<String>) input.readObject();
								for(int i=0;i<artistsName.size();i++) {
									System.out.println("\n"+i+"- "+artistsName.get(i)+"\n");
								}
								System.out.println("\n"+artistsName.size()+"- None");
								System.out.println((String) input.readObject());
								int numberArtistName = Integer.parseInt(scan.nextLine());
								output.writeObject(numberArtistName);
								if(numberArtistName==artistsName.size()) {
									break;
								} else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)){
									System.out.println((String) input.readObject());
									LinkedList<Song> songsToDisplayArtist = (LinkedList<Song>) input.readObject();
									for (int i = 0; i < songsToDisplayArtist.size(); i++) {
											System.out.println("\n" + songsToDisplayArtist.get(i));
									}
									break;
								} else {
									System.out.println((String) input.readObject());
									break;
								}

							case "11": //Show all Authors
								System.out.println((String) input.readObject());
								LinkedList<String> authorsName = (LinkedList<String>) input.readObject();
								for(int i=0;i<authorsName.size();i++) {
									System.out.println("\n"+i+"- "+authorsName.get(i)+"\n");
								}
								System.out.println("\n"+authorsName.size()+"- None");
								System.out.println((String) input.readObject());
								int numberAuthorName = Integer.parseInt(scan.nextLine());
								output.writeObject(numberAuthorName);
								if(numberAuthorName==authorsName.size()) {
									break;
								} else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)){
									System.out.println((String) input.readObject());
									LinkedList<AudioBook> booksToDisplayAuthor = (LinkedList<AudioBook>) input.readObject();
									for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
											System.out.println("\n" + booksToDisplayAuthor.get(i));
									}
									break;
								} else {
									System.out.println((String) input.readObject());
									break;
								}

							case "12": //Show all Genres
								System.out.println((String) input.readObject());
								LinkedList<Genre> genres = (LinkedList<Genre>) input.readObject();
								for(int i=0;i<genres.size();i++) {
									System.out.println("\n"+i+"- "+genres.get(i)+"\n");
								}
								System.out.println("\n"+genres.size()+"- None");
								System.out.println((String) input.readObject());
								int numberGenre = Integer.parseInt(scan.nextLine());
								output.writeObject(numberGenre);
								if(numberGenre==genres.size()) {
									break;
								} else if((numberGenre<genres.size())&&(numberGenre>=0)){
									System.out.println((String) input.readObject());
									LinkedList<Song> songsToDisplayGenre = (LinkedList<Song>) input.readObject();
									for (int i = 0; i < songsToDisplayGenre.size(); i++) {
											System.out.println("\n" + songsToDisplayGenre.get(i));
									}
									break;
								} else {
									System.out.println((String) input.readObject());
									break;
								}

							case "13": //Show all Categories
								System.out.println((String) input.readObject());
								LinkedList<Category> categories = (LinkedList<Category>) input.readObject();
								for(int i=0;i<categories.size();i++) {
									System.out.println("\n"+i+"- "+categories.get(i)+"\n");
								}
								System.out.println("\n"+categories.size()+"- None");
								System.out.println((String) input.readObject());
								int numberCategory = Integer.parseInt(scan.nextLine());
								output.writeObject(numberCategory);
								if(numberCategory==categories.size()) {
									break;
								} else if((numberCategory<categories.size())&&(numberCategory>=0)){
									System.out.println((String) input.readObject());
									LinkedList<AudioBook> audioBooksToDisplayCategory = (LinkedList<AudioBook>) input.readObject();
									for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
											System.out.println("\n" + audioBooksToDisplayCategory.get(i));
									}
									break;
								} else {
									System.out.println((String) input.readObject());
									break;
								}

							case "14": //Show all Languages
								System.out.println((String) input.readObject());
								LinkedList<Language> languages = (LinkedList<Language>) input.readObject();
								for(int i=0;i<languages.size();i++) {
									System.out.println("\n"+i+"- "+languages.get(i)+"\n");
								}
								System.out.println("\n"+languages.size()+"- None");
								System.out.println((String) input.readObject());
								int numberLanguage = Integer.parseInt(scan.nextLine());
								output.writeObject(numberLanguage);
								if(numberLanguage==languages.size()) {
									break;
								} else if((numberLanguage<languages.size())&&(numberLanguage>=0)){
									System.out.println((String) input.readObject());
									LinkedList<AudioBook> audioBooksToDisplayLanguage = (LinkedList<AudioBook>) input.readObject();
									for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
											System.out.println("\n" + audioBooksToDisplayLanguage.get(i));
									}
									break;
								} else {
									System.out.println((String) input.readObject());
									break;
								}
=======
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
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
								music = SingletonMusic.getInstance();
								//music.playMusic(ip,6668);
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
								music = SingletonMusic.getInstance();
								//music.playMusic(ip,6668);
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
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
							music = SingletonMusic.getInstance();
							//music.playMusic(ip,6668);
						} else {
							System.out.println(song);
						}
					}
					break;

					case "10": //Show all Artists
					System.out.println((String) input.readObject());
					LinkedList<String> artistsName = (LinkedList<String>) input.readObject();
					for(int i=0;i<artistsName.size();i++) {
						System.out.println("\n"+i+"- "+artistsName+"\n");
					}
					System.out.println("\n"+artistsName.size()+"- None");
					System.out.println((String) input.readObject());
					int numberArtistName = Integer.parseInt(scan.nextLine());
					output.writeObject(numberArtistName);
					if(numberArtistName==artistsName.size()) {
						break;
					} else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
						System.out.println((String) input.readObject());
						LinkedList<Song> songsToDisplayArtist = (LinkedList<Song>) input.readObject();
						for (int i = 0; i < songsToDisplayArtist.size(); i++) {
							System.out.println("\n" + songsToDisplayArtist.get(i));
						}
						break;
					} else {
						System.out.println((String) input.readObject());
						break;
					}

					case "11": //Show all Authors
					System.out.println((String) input.readObject());
					LinkedList<String> authorsName = (LinkedList<String>) input.readObject();
					for(int i=0;i<authorsName.size();i++) {
						System.out.println("\n"+i+"- "+authorsName+"\n");
					}
					System.out.println("\n"+authorsName.size()+"- None");
					System.out.println((String) input.readObject());
					int numberAuthorName = Integer.parseInt(scan.nextLine());
					output.writeObject(numberAuthorName);
					if(numberAuthorName==authorsName.size()) {
						break;
					} else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
						System.out.println((String) input.readObject());
						LinkedList<AudioBook> booksToDisplayAuthor = (LinkedList<AudioBook>) input.readObject();
						for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
							System.out.println("\n" + booksToDisplayAuthor.get(i));
						}
						break;
					} else {
						System.out.println((String) input.readObject());
						break;
					}
>>>>>>> c47376083fa319fdf7c81d0b3fe403e04ac2d905

					case "12": //Show all Genres
					System.out.println((String) input.readObject());
					LinkedList<Genre> genres = (LinkedList<Genre>) input.readObject();
					for(int i=0;i<genres.size();i++) {
						System.out.println("\n"+i+"- "+genres+"\n");
					}
					System.out.println("\n"+genres.size()+"- None");
					System.out.println((String) input.readObject());
					int numberGenre = Integer.parseInt(scan.nextLine());
					output.writeObject(numberGenre);
					if(numberGenre==genres.size()) {
						break;
					} else if((numberGenre<genres.size())&&(numberGenre>=0)) {
						System.out.println((String) input.readObject());
						LinkedList<Song> songsToDisplayGenre = (LinkedList<Song>) input.readObject();
						for (int i = 0; i < songsToDisplayGenre.size(); i++) {
							System.out.println("\n" + songsToDisplayGenre.get(i));
						}
						break;
					} else {
						System.out.println((String) input.readObject());
						break;
					}

					case "13": //Show all Categories
					System.out.println((String) input.readObject());
					LinkedList<Category> categories = (LinkedList<Category>) input.readObject();
					for(int i=0;i<categories.size();i++) {
						System.out.println("\n"+i+"- "+categories+"\n");
					}
					System.out.println("\n"+categories.size()+"- None");
					System.out.println((String) input.readObject());
					int numberCategory = Integer.parseInt(scan.nextLine());
					output.writeObject(numberCategory);
					if(numberCategory==categories.size()) {
						break;
					} else if((numberCategory<categories.size())&&(numberCategory>=0)) {
						System.out.println((String) input.readObject());
						LinkedList<AudioBook> audioBooksToDisplayCategory = (LinkedList<AudioBook>) input.readObject();
						for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
							System.out.println("\n" + audioBooksToDisplayCategory.get(i));
						}
						break;
					} else {
						System.out.println((String) input.readObject());
						break;
					}

					case "14": //Show all Languages
					System.out.println((String) input.readObject());
					LinkedList<Language> languages = (LinkedList<Language>) input.readObject();
					for(int i=0;i<languages.size();i++) {
						System.out.println("\n"+i+"- "+languages+"\n");
					}
					System.out.println("\n"+languages.size()+"- None");
					System.out.println((String) input.readObject());
					int numberLanguage = Integer.parseInt(scan.nextLine());
					output.writeObject(numberLanguage);
					if(numberLanguage==languages.size()) {
						break;
					} else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
						System.out.println((String) input.readObject());
						LinkedList<AudioBook> audioBooksToDisplayLanguage = (LinkedList<AudioBook>) input.readObject();
						for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
							System.out.println("\n" + audioBooksToDisplayLanguage.get(i));
						}
						break;
					} else {
						System.out.println((String) input.readObject());
						break;
					}

					case "q" :// Quit the application
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
