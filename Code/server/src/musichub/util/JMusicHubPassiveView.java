/*
 * Class' name : JMusicHubPassiveView
 *
 * Description : JMusicHubPassiveView is the view used when the server is in passive mod
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package util;

import business.*;
import business.exceptions.*;
import business.*;
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
 * JMusicHubPassiveView is the view used when the server is in passive mod
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
    private JMusicHubController controller;

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
     * JMusicHubPassiveView constructor
     *
     * @param     controller
     * @param     socket TODO
     *
     * @author      Steve Chauvreau-Manat
     */
    public JMusicHubPassiveView(JMusicHubController controller, Socket socket) {
        this.controller = controller;
        this.socket = socket;
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

            output.writeObject("\nConnected to the server\nType any command to begin using jMusicHub\nType \"h\" for help\n"); //serialize and write the object to the stream

            while(true) {
                String command = (String) input.readObject();  //read the object received through the stream and deserialize it
                System.out.println(command);
                switch (command) {
                    case "1" : //Send all Elements
                    output.writeObject("\t\tSongs and AudioBooks sorted by alphabetical order:\n");
                    LinkedList<Audio> audios = controller.getElements();
                    output.writeObject(audios);
                    output.writeObject("Which one would you like to hear? (Enter the number)");

                    Audio audio = audios.get((int) input.readObject());
                    output.writeObject("What do you want? (Enter the number)\n1- Listen the playlist\n2- More informtion");
                    if(((String) input.readObject()).equals("1")) {
                        output.writeObject(true);
                        SingletonMusic music = SingletonMusic.getInstance(audio.getContent(),6668,socket);
                        //music.startMusic();
                        while((boolean) input.readObject());
                        music.stopMusic();
                    } else {
                        output.writeObject(false);
                    }
                    break;

                    case "2" : //Send all Albums
                    output.writeObject("\t\tAlbums sorted by alphabetical order:\n");
                    output.writeObject(controller.getAlbums());
                    output.writeObject("Which one would you like to hear? (Enter the number)");
                    output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
                    if(((String) input.readObject()).equals("1")) {
                        output.writeObject(true);
                    } else {
                        output.writeObject(false);
                    }
                    break;

                    case "3" : // Send all playlists
                    output.writeObject("\t\t Playlist names sorted by alphabetical order:\nExisting playlists :\n\n");
                    output.writeObject(controller.getPlaylists());
                    output.writeObject("Which one would you like to hear? (Enter the number)");
                    output.writeObject("What do you want? (Enter the number)\n1- Listen the playlist\n2- More informtion");
                    if(((String) input.readObject()).equals("1")) {
                        output.writeObject(true);
                    } else {
                        output.writeObject(false);
                    }
                    break;

                    case "4" : // Select and send an album
                    output.writeObject("\nName of the album to display :\n");
                    String albumTitle = (String) input.readObject();  /* Album title entered by the user */
                    Album album = controller.getSpecificAlbum(albumTitle);
                    if (album==null) {
                        output.writeObject("No album found.\n");
                    } else {
                        output.writeObject(album);
                        output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
                        if(((String) input.readObject()).equals("1")) {
                            output.writeObject(true);
                        } else {
                            output.writeObject(false);
                        }
                    }
                    break;

                    case "5" : // Select and send a playlist
                    output.writeObject("\nName of the playlist :\n");
                    String name = (String) input.readObject();
                    Playlist playlist = controller.getSpecificPlaylist(name);
                    if (playlist==null) {
                        output.writeObject("No playlist found.\n");
                    } else {
                        output.writeObject(playlist);
                        output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
                        if(((String) input.readObject()).equals("1")) {
                            output.writeObject(true);
                        } else {
                            output.writeObject(false);
                        }
                    }
                    break;

                    case "6" : // Select and send all artist's songs
                    output.writeObject("\t\tArtist's song names sorted by alphabetical order:\nArtist's name :\n\n");
                    LinkedList<Song> artistSongs = controller.getSongsByArtist((String) input.readObject());
                    if(artistSongs==null) {
                        output.writeObject("No artist with this name or no artist's song found.\n");
                    } else {
                        output.writeObject(artistSongs);
                        output.writeObject("Which one would you like to hear? (Enter the number)");
                        output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
                        if(((String) input.readObject()).equals("1")) {
                            output.writeObject(true);
                        } else {
                            output.writeObject(false);
                        }
                    }
                    break;

                    case "7" : // Select and send all author's audiobooks
                    output.writeObject("\t\t AudioBook titles sorted by them author:\nAuthor's name :\n\n");
                    LinkedList<AudioBook> authorAudioBooks = controller.getAudioBooksByAuthor((String) input.readObject());
                    if(authorAudioBooks==null) {
                        output.writeObject("No author with this name or no author's audio book found.\n");
                    } else {
                        output.writeObject(authorAudioBooks);
                        output.writeObject("Which one would you like to hear? (Enter the number)");
                        output.writeObject("What do you want? (Enter the number)\n1- Listen the audio book\n2- More informtion");
                        if(((String) input.readObject()).equals("1")) {
                            output.writeObject(true);
                        } else {
                            output.writeObject(false);
                        }
                    }
                    break;

                    case "8" : // Send all albums release by date
                    output.writeObject("\t\tAlbum titles sorted by them date:\nAlbums ordered by release date :\n\n");
                    //TODO(Exception handling)
                    // output.writeObject(controller.getAlbumByReleaseDate());
                    output.writeObject("Which one would you like to hear? (Enter the number)");
                    output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
                    if(((String) input.readObject()).equals("1")) {
                        output.writeObject(true);
                    } else {
                        output.writeObject(false);
                    }
                    break;

                    case "9" : // Send all songs sorted by genre
                    output.writeObject("\t\t Song titles sorted by them genre:\nName of the album to display :");
                    LinkedList<Song> songs = controller.getSongByGenre((String) input.readObject()); //Album title entered by the user
                    if(songs==null) {
                        output.writeObject("\nNo album found.\n");
                    } else {
                        output.writeObject(songs);
                        output.writeObject("Which one would you like to hear? (Enter the number)");
                        output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
                        if(((String) input.readObject()).equals("1")) {
                            output.writeObject(true);
                        } else {
                            output.writeObject(false);
                        }
                    }
                    break;

                  case "10": //Show all Artists
                    output.writeObject("\t\tAll artists' name :");
                    LinkedList<String> artistsName = this.controller.getArtists();
                    if(artistsName==null) {
                      output.writeObject("\nNo artist found.\n");
                    } else {
                      output.writeObject(artistsName);
                      output.writeObject("\n\nEnter a name's-artist number :");
                      int numberArtistName = (int) input.readObject();
                      if(numberArtistName==artistsName.size()) {
                        break;
                      } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
                        output.writeObject("\n\t\tAll the "+artistsName.get(numberArtistName)+"\'s songs :\n\n");
                        output.writeObject(this.controller.getSongsByArtist(artistsName.get(numberArtistName)));
                        break;
                      } else {
                        output.writeObject("\nInvalid number");
                        break;
                      }
                    }

                  case "11": //Show all Authors
                    output.writeObject("\t\tAll author's name :");
                    LinkedList<String> authorsName = this.controller.getAuthors();
                    if(authorsName==null) {
                      output.writeObject("\nNo authors found.\n");
                    } else {
                      output.writeObject(authorsName);
                      int numberAuthorName = (int) input.readObject();
                      if(numberAuthorName==authorsName.size()) {
                        break;
                      } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
                        output.writeObject("\n\t\tAll the "+authorsName.get(numberAuthorName)+"\'s songs :\n\n");
                        LinkedList<AudioBook> booksToDisplayAuthor = this.controller.getAudioBooksByAuthor(authorsName.get(numberAuthorName));
                        for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
                            output.writeObject(booksToDisplayAuthor.get(i));
                        }
                        break;
                      } else {
                        output.writeObject("\nInvalid number");
                        break;
                      }
                    }

                  case "12": //Show all Genres
                    output.writeObject("\t\tAll genres :");
                    LinkedList<Genre> genres = this.controller.getGenres();
                    if(genres==null) {
                      output.writeObject("\nNo genres found.\n");
                    } else {
                      output.writeObject(genres);
                      int numberGenre = (int) input.readObject();
                      if(numberGenre==genres.size()) {
                        break;
                      } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
                        output.writeObject("\n\t\tAll the "+genres.get(numberGenre)+"\'s songs :\n\n");
                        LinkedList<Song> songsToDisplayGenre = this.controller.getSongsByGenre(genres.get(numberGenre));
                        for (int i = 0; i < songsToDisplayGenre.size(); i++) {
                            output.writeObject(songsToDisplayGenre.get(i));
                        }
                        break;
                      } else {
                        output.writeObject("\nInvalid number");
                        break;
                      }
                    }

                  case "13": //Show all Categories
                    output.writeObject("\t\tAll categories :");
                    LinkedList<Category> categories = this.controller.getCategories();
                    if(categories==null) {
                      output.writeObject("\nNo categories found.\n");
                    } else {
                      output.writeObject(categories);
                      int numberCategory = (int) input.readObject();
                      if(numberCategory==categories.size()) {
                        break;
                      } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
                        output.writeObject("\n\t\tAll the "+categories.get(numberCategory)+"\'s songs :\n\n");
                        LinkedList<AudioBook> audioBooksToDisplayCategory = this.controller.getAudioBooksByCategory(categories.get(numberCategory));
                        for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
                            output.writeObject(audioBooksToDisplayCategory.get(i));
                        }
                        break;
                      } else {
                        output.writeObject("\nInvalid number");
                        break;
                      }
                    }

                  case "14": //Show all Languages
                    output.writeObject("\t\tAll languages :");
                    LinkedList<Language> languages = this.controller.getLanguages();
                    if(languages==null) {
                      output.writeObject("\nNo languages found.\n");
                    } else {
                      output.writeObject(languages);
                      int numberLanguage = (int) input.readObject();
                      if(numberLanguage==languages.size()) {
                        break;
                      } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
                        output.writeObject("\n\t\tAll the "+languages.get(numberLanguage)+"\'s songs :\n\n");
                        LinkedList<AudioBook> audioBooksToDisplayLanguage = this.controller.getAudioBooksByLanguage(languages.get(numberLanguage));
                        for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
                            output.writeObject(audioBooksToDisplayLanguage.get(i));
                        }
                        break;
                      } else {
                        output.writeObject("\nInvalid number");
                        break;
                      }
                    }

                    case "q" :// Quit the application
                    output.writeObject("\t\t Thank you for you time, have a nice day!\n\t\t\t\t\tSigned by nope.\n\n\n");
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
}
