/*
 * Class' names : JMusicHubClientView
 *
 * Description 	: Display all command result
 *
 * Version      : 1.0
 *
 * Date         : 13/04/2021
 *
 * Copyright    : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */
package musichub.util;

import musichub.business.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Execution of the JMusicHub program and interaction with the server
 *
 * Version : 1.0
 *
 * @see View
 * @author  Steve Chauvreau-Manat
 */
public class JMusicHubClientView implements View {
		/**
		 * Contains all the methods used to play an audio
		 * @see ControlMusicList
		 */
    private ControlMusic contMus;

		/**
		 * Class to establish a connection with the server
		 */
    private Socket socket;

		/**
		 * Scan the keyboard to receive information from the user
		 */
    private Scanner scan = new Scanner(System.in);

    /**
     * Network interface to send data over the network
     */
    private ObjectOutputStream output;

    /**
     * Network interface to retrieve network's data
     */
    private ObjectInputStream input;

    /**
     * JMusicHubPassiveView constructor
     *
     * @param     socket TODO
     * @param     ip TODO
     * @param     port TODO
     *
     * @author      Steve Chauvreau-Manat
     */
    public JMusicHubClientView(Socket socket, String ip, int port) {
        this.socket = socket;
        this.contMus = new ControlMusicList(ip, port+1);
    }

		/**
	   * Displays the result of all commands
	   *
	   * @author	Steve Chauvreau-Manat
	   */
    public void display() {
        try {
            //create the streams that will handle the objects coming and going through the sockets
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());

            System.out.println("\n\nWelcome in JMusicHub,");
            System.out.println("Connection to the server...\n\n");
            System.out.println((String) input.readObject());
            int choice;

            while(true) {
                System.out.println((String) input.readObject());
                String command = this.scan.nextLine();
                output.writeObject(command);
                switch(command) {
                    case "1":
                        showAllElements();
                        break;

                    case "2" :
                        showAllAlbums();
                        break;

                    case "3" :
                        showAllPlaylists();
                        break;

                    case "4" :
                        showSelectedAlbum();
                        break;

                    case "5" :
                        showSelectedPlaylist();
                        break;

                    case "6" :
                        showAllSelectedArtistSongs();
                        break;

                    case "7" :
                        showAllSelectedAuthorAudioBooks();
                        break;

                    case "8" :
                        showAllAlbumsReleaseByDate();
                        break;

                    case "9" :
                        showAllSongsSortedByGenre();
                        break;

                    case "10":
                        showAllArtists();
                        break;

                    case "11":
                        showAllAuthors();
                        break;

                    case "12":
                        showAllGenres();
                        break;

                    case "13":
                        showAllCategories();
                        break;

                    case "14":
                        showAllLanguages();
                        break;

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
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Listen to an audio list
		 *
		 * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
		 *
		 * @see					ControlMusicList
     * @author      Angélique Proux
     */
    private void listenToSomeMusic() throws IOException, ClassNotFoundException {
				String choix;
				String next;
				System.out.println((String) input.readObject());
        int size = (int) input.readObject();
        int i=0;
        while (i<size) {
            if((boolean) input.readObject()){
                System.out.println("Que voulez-vous faire ?\n (previous/listen/next/end)");
                contMus.playMusicList();
                next = scan.nextLine();
                output.writeObject(next);
                choix = (String) input.readObject();
                switch (choix) {
                    case "next":
                        contMus.stopMusic();
                        System.out.println("nouvelle musique");
                        i++;
                        break;
                    case "previous":
                        i--;
                        contMus.stopMusic();
                        System.out.println("musique précédente");
                        break;
                    case "end":
                        contMus.stopMusic();
                        System.out.println("Fin de l'écoute.");
                        i=size;
                        break;
                    case "listen":
                        while (!contMus.isFinished()){
                            System.out.println("Enter a command : (play/pause/stop)");
                            switch(scan.nextLine()) {
                                case "pause":
                                    contMus.pauseMusic();
                                    break;
                                case "play":
                                    contMus.restartMusic();
                                    break;
                                case "stop":
                                    contMus.stopMusic();
                                    break;
                                default:
                                    System.out.println("This is not a command");
                                    break;
                            }
                        }
                        i++;
                        System.out.println("Ici la musique finit.");
                        output.writeObject("Fin de la lecture sur le client.");
                        break;
                }

            }
        }
        System.out.println("Fin de la liste.");
    }

		/**
	   * Allows you to listen to an audio list or choose one to either get more information or listen to it
	   *
	   * @param 				audios audio list
	   * @exception   	IOException thrown if there is an error on the input and/or output streams
	   * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
	 	 *
	   * @see         Audio
	   * @see         ControlMusicList
	   * @author      Steve Chauvreau-Manat
	   */
    private void audioPlayingOrInformation(LinkedList<Audio> audios) throws IOException, ClassNotFoundException {
        System.out.println((String) input.readObject());
        try {
            int choice = Integer.parseInt(this.scan.nextLine());
            output.writeObject(choice);
            if(choice<audios.size()&&(choice>=0)) {
                Audio oneAudio = audios.get(choice);
                System.out.println(input.readObject());
                output.writeObject(this.scan.nextLine());
                if(((boolean) input.readObject())) {
                    System.out.println("You are listening to : "+oneAudio.getTitle());
                    listenToSomeMusic();
                } else {
                    if(oneAudio instanceof Song) {
                        System.out.println((Song) oneAudio);
                    } else if(oneAudio instanceof AudioBook) {
                        System.out.println((AudioBook) oneAudio);
                    } else {
                        System.out.println(oneAudio+"\n");
                    }
                }
            } else if(choice==-1) {
                System.out.println("You will listen to all the audios.");
                listenToSomeMusic();
            } else {
                System.out.println("Wrong number, you go back to the menu.");
            }
        } catch(NumberFormatException nfe) {
            System.out.println("Wrong expression, you go back to the menu.");
            output.writeObject(audios.size());
        }
    }

		/**
	   * Allows you to listen to a song list or choose one to either get more information or listen to it
		 *
		 * @param 				songs song list
	   * @exception   	IOException thrown if there is an error on the input and/or output streams
	   * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
	 	 *
	   * @see         Audio
	   * @see         ControlMusicList
	   * @author      Steve Chauvreau-Manat
	   */
    private void musicPlayingOrInformation(LinkedList<Song> songs) throws IOException, ClassNotFoundException {
      System.out.println((String) input.readObject());
			try {
	      int choice = Integer.parseInt(this.scan.nextLine());
        output.writeObject(choice);
	      if(choice<songs.size()&&(choice>=0)) {
	      	Song song = songs.get(choice);
	        System.out.println(input.readObject());
	        output.writeObject(this.scan.nextLine());
	        if(((boolean) input.readObject())) {
						System.out.println("You are listening to : "+song.getTitle());
	          listenToSomeMusic();
	        } else {
	          System.out.println(song+"\n");
	        }
	      } else if(choice==-1) {
						System.out.println("You will listen to all the audios.");
						listenToSomeMusic();
				} else {
						System.out.println("Wrong number, you go back to the menu.");
				}
			} catch(NumberFormatException nfe) {
        output.writeObject(songs.size());
        System.out.println("Wrong expression, you go back to the menu.");
      }
    }

		/**
	   * Allows you to listen to an audio book list or choose one to either get more information or listen to it
		 *
		 * @param 				audioBooks audio book list
	   * @exception   	IOException thrown if there is an error on the input and/or output streams
	   * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
	 	 *
	   * @see         Audio
	   * @see         ControlMusicList
	   * @author      Steve Chauvreau-Manat
	   */
    private void audioBookPlayingOrInformation(LinkedList<AudioBook> audioBooks) throws IOException, ClassNotFoundException {
      System.out.println((String) input.readObject());
			try {
        int choice = Integer.parseInt(this.scan.nextLine());
        output.writeObject(choice);
        if(choice<audioBooks.size()&&(choice>=0)) {
          AudioBook audioBook = audioBooks.get(choice);
          output.writeObject(choice);
          System.out.println(input.readObject());
          output.writeObject(this.scan.nextLine());
          if(((boolean) input.readObject())) {
						System.out.println("You are listening to : " + audioBook.getTitle());
						listenToSomeMusic();
          } else {
            System.out.println(audioBook+"\n");
          }
        } else if(choice==-1) {
						System.out.println("You will listen to all the audios.");
						listenToSomeMusic();
				} else {
						System.out.println("Wrong number, you go back to the menu.");
				}
			} catch(NumberFormatException nfe) {
          System.out.println("Wrong expression, you go back to the menu.");
          output.writeObject(audioBooks.size());
      }
    }

    /**
     * Allows you to choose then listen to an album or choose one to either get more information or choose one album's song to listen to it
     *
     * @param 				albums album list
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Album
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void albumPlayingOrInformation(LinkedList<Album> albums) throws IOException, ClassNotFoundException {
      System.out.println((String) input.readObject());
      try {
        int number = Integer.parseInt(this.scan.nextLine());
        output.writeObject(number);
        if(number<albums.size()&&(number>=0)) {
          System.out.println((String) input.readObject());
          output.writeObject(this.scan.nextLine());
          if((boolean) input.readObject()) {
            musicPlayingOrInformation(albums.get(number).getSongs());
          } else {
            System.out.println(albums.get(number));
          }
        }
      } catch(NumberFormatException nfe) {
        output.writeObject(albums.size());
        System.out.println("Wrong expression");
      }
    }

    /**
     * Allows you to choose then listen to a playlist or choose one to either get more information or choose one playlist's song to listen to it
     *
     * @param 				playlists playlist list
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Playlist
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void playlistPlayingOrInformation(LinkedList<Playlist> playlists) throws IOException, ClassNotFoundException {
      System.out.println((String) input.readObject());
      try {
        int number = Integer.parseInt(this.scan.nextLine());
        output.writeObject(number);
        if(number<playlists.size()&&(number>=0)) {
          System.out.println((String) input.readObject());
          output.writeObject(this.scan.nextLine());
          if((boolean) input.readObject()) {
            audioPlayingOrInformation(playlists.get(number).getAudios());
          } else {
            System.out.println(playlists.get(number));
          }
        }
      } catch(NumberFormatException nfe) {
        output.writeObject(playlists.size());
        System.out.println("Wrong expression");
      }
    }

    /**
     * Receive and show all elements then use the audioPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Audio
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllElements() throws IOException, ClassNotFoundException { //case 1
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
        for(int i=0;i<audios.size();i++) {
          System.out.println(i+"- "+audios.get(i)+"\n");
        }
        System.out.println("\n"+audios.size()+"- None");
        audioPlayingOrInformation(audios);
      } else {
        System.out.println((String) input.readObject());
      }
    }

    /**
     * Receive and show all albums then use the albumPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Album
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllAlbums() throws IOException, ClassNotFoundException { //case 2
        System.out.println((String) input.readObject());
        if((boolean) input.readObject()) {
          LinkedList<Album> albums = (LinkedList<Album>) input.readObject();
          for(int i=0;i<albums.size();i++) {
            System.out.println(i+"- "+albums.get(i)+"\n");
          }
          System.out.println("\n"+albums.size()+"- None");
          albumPlayingOrInformation(albums);
        } else {
          System.out.println((String) input.readObject());
        }
    }

    /**
     * Receive and s all playlists then use the playlistPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Playlist
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllPlaylists() throws IOException, ClassNotFoundException { //case 3
        System.out.println((String) input.readObject());
        if((boolean) input.readObject()) {
          LinkedList<Playlist> playlists = (LinkedList<Playlist>) input.readObject();
          for (int i = 0; i < playlists.size(); i++) {
            System.out.println(i+"- "+playlists.get(i) + "\n");
          }
          System.out.println("\n"+playlists.size()+"- None");
          playlistPlayingOrInformation(playlists);
        } else {
          System.out.println((String) input.readObject());
        }
    }

    /**
     * Receive and show an album selected by name, then use the listenToSomeMusic method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Album
     * @see					Song
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showSelectedAlbum() throws IOException, ClassNotFoundException { //case 4
        System.out.println((String) input.readObject());
        output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
        if((boolean) input.readObject()) {
            System.out.println((String) input.readObject());
        } else {
            Album album = (Album) input.readObject();
            System.out.println(input.readObject());
            output.writeObject(this.scan.nextLine());
            if(((boolean) input.readObject())) {
                listenToSomeMusic();
            } else {
                System.out.println(album);
            }
        }
    }

    /**
     * Receive and show a playlist selected by name, then use the listenToSomeMusic method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Playlist
     * @see					Audio
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showSelectedPlaylist() throws IOException, ClassNotFoundException { //case 5
        System.out.println((String) input.readObject());
        output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
        if((boolean) input.readObject()) {
            System.out.println((String) input.readObject());
        } else {
            Playlist playlist = (Playlist) input.readObject();
            System.out.println(input.readObject());
            output.writeObject(this.scan.nextLine());
            if(((boolean) input.readObject())) {
                listenToSomeMusic();
            } else {
                System.out.println(playlist);
            }
        }
    }

    /**
     * Receive and show all songs of a selected artist then use the musicPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Song
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllSelectedArtistSongs() throws IOException, ClassNotFoundException { //case 6
        System.out.println((String) input.readObject());
        output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
        if((boolean) input.readObject()) {
            System.out.println((String) input.readObject());
        } else {
            LinkedList<Song> artistSongs = (LinkedList<Song>) input.readObject();
            for(int i=0;i<artistSongs.size();i++) {
                System.out.println(i+"- "+artistSongs.get(i)+"\n");
            }
            System.out.println(artistSongs.size()+"- None\n");
            musicPlayingOrInformation(artistSongs);
        }
    }

    /**
     * Receive and show all songs of a selected author then use the audioBookPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					AudioBook
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllSelectedAuthorAudioBooks() throws IOException, ClassNotFoundException { //case 7
        System.out.println((String) input.readObject());
        output.writeObject(this.scan.nextLine());
        if((boolean) input.readObject()) {
            System.out.println((String) input.readObject());
        } else {
            LinkedList<AudioBook> authorAudioBooks = (LinkedList<AudioBook>) input.readObject();
            for(int i=0;i<authorAudioBooks.size();i++) {
                System.out.println(i+"- "+authorAudioBooks.get(i)+"\n");
            }
            System.out.println(authorAudioBooks.size()+"- None\n");
            audioBookPlayingOrInformation(authorAudioBooks);
        }
    }

    /**
     * Receive and show all albums release by date then use the albumPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Album
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllAlbumsReleaseByDate() throws IOException, ClassNotFoundException { //case 8
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<Album> albumsByDate = (LinkedList<Album>) input.readObject();
        for (int i = 0; i < albumsByDate.size(); i++) {
          System.out.println(i+"- "+albumsByDate.get(i) + "\n");
        }
        System.out.println(albumsByDate.size()+"- None\n");
        albumPlayingOrInformation(albumsByDate);
      }
    }

    /**
     * Receive and show all songs sorted by genre then use the musicPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Song
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllSongsSortedByGenre() throws IOException, ClassNotFoundException { //case 9
      System.out.println((String) input.readObject());
      output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<Song> songs = (LinkedList<Song>) input.readObject();
        System.out.println("---"+songs.get(0).getGenre()+"---\n");
        for(int i=0;i<songs.size();i++) {
          System.out.println(i+"- "+songs.get(i)+"\n");
          if(i<songs.size()-1) {
            if(songs.get(i).getGenre()!=songs.get(i+1).getGenre()) {
              System.out.println("---"+songs.get(i+1).getGenre()+"---\n");
            }
          }
        }
        musicPlayingOrInformation(songs);
      }
    }

    /**
     * Receive and show all existing artists then use the musicPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					Song
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllArtists() throws IOException, ClassNotFoundException { //case 10
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<String> artistsName = (LinkedList<String>) input.readObject();
        for(int i=0;i<artistsName.size();i++) {
          System.out.println("\n"+i+"- "+artistsName.get(i));
        }
        System.out.println("\n"+artistsName.size()+"- None");
        System.out.println((String) input.readObject());
        try {
          int numberArtistName = Integer.parseInt(this.scan.nextLine());
          output.writeObject(numberArtistName);
          if(numberArtistName==artistsName.size()) {
            System.out.println((String) input.readObject());
          } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
            System.out.println((String) input.readObject());
            LinkedList<Song> songsToDisplayArtist = (LinkedList<Song>) input.readObject();
            for(int i = 0; i < songsToDisplayArtist.size(); i++) {
              System.out.println("\n"+i+"- "+ songsToDisplayArtist.get(i));
            }
            System.out.println("\n"+songsToDisplayArtist.size()+"- None");
            musicPlayingOrInformation(songsToDisplayArtist);
          } else {
            System.out.println((String) input.readObject());
          }
        } catch(NumberFormatException nfe) {
          output.writeObject(artistsName.size());
          System.out.println((String) input.readObject());
        }
      }
    }

    /**
     * Receive and show all existing authors then use the audioBookPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see					AudioBook
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllAuthors() throws IOException, ClassNotFoundException { //case 11
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<String> authorsName = (LinkedList<String>) input.readObject();
        for(int i=0;i<authorsName.size();i++) {
          System.out.println("\n"+i+"- "+authorsName.get(i));
        }
        System.out.println("\n"+authorsName.size()+"- None");
        System.out.println((String) input.readObject());
        try {
          int numberAuthorName = Integer.parseInt(this.scan.nextLine());
          output.writeObject(numberAuthorName);
          if(numberAuthorName==authorsName.size()) {
            System.out.println((String) input.readObject());
          } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
            System.out.println((String) input.readObject());
            LinkedList<AudioBook> booksToDisplayAuthor = (LinkedList<AudioBook>) input.readObject();
            for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
              System.out.println("\n"+i+"- "+ booksToDisplayAuthor.get(i));
            }
            System.out.println("\n"+booksToDisplayAuthor.size()+"- None");
            audioBookPlayingOrInformation(booksToDisplayAuthor);
          } else {
            System.out.println((String) input.readObject());
          }
        } catch(NumberFormatException nfe) {
          output.writeObject(authorsName.size());
          System.out.println((String) input.readObject());
        }
      }
    }

    /**
     * Receive and show all existing genres then use the musicPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Genre
     * @see					Song
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllGenres() throws IOException, ClassNotFoundException { //case 12
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<Genre> genres = (LinkedList<Genre>) input.readObject();
        for(int i=0;i<genres.size();i++) {
          System.out.println("\n"+i+"- "+genres.get(i));
        }
        System.out.println("\n"+genres.size()+"- None");
        System.out.println((String) input.readObject());
        try {
          int numberGenre = Integer.parseInt(this.scan.nextLine());
          output.writeObject(numberGenre);
          if(numberGenre==genres.size()) {
            System.out.println((String) input.readObject());
          } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
            System.out.println((String) input.readObject());
            LinkedList<Song> songsToDisplayGenre = (LinkedList<Song>) input.readObject();
            for (int i = 0; i < songsToDisplayGenre.size(); i++) {
              System.out.println("\n"+i+"- "+songsToDisplayGenre.get(i));
            }
            System.out.println("\n"+songsToDisplayGenre.size()+"- None");
            musicPlayingOrInformation(songsToDisplayGenre);
          } else {
            System.out.println((String) input.readObject());
          }
        } catch(NumberFormatException nfe) {
          output.writeObject(genres.size());
          System.out.println((String) input.readObject());
        }
      }
    }

    /**
     * Receive and show all existing categories then use the audioBookPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Category
     * @see					AudioBook
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllCategories() throws IOException, ClassNotFoundException { //case 13
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<Category> categories = (LinkedList<Category>) input.readObject();
        for(int i=0;i<categories.size();i++) {
          System.out.println("\n"+i+"- "+categories.get(i));
        }
        System.out.println("\n"+categories.size()+"- None");
        System.out.println((String) input.readObject());
        try {
          int numberCategory = Integer.parseInt(this.scan.nextLine());
          output.writeObject(numberCategory);
          if(numberCategory==categories.size()) {
            System.out.println((String) input.readObject());
          } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
            System.out.println((String) input.readObject());
            LinkedList<AudioBook> audioBooksToDisplayCategory = (LinkedList<AudioBook>) input.readObject();
            for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
              System.out.println("\n"+i+"- "+audioBooksToDisplayCategory.get(i));
            }
            System.out.println("\n"+audioBooksToDisplayCategory.size()+"- None");
            audioBookPlayingOrInformation(audioBooksToDisplayCategory);
          } else {
            System.out.println((String) input.readObject());
          }
        } catch(NumberFormatException nfe) {
          output.writeObject(categories.size());
          System.out.println((String) input.readObject());
        }
      }
    }

    /**
     * Receive and show all existing languages then use the audioBookPlayingOrInformation method
     *
     * @exception   	IOException thrown if there is an error on the input and/or output streams
     * @exception   	ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Language
     * @see					AudioBook
     * @see        	ControlMusicList
     * @author     	Steve Chauvreau-Manat
     */
    private void showAllLanguages() throws IOException, ClassNotFoundException { //case 14
      System.out.println((String) input.readObject());
      if((boolean) input.readObject()) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<Language> languages = (LinkedList<Language>) input.readObject();
        for(int i=0;i<languages.size();i++) {
          System.out.println("\n"+i+"- "+languages.get(i));
        }
        System.out.println("\n"+languages.size()+"- None");
        System.out.println((String) input.readObject());
        try {
          int numberLanguage = Integer.parseInt(this.scan.nextLine());
          output.writeObject(numberLanguage);
          if(numberLanguage==languages.size()) {
            System.out.println((String) input.readObject());
          } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
            System.out.println((String) input.readObject());
            LinkedList<AudioBook> audioBooksToDisplayLanguage = (LinkedList<AudioBook>) input.readObject();
            for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
              System.out.println("\n"+i+"- "+audioBooksToDisplayLanguage.get(i));
            }
            System.out.println("\n"+audioBooksToDisplayLanguage.size()+"- None");
            audioBookPlayingOrInformation(audioBooksToDisplayLanguage);
          } else {
            System.out.println((String) input.readObject());
          }
        } catch(NumberFormatException nfe) {
          output.writeObject(languages.size());
          System.out.println((String) input.readObject());
        }
      }
    }
}
