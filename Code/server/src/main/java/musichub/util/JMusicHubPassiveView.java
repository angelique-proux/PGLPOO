/*
 * Class' name : JMusicHubPassiveView
 *
 * Description : JMusicHubPassiveView is the view used when the server is in passive mod
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import musichub.business.exceptions.*;
import musichub.business.*;
import musichub.util.musicplayer.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;


/**
 * Execution of the JMusicHub program and interaction with the client
 *
 * Version : 1.0
 *
 * @see View
 * @author Steve Chauvreau-Manat
 */
public class JMusicHubPassiveView implements View {

    /**
     * Contains all the methods used by the View
     * @see  JMusicHubController
     */
    private Controller controller;

    /**
     * Network interface to retrieve network's data
     */
    private ObjectInputStream input;

    /**
     * Network interface to send data over the network
     */
    private ObjectOutputStream output;

    /**
     * Class to establish a connection with the client
     */
    private Socket socket;

    /**
     * Contains all the methods used to play an audio
  	 * @see ControlMusicList
     */
    private ControlMusic contMus;

    /**
     * JMusicHubPassiveView constructor
     *
     * @param     controller TODO
     * @param     socket TODO
     * @param     port TODO
     *
     * @author      Steve Chauvreau-Manat
     */
    public JMusicHubPassiveView(Controller controller, Socket socket, int port) {
        this.controller = controller;
        this.socket = socket;
        this.contMus = new ControlMusicList(port+1);
    }

    /**
     * Send to the customer the result of all commands
     * @see         JMusicHubController
     * @author      Steve Chauvreau-Manat
     */
    public void display() {
        try {
            //create the streams that will handle the objects coming through the sockets
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
            //serialize and write the object to the stream
            output.writeObject("\nConnected to the server\nType any command to begin using jMusicHub\nType \"h\" for help\n");

            while(true) {
                output.writeObject("You are in the menu\n");
                //read the object received through the stream and deserialize it
                String command = (String) input.readObject();
                switch (command) {
                    case "1" :
                      sendAllElements();
                      break;

                    case "2" :
                      sendAllAlbums();
                      break;

                    case "3" :
                      sendAllPlaylists();
                      break;

                    case "4" :
                      sendSelectedAlbum();
                      break;

                    case "5" :
                      sendSelectedPlaylist();
                      break;

                    case "6" :
                      sendAllSelectedArtistSongs();
                      break;

                    case "7" :
                      sendAllSelectedAuthorAudioBooks();
                      break;

                    case "8" :
                      sendAllAlbumsReleaseByDate();
                      break;

                    case "9" :
                      sendAllSongsSortedByGenre();
                      break;

                    case "10":
                      sendAllArtists();
                      break;

                    case "11":
                      sendAllAuthors();
                      break;

                    case "12":
                      sendAllGenres();
                      break;

                    case "13":
                      sendAllCategories();
                      break;

                    case "14":
                      sendAllLanguages();
                      break;

                    case "q" :// Quit the application
                      output.writeObject("\t\t Thank you for you time, have a nice day!\n\t\t\t\t\tSigned by Gael LEJEUNE, Angelique PROUX,\n\t\t\t\t\tAntonin MORCRETTE et Steve CHAUVREAU-MANAT.\n\n\n");
                      System.out.println("Client disconnected");
                      break;

                    case "h" ://Display the help
                      output.writeObject(this.controller.helpPassive());
                      break;

                    default:
                      output.writeObject("\nWrong command, press \"h\" for help.");
                      break;
                }
                if(command.equals("q")) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                this.output.close();
                this.input.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    /**
     * Listen to an audio list
     *
     * @param       listSize linked list size
     * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         ControlMusicList
     * @author      Angelique Proux
     */
    private void listenToSomeMusic(int listSize) throws IOException, ClassNotFoundException {
        output.writeObject("Envoi de la musique à écouter");
        output.writeObject(listSize);
        for (int i = 0; i < listSize; i++) {
            output.writeObject(true);
            contMus.playMusicList(i);
            System.out.println("Musique " + i + " envoyée");
            String choix = (String) input.readObject();
            switch (choix) {
                case "next":
                    if (i != (listSize - 1)) {
                        output.writeObject("next");
                        contMus.stopMusic();
                        System.out.println("suivante");
                    } else {
                        output.writeObject("end");
                    }
                    break;
                case "previous":
                    contMus.stopMusic();
                    if (i >= 1) {
                        output.writeObject("previous");
                        //si il y a une musique avant, l'écouter
                        i -= 2;
                    } else {
                        // sinon, écouter celle d'après
                        output.writeObject("next");
                    }
                    System.out.println("précédente");
                    break;
                case "listen":
                    output.writeObject("listen");
                    System.out.println(input.readObject());
                    contMus.stopMusic();
                    break;
                default:
                    output.writeObject("end");
                    i = listSize - 1;
                    contMus.reset();
                    break;
            }
        }
        System.out.println("Fin");
    }

    /**
     * Send all data to listen to an audio list or choose one to either get more information or listen to it
     *
     * @param       audios audio list
     * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Audio
     * @see         ControlMusicList
     * @author      Steve Chauvreau-Manat
     */
    private void audioPlayingOrInformation(LinkedList<Audio> audios) throws IOException, ClassNotFoundException {
        output.writeObject("Which one would you like to hear? (Enter the number)\nEnter '-1' if you want to listen to everything");
        int number = ((int) input.readObject());
        if(number<audios.size()&&(number>=0)) {
            output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More information");
            if(((String) input.readObject()).equals("1")) {
                output.writeObject(true);
                this.contMus.addAudio(audios.get(number));
                listenToSomeMusic(1);
            } else {
                output.writeObject(false);
            }
        } else if(number==-1) {
            this.contMus.addAudios(audios);
            listenToSomeMusic(audios.size());
        }
    }

    /**
     * Send all data to listen to a song list or choose one to either get more information or listen to it
     *
     * @param       songs song list
     * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Song
     * @see         ControlMusicList
     * @author      Steve Chauvreau-Manat
     */
    private void musicPlayingOrInformation(LinkedList<Song> songs) throws IOException, ClassNotFoundException {
        output.writeObject("Which one would you like to hear? (Enter the number)\nEnter '-1' if you want to listen to everything");
        int number = ((int) input.readObject());
        if(number<songs.size()&&(number>=0)) {
            output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
            if(((String) input.readObject()).equals("1")) {
                output.writeObject(true);
                this.contMus.addAudio(songs.get(number));
                listenToSomeMusic(1);
            } else {
                output.writeObject(false);
            }
        } else if(number==-1) {
          this.contMus.addSongs(songs);
          listenToSomeMusic(songs.size());
        }
    }

    /**
     * Send all data to listen to an audio book list or choose one to either get more information or listen to it
     *
     * @param       audioBooks audio book list
     * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         AudioBook
     * @see         ControlMusicList
     * @author      Steve Chauvreau-Manat
     */
    private void audioBookPlayingOrInformation(LinkedList<AudioBook> audioBooks) throws IOException, ClassNotFoundException {
      output.writeObject("Which one would you like to hear? (Enter the number)\nEnter '-1' if you want to listen to everything");
      int number = ((int) input.readObject());
      if(number<audioBooks.size()&&(number>=0)) {
        output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
        if(((String) input.readObject()).equals("1")) {
          output.writeObject(true);
          this.contMus.addAudio(audioBooks.get(number));
          listenToSomeMusic(1);
        } else {
          output.writeObject(false);
        }
      } else if(number==-1) {
        this.contMus.addAudioBooks(audioBooks);
        listenToSomeMusic(audioBooks.size());
      }
    }

    /**
      * Send all data to choose then listen to an album or choose one to either get more information or choose one album's song to listen to it
      *
      * @param       albums album list
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Album
      * @author      Steve Chauvreau-Manat
      */
    private void albumPlayingOrInformation(LinkedList<Album> albums) throws IOException, ClassNotFoundException {
      output.writeObject("Which one would you like to hear? (Enter the number)");
      int number = ((int) input.readObject());
      if(number<albums.size()&&(number>=0)) {
        output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
        if(((String) input.readObject()).equals("1")) {
          output.writeObject(true);
          musicPlayingOrInformation(albums.get(number).getSongs());
        } else {
          output.writeObject(false);
        }
      }
    }

    /**
      * Send all data to choose then listen to a playlist or choose one to either get more information or choose one playlist's song to listen to it
      *
      * @param       playlists playlist list
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Playlist
      * @author      Steve Chauvreau-Manat
      */
    private void playlistPlayingOrInformation(LinkedList<Playlist> playlists) throws IOException, ClassNotFoundException {
      output.writeObject("Which one would you like to hear? (Enter the number)");
      int number = ((int) input.readObject());
      if(number<playlists.size()&&(number>=0)) {
        output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
        if(((String) input.readObject()).equals("1")) {
          output.writeObject(true);
          audioPlayingOrInformation(playlists.get(number).getAudios());
        } else {
          output.writeObject(false);
        }
      }
    }

    /**
      * Send all elements then use the audioPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Audio
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllElements() throws IOException, ClassNotFoundException { //case 1
        output.writeObject("\t\tSongs and AudioBooks sorted by alphabetical order:\n");
        LinkedList<Audio> audios = this.controller.getElements();
        if(audios!=null) {
          output.writeObject(true);
          output.writeObject(audios);
          audioPlayingOrInformation(audios);
        } else {
          output.writeObject(false);
          output.writeObject("\nNo elements in the database");
        }
    }

    /**
      * Send all albums then use the albumPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Album
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllAlbums() throws IOException, ClassNotFoundException { //case 2
        output.writeObject("\t\tAlbums sorted by alphabetical order:\n");
        LinkedList<Album> albums = this.controller.getAlbums();
        if(albums!=null) {
          output.writeObject(true);
          output.writeObject(albums);
          albumPlayingOrInformation(albums);
        } else {
          output.writeObject(false);
          output.writeObject("\nNo albums in the database");
        }
    }

    /**
      * Send all playlists then use the playlistPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Playlist
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllPlaylists() throws IOException, ClassNotFoundException { //case 3
        output.writeObject("\t\t Playlist names sorted by alphabetical order:\nExisting playlists :\n\n");
        LinkedList<Playlist> playlists = this.controller.getPlaylists();
        if(playlists!=null) {
          output.writeObject(true);
          output.writeObject(playlists);
          playlistPlayingOrInformation(playlists);
        } else {
          output.writeObject(false);
          output.writeObject("\nNo playlist in te database");
        }
    }

    /**
      * Send an album selected by name, then use the listenToSomeMusic method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Album
      * @see         JMusicHubController
      * @see         ControlMusicList
      * @author      Steve Chauvreau-Manat
      */
    private void sendSelectedAlbum() throws IOException, ClassNotFoundException { //case 4
      output.writeObject("\nName of the album to display :\n");
      String albumTitle = (String) input.readObject();  /* Album title entered by the user */
      Album album = this.controller.getSpecificAlbum(albumTitle);
      if(album==null) {
        output.writeObject(true);
        output.writeObject("No album found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(album);
        output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
        if(((String) input.readObject()).equals("1")) {
          output.writeObject(true);
          this.contMus.addSongs(album.getSongs());
          listenToSomeMusic(album.getSongs().size());
        } else {
          output.writeObject(false);
        }
      }
    }

    /**
      * Send a playlist selected by name, then use the listenToSomeMusic method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Playlist
      * @see         JMusicHubController
      * @see         ControlMusicList
      * @author      Steve Chauvreau-Manat
      */
    private void sendSelectedPlaylist() throws IOException, ClassNotFoundException { //case 5
      output.writeObject("\nName of the playlist :\n");
      String name = (String) input.readObject();
      Playlist playlist = this.controller.getSpecificPlaylist(name);
      if (playlist==null) {
        output.writeObject(true);
        output.writeObject("No playlist found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(playlist);
        output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
        if(((String) input.readObject()).equals("1")) {
          output.writeObject(true);
          this.contMus.addAudios(playlist.getAudios());
          listenToSomeMusic(playlist.getAudios().size());
        } else {
          output.writeObject(false);
        }
      }
    }

    /**
      * Send all songs of a selected artist then use the musicPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Song
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllSelectedArtistSongs() throws IOException, ClassNotFoundException { //case 6
      output.writeObject("\t\tArtist's song names sorted by alphabetical order:\nArtist's name :\n\n");
      LinkedList<Song> artistSongs = this.controller.getSongsByArtist((String) input.readObject());
      if(artistSongs==null) {
        output.writeObject(true);
        output.writeObject("No artist with this name or no artist's song found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(artistSongs);
        musicPlayingOrInformation(artistSongs);
      }
    }

    /**
      * Send all songs of a selected author then use the audioBookPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         AudioBook
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllSelectedAuthorAudioBooks() throws IOException, ClassNotFoundException { //case 7
      output.writeObject("\t\t AudioBook titles sorted by them author:\nAuthor's name :\n\n");
      LinkedList<AudioBook> authorAudioBooks = this.controller.getAudioBooksByAuthor((String) input.readObject());
      if(authorAudioBooks==null) {
        output.writeObject(true);
        output.writeObject("No author with this name or no author's audio book found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(authorAudioBooks);
        audioBookPlayingOrInformation(authorAudioBooks);
      }
    }

    /**
      * Send all albums release by date then use the albumPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Album
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllAlbumsReleaseByDate() throws IOException, ClassNotFoundException { //case 8
      try {
        output.writeObject("\t\tAlbum titles sorted by them date:\nAlbums ordered by release date :\n\n");
        //TODO (Exception handling)
        LinkedList<Album> albumsRealeaseByDate = this.controller.getAlbumByReleaseDate();
        if(albumsRealeaseByDate==null) {
          output.writeObject(true);
          output.writeObject("No album in the database or not found");
        } else {
          output.writeObject(false);
          output.writeObject(albumsRealeaseByDate);
          albumPlayingOrInformation(albumsRealeaseByDate);
        }
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    /**
      * Send all songs sorted by genre then use the musicPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Song
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllSongsSortedByGenre() throws IOException, ClassNotFoundException { //case 9
      output.writeObject("\t\t Song titles sorted by them genre:\nName of the album to display :");
      LinkedList<Song> songs = this.controller.getSongByGenre((String) input.readObject()); //Album title entered by the user
      if(songs==null) {
        output.writeObject(true);
        output.writeObject("\nNo album found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(songs);
        musicPlayingOrInformation(songs);
      }
    }

    /**
      * Send all existing artists then use the musicPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Song
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllArtists() throws IOException, ClassNotFoundException { //case 10
      output.writeObject("\t\tAll artists' name :");
      LinkedList<String> artistsName = this.controller.getArtists();
      if(artistsName==null) {
        output.writeObject(true);
        output.writeObject("\nNo artist found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(artistsName);
        output.writeObject("\n\nEnter a name's-artist number :");
        int numberArtistName = (int) input.readObject();
        if(numberArtistName==artistsName.size()) {
          output.writeObject("Return to the main menu");
        } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
          output.writeObject("\n\t\tAll the "+artistsName.get(numberArtistName)+"\'s songs :\n\n");
          LinkedList<Song> artistSongs = this.controller.getSongsByArtist(artistsName.get(numberArtistName));
          output.writeObject(artistSongs);
          musicPlayingOrInformation(artistSongs);
        } else {
          output.writeObject("\nInvalid number");
        }
      }
    }

    /**
      * Send all existing authors then use the audioBookPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         AudioBook
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllAuthors() throws IOException, ClassNotFoundException { //case 11
      output.writeObject("\t\tAll author's name :");
      LinkedList<String> authorsName = this.controller.getAuthors();
      if(authorsName==null) {
        output.writeObject(true);
        output.writeObject("\nNo authors found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(authorsName);
        output.writeObject("\n\nEnter a name's-author number :");
        int numberAuthorName = (int) input.readObject();
        if(numberAuthorName==authorsName.size()) {
          output.writeObject("Return to the main menu");
        } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
          output.writeObject("\n\t\tAll the "+authorsName.get(numberAuthorName)+"\'s audio books :\n\n");
          LinkedList<AudioBook> artistAudioBooks = this.controller.getAudioBooksByAuthor(authorsName.get(numberAuthorName));
          output.writeObject(artistAudioBooks);
          audioBookPlayingOrInformation(artistAudioBooks);
        } else {
          output.writeObject("\nInvalid number");
        }
      }
    }

    /**
      * Send all existing genres then use the musicPlayingOrInformation method
      *
      * @exception   IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Genre
      * @see         Song
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllGenres() throws IOException, ClassNotFoundException { //case 12
      output.writeObject("\t\tAll genres :");
      LinkedList<Genre> genres = this.controller.getGenres();
      if(genres==null) {
        output.writeObject(true);
        output.writeObject("\nNo genres found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(genres);
        output.writeObject("\n\nEnter a genre number :");
        int numberGenre = (int) input.readObject();
        if(numberGenre==genres.size()) {
          output.writeObject("\nReturn to the main menu\n");
        } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
          output.writeObject("\n\t\tAll the "+genres.get(numberGenre)+"\'s songs :\n\n");
          LinkedList<Song> songsToDisplayGenre = this.controller.getSongsByGenre(genres.get(numberGenre));
          output.writeObject(songsToDisplayGenre);
          musicPlayingOrInformation(songsToDisplayGenre);
        } else {
          output.writeObject("\nInvalid number");
        }
      }
    }

    /**
     * Send all existing categories then use the audioBookPlayingOrInformation method
     *
     * @exception   IOException thrown if there is an error on the input and/or output streams
     * @exception   ClassNotFoundException thrown if the class of the received stream is not known
     *
     * @see         Category
     * @see         AudioBook
     * @see         JMusicHubController
     * @author      Steve Chauvreau-Manat
     */
    private void sendAllCategories() throws IOException, ClassNotFoundException { //case 13
      output.writeObject("\t\tAll categories :");
      LinkedList<Category> categories = this.controller.getCategories();
      if(categories==null) {
        output.writeObject(true);
        output.writeObject("\nNo categories found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(categories);
        output.writeObject("\n\nEnter a category number :");
        int numberCategory = (int) input.readObject();
        if(numberCategory==categories.size()) {
          output.writeObject("\nReturn to the main menu\n");
        } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
          output.writeObject("\n\t\tAll the "+categories.get(numberCategory)+"\'s audio books :\n\n");
          LinkedList<AudioBook> audioBooksToDisplayCategory = this.controller.getAudioBooksByCategory(categories.get(numberCategory));
          output.writeObject(audioBooksToDisplayCategory);
          audioBookPlayingOrInformation(audioBooksToDisplayCategory);
        } else {
          output.writeObject("\nInvalid number");
        }
      }
    }

    /**
      * Send all existing languages then use the audioBookPlayingOrInformation method
      *
      * @exception   IOException IOException thrown if there is an error on the input and/or output streams
      * @exception   ClassNotFoundException thrown if the class of the received stream is not known
      *
      * @see         Language
      * @see         AudioBook
      * @see         JMusicHubController
      * @author      Steve Chauvreau-Manat
      */
    private void sendAllLanguages() throws IOException, ClassNotFoundException { //case 14
      output.writeObject("\t\tAll languages :");
      LinkedList<Language> languages = this.controller.getLanguages();
      if(languages==null) {
        output.writeObject(true);
        output.writeObject("\nNo languages found.\n");
      } else {
        output.writeObject(false);
        output.writeObject(languages);
        output.writeObject("\n\nEnter a language number :");
        int numberLanguage = (int) input.readObject();
        if(numberLanguage==languages.size()) {
          output.writeObject("\nReturn to the main menu\n");
        } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
          output.writeObject("\n\t\tAll the "+languages.get(numberLanguage)+"\'s audio books :\n\n");
          LinkedList<AudioBook> audioBooksToDisplayLanguage = this.controller.getAudioBooksByLanguage(languages.get(numberLanguage));
          output.writeObject(audioBooksToDisplayLanguage);
          audioBookPlayingOrInformation(audioBooksToDisplayLanguage);
        } else {
          output.writeObject("\nInvalid number");
        }
      }
    }
}
