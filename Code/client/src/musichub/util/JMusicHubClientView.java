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
package util;

import business.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.LinkedList;

public class JMusicHubClientView {
  /**
	 * TODO
	 */
	private ControlMusic contMus;

  /**
	 * TODO
	 */
	private Socket socket;

  /**
	 * TODO
	 */
	private Scanner scan = new Scanner(System.in);

  /**
	 * Stream to send data to the server
	 */
	private ObjectOutputStream output;

	/**
	 * Stream to read data sent by the server
	 */
	private ObjectInputStream input;

  /**
   * JMusicHubPassiveView constructor
   *
   * @param     socket TODO
   * @param     port TODO
   *
   * @author      Steve Chauvreau-Manat
   */
  public JMusicHubClientView(Socket socket, String ip, int port) {
    this.socket = socket;
    this.contMus = new ControlMusicList(ip, port+1);
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
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
    * TODO
    * @exception   Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         Audio
    * @see         ControlMusicList
    * @author      Steve Chauvreau-Manat
    */
  private void audioPlayingOrInformation(LinkedList<Audio> audios) throws IOException, ClassNotFoundException {
    System.out.println((String) input.readObject());
    int choice = Integer.parseInt(this.scan.nextLine());
    if(choice<audios.size()&&(choice>=0)) {
      Audio oneAudio = audios.get(choice);
      output.writeObject(choice);
      System.out.println(input.readObject());
      output.writeObject(this.scan.nextLine());
      if(((boolean) input.readObject())) {
        contMus.playMusicList();
        do {
          System.out.println("Enter a command : (play/pause/stop/next/previous)");
          switch(this.scan.nextLine()) {
            case "pause":
              contMus.pauseMusic();
              break;
            case "play":
              contMus.restartMusic();
              break;
            case "stop":
              output.writeObject(0);
              contMus.stopMusic();
              break;
            case "next":
              output.writeObject(1);
              contMus.nextMusic();
              break;
            case "previous":
              output.writeObject(2);
              break;
            default:
              System.out.println("This is not a command");
              break;
          }
        } while(contMus.isFinished());
      } else {
        if(oneAudio instanceof Song) {
          System.out.println((Song) oneAudio);
        } else if(oneAudio instanceof AudioBook) {
          System.out.println((AudioBook) oneAudio);
        } else {
          System.out.println(oneAudio);
        }
      }
    }
  }

  /**
    * TODO
    * @exception   Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         Audio
    * @see         ControlMusicList
    * @author      Steve Chauvreau-Manat
    */
  private void musicPlayingOrInformation(LinkedList<Song> songs) throws IOException, ClassNotFoundException {
    System.out.println((String) input.readObject());
    int choice = Integer.parseInt(this.scan.nextLine());
    if(choice<songs.size()&&(choice>=0)) {
      Song song = songs.get(choice);
      output.writeObject(choice);
      System.out.println(input.readObject());
      output.writeObject(this.scan.nextLine());
      if(((boolean) input.readObject())) {
        do {
          System.out.println("Enter a command : (play/pause/stop\nnext/previous)");
          switch(this.scan.nextLine()) {
            case "pause":
              contMus.pauseMusic();
              break;
            case "play":
              contMus.restartMusic();
              break;
            case "stop":
              output.writeObject(0);
              contMus.stopMusic();
              break;
            case "next":
              output.writeObject(1);
              contMus.nextMusic();
              break;
            case "previous":
              output.writeObject(2);
              break;
            default:
              System.out.println("This is not a command");
              break;
          }
        } while(contMus.isFinished());
      } else {
        System.out.println(song+"\n");
      }
    }
  }

  /**
    * TODO
    * @exception   Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         Audio
    * @see         ControlMusicList
    * @author      Steve Chauvreau-Manat
    */
  private void audioBookPlayingOrInformation(LinkedList<AudioBook> audioBooks) throws IOException, ClassNotFoundException {
    System.out.println((String) input.readObject());
    int choice = Integer.parseInt(this.scan.nextLine());
    if(choice<audioBooks.size()&&(choice>=0)) {
      AudioBook audioBook = audioBooks.get(choice);
      output.writeObject(choice);
      System.out.println(input.readObject());
      output.writeObject(this.scan.nextLine());
      if(((boolean) input.readObject())) {
        do {
          System.out.println("Enter a command : (play/pause/stop\nnext/previous)");
          switch(this.scan.nextLine()) {
            case "pause":
              contMus.pauseMusic();
              break;
            case "play":
              contMus.restartMusic();
              break;
            case "stop":
              output.writeObject(0);
              contMus.stopMusic();
              break;
            case "next":
              output.writeObject(1);
              contMus.nextMusic();
              break;
            case "previous":
              output.writeObject(2);
              break;
            default:
              System.out.println("This is not a command");
              break;
          }
        } while(contMus.isFinished());
      } else {
        System.out.println(audioBook);
      }
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Album
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void albumPlayingOrInformation(LinkedList<Album> albums) throws IOException, ClassNotFoundException {
    System.out.println((String) input.readObject());
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
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Playlist
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void playlistPlayingOrInformation(LinkedList<Playlist> playlists) throws IOException, ClassNotFoundException {
    System.out.println((String) input.readObject());
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
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Audio
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllElements() throws IOException, ClassNotFoundException { //case 1
    System.out.println((String) input.readObject());
    LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
    for(int i=0;i<audios.size();i++) {
      System.out.println(i+"- "+audios.get(i)+"\n");
    }
    System.out.println("\n"+audios.size()+"- None");
    audioPlayingOrInformation(audios);
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Album
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllAlbums() throws IOException, ClassNotFoundException { //case 2
    System.out.println((String) input.readObject());
    LinkedList<Album> albums = (LinkedList<Album>) input.readObject();
    for(int i=0;i<albums.size();i++) {
     System.out.println(i+"- "+albums.get(i)+"\n");
    }
    System.out.println("\n"+albums.size()+"- None");
    albumPlayingOrInformation(albums);
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Playlist
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllPlaylists() throws IOException, ClassNotFoundException { //case 3
    System.out.println((String) input.readObject());
    LinkedList<Playlist> playlists = (LinkedList<Playlist>) input.readObject();
    for (int i = 0; i < playlists.size(); i++) {
      System.out.println(i+"- "+playlists.get(i) + "\n");
    }
    System.out.println("\n"+playlists.size()+"- None");
    playlistPlayingOrInformation(playlists);
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Album
    * @see					Song
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showSelectedAlbum() throws IOException, ClassNotFoundException { //case 4
    System.out.println((String) input.readObject());
    output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      Album album = (Album) input.readObject();
      System.out.println(input.readObject());
      output.writeObject(this.scan.nextLine());
      if(((boolean) input.readObject())) {
        LinkedList<Song> song = album.getSongs();
        //TODO
      } else {
        System.out.println(album);
      }
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Playlist
    * @see					Audio
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showSelectedPlaylist() throws IOException, ClassNotFoundException { //case 5
     System.out.println((String) input.readObject());
     output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
     if(input.readObject() instanceof String) {
       System.out.println((String) input.readObject());
     } else {
       Playlist playlist = (Playlist) input.readObject();
       System.out.println(input.readObject());
       output.writeObject(this.scan.nextLine());
       if(((boolean) input.readObject())) {
         LinkedList<Audio> audio = playlist.getAudios();
         //TODO
       } else {
         System.out.println(playlist);
       }
     }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Song
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllSelectedArtistSongs() throws IOException, ClassNotFoundException { //case 6
    System.out.println((String) input.readObject());
    output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<Song> artistSongs = (LinkedList<Song>) input.readObject();
      for(int i=0;i<artistSongs.size();i++) {
        System.out.println(i+"- "+artistSongs.get(i)+"\n");
      }
      System.out.println("\n"+artistSongs.size()+"- None\n");
      musicPlayingOrInformation(artistSongs);
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					AudioBook
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllSelectedAuthorAudioBooks() throws IOException, ClassNotFoundException { //case 7
    System.out.println((String) input.readObject());
    output.writeObject(this.scan.nextLine());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<AudioBook> authorAudioBooks = (LinkedList<AudioBook>) input.readObject();
      for(int i=0;i<authorAudioBooks.size();i++) {
        System.out.println(i+"- "+authorAudioBooks.get(i)+"\n");
      }
      System.out.println("\n"+authorAudioBooks.size()+"- None\n");
      audioBookPlayingOrInformation(authorAudioBooks);
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Album
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllAlbumsReleaseByDate() throws IOException, ClassNotFoundException { //case 8
    System.out.println((String) input.readObject());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<Album> albumsByDate = (LinkedList<Album>) input.readObject();
      for (int i = 0; i < albumsByDate.size(); i++) {
        System.out.println(i+"- "+albumsByDate.get(i) + "\n");
      }
      System.out.println("\n"+albumsByDate.size()+"- None\n");
      albumPlayingOrInformation(albumsByDate);
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Song
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllSongsSortedByGenre() throws IOException, ClassNotFoundException { //case 9
      System.out.println((String) input.readObject());
      output.writeObject(this.scan.nextLine());  /* Album title entered by the user */
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
        musicPlayingOrInformation(songs);
      }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					Song
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllArtists() throws IOException, ClassNotFoundException { //case 10
      System.out.println((String) input.readObject());
      if(input.readObject() instanceof String) {
        System.out.println((String) input.readObject());
      } else {
        LinkedList<String> artistsName = (LinkedList<String>) input.readObject();
        for(int i=0;i<artistsName.size();i++) {
          System.out.println("\n"+i+"- "+artistsName+"\n");
        }
        System.out.println("\n"+artistsName.size()+"- None");
        System.out.println((String) input.readObject());
        int numberArtistName = Integer.parseInt(this.scan.nextLine());
        output.writeObject(numberArtistName);
        if(numberArtistName==artistsName.size()) {
          System.out.println((String) input.readObject());
        } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
          System.out.println((String) input.readObject());
          LinkedList<Song> songsToDisplayArtist = (LinkedList<Song>) input.readObject();
          for (int i = 0; i < songsToDisplayArtist.size(); i++) {
            System.out.println("\n" + songsToDisplayArtist.get(i));
          }
          musicPlayingOrInformation(songsToDisplayArtist);
        } else {
          System.out.println((String) input.readObject());
        }
      }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see					AudioBook
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllAuthors() throws IOException, ClassNotFoundException { //case 11
    System.out.println((String) input.readObject());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<String> authorsName = (LinkedList<String>) input.readObject();
      for(int i=0;i<authorsName.size();i++) {
        System.out.println("\n"+i+"- "+authorsName+"\n");
      }
      System.out.println("\n"+authorsName.size()+"- None");
      System.out.println((String) input.readObject());
      int numberAuthorName = Integer.parseInt(this.scan.nextLine());
      output.writeObject(numberAuthorName);
      if(numberAuthorName==authorsName.size()) {
        System.out.println((String) input.readObject());
      } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
        System.out.println((String) input.readObject());
        LinkedList<AudioBook> booksToDisplayAuthor = (LinkedList<AudioBook>) input.readObject();
        for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
          System.out.println("\n" + booksToDisplayAuthor.get(i));
        }
        audioBookPlayingOrInformation(booksToDisplayAuthor);
      } else {
        System.out.println((String) input.readObject());
      }
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         	Genre
    * @see					Song
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllGenres() throws IOException, ClassNotFoundException { //case 12
    System.out.println((String) input.readObject());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<Genre> genres = (LinkedList<Genre>) input.readObject();
      for(int i=0;i<genres.size();i++) {
        System.out.println("\n"+i+"- "+genres+"\n");
      }
      System.out.println("\n"+genres.size()+"- None");
      System.out.println((String) input.readObject());
      int numberGenre = Integer.parseInt(this.scan.nextLine());
      output.writeObject(numberGenre);
      if(numberGenre==genres.size()) {
        System.out.println((String) input.readObject());
      } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
        System.out.println((String) input.readObject());
        LinkedList<Song> songsToDisplayGenre = (LinkedList<Song>) input.readObject();
        for (int i = 0; i < songsToDisplayGenre.size(); i++) {
          System.out.println("\n" + songsToDisplayGenre.get(i));
        }
        musicPlayingOrInformation(songsToDisplayGenre);
      } else {
        System.out.println((String) input.readObject());
      }
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         	Category
    * @see					AudioBook
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllCategories() throws IOException, ClassNotFoundException { //case 13
    System.out.println((String) input.readObject());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<Category> categories = (LinkedList<Category>) input.readObject();
      for(int i=0;i<categories.size();i++) {
        System.out.println("\n"+i+"- "+categories+"\n");
      }
      System.out.println("\n"+categories.size()+"- None");
      System.out.println((String) input.readObject());
      int numberCategory = Integer.parseInt(this.scan.nextLine());
      output.writeObject(numberCategory);
      if(numberCategory==categories.size()) {
        System.out.println((String) input.readObject());
      } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
        System.out.println((String) input.readObject());
        LinkedList<AudioBook> audioBooksToDisplayCategory = (LinkedList<AudioBook>) input.readObject();
        for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
          System.out.println("\n" + audioBooksToDisplayCategory.get(i));
        }
        audioBookPlayingOrInformation(audioBooksToDisplayCategory);
      } else {
        System.out.println((String) input.readObject());
      }
    }
  }

  /**
    * TODO
    * @exception   	Exception thrown if there is an error on the input and/or output streams or if the class of the received stream is not known
    * @see         	Language
    * @see					AudioBook
    * @see        	ControlMusicList
    * @author     	Steve Chauvreau-Manat
    */
  private void showAllLanguages() throws IOException, ClassNotFoundException { //case 14
    System.out.println((String) input.readObject());
    if(input.readObject() instanceof String) {
      System.out.println((String) input.readObject());
    } else {
      LinkedList<Language> languages = (LinkedList<Language>) input.readObject();
      for(int i=0;i<languages.size();i++) {
        System.out.println("\n"+i+"- "+languages+"\n");
      }
      System.out.println("\n"+languages.size()+"- None");
      System.out.println((String) input.readObject());
      int numberLanguage = Integer.parseInt(this.scan.nextLine());
      output.writeObject(numberLanguage);
      if(numberLanguage==languages.size()) {
        System.out.println((String) input.readObject());
      } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
        System.out.println((String) input.readObject());
        LinkedList<AudioBook> audioBooksToDisplayLanguage = (LinkedList<AudioBook>) input.readObject();
        for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
          System.out.println("\n" + audioBooksToDisplayLanguage.get(i));
        }
        audioBookPlayingOrInformation(audioBooksToDisplayLanguage);
      } else {
        System.out.println((String) input.readObject());
      }
    }
  }
}
