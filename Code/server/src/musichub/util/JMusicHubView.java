package util;

import business.*;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/** JMusicHub Class is the main class of the JMusicHub program.
*
*
* Version : 1.0
*
* Date : 30/02/2001
*
* @author Gaël Lejeune and Steve Chauvreau-Manat (based on the work of Angélique Proux & Manelle Nouar)
*/
public class JMusicHubView implements View {


    private JMusicHubController controller;

    private JMusicHubModel model;

    public JMusicHubView(JMusicHubController controller) {
        this.controller = controller;
    }

    /**
    * Execution of the JMusicHub program and interaction with the user using a terminal
    * @param       args Arguments of the function
    * @author Gaël Lejeune and Steve Chauvreau-Manat
    */
    public void display() {
        System.out.println("\n\nWelcome in JMusicHub,");
        System.out.println("Reading library...\n\n");
        System.out.println("Library loaded, type any command to begin using jMusicHub\nType \"h\" for help\n");
        Scanner scanner = new Scanner (System.in);
        while(true) {
            String command = scanner.nextLine();
            switch (command) {
                case "1" : // Show albums
                System.out.println("\t\t Album titles sorted by them date:");
                //TODO(Erreur à gérer)
                // LinkedList<Album> albumList = controller.getAlbumByReleaseDate();
                // for (int i = 0; i < albumList.size(); i++) {
                //     System.out.println("\n"+albumList.get(i) + "\n");
                // }
                break;

                case "2" : // Show songs
                System.out.println("\t\t Song titles sorted by them genre:");
                String album = scanner.nextLine();
                LinkedList<Song> songs = this.controller.getSongByGenre(album);
                Genre currentGenre = songs.get(0).getGenre();
                while(songs.size()>0){
                    System.out.println("\nSongs with Genre : "+ currentGenre);
                    for (int i = 0; i < songs.size() ; i++) {
                        if (songs.get(i).getGenre().equals(currentGenre)) {
                            System.out.println(songs.get(i));
                            songs.remove(i);
                            i--;
                        }
                    }
                }
                break;

                case "3" : // Show audiobooks
                System.out.println("\t\t AudioBook titles sorted by them author:");
                String author = scanner.nextLine();
                LinkedList<AudioBook> audioBooks =  this.controller.getAudioBooksByAuthor(author);
                String currentAuthor = audioBooks.get(0).getAuthor();
                while(audioBooks.size()>0){
                    System.out.println("\nAuthor : "+ currentAuthor);
                    for (int i = 0; i < audioBooks.size() ; i++) {
                        if (audioBooks.get(i).getAuthor().equals(currentAuthor)) {
                            System.out.println(audioBooks.get(i));
                            audioBooks.remove(i);
                            i--;
                        }
                    }
                }
                break;

                case "4" : // Show playlists
                System.out.println("\t\t Playlist names sorted by alphabetical order:");
                System.out.println("\nExisting playlists :\n");
                for (int i = 0; i < controller.getPlaylists().size(); i++) {
                    System.out.println(controller.getPlaylists().get(i) + "\n");
                }
                break;

                case "5" : // Select an album
                String albumName = scanner.nextLine();
                Album albumToDisplay = controller.getSpecificAlbum(albumName);
                System.out.println(albumToDisplay + "\n");
                break;

                case "6" : // Select a playlist
                String playlistName = scanner.nextLine();
                Playlist playlistToDisplay = controller.getSpecificPlaylist(playlistName);
                System.out.println(playlistToDisplay + "\n");
                break;

                case "7" : // Select all the song of an artist
                String artistName = scanner.nextLine();
                LinkedList<Song> songsToDisplay = controller.getSongsByArtist(artistName);
                System.out.println("\nSongs from : " + artistName + "\n");
                for (int i = 0; i < songsToDisplay.size(); i++) {
                    System.out.println("\n" + songsToDisplay.get(i));
                }
                break;

                case "8" : // Select all the audiobooks of an author
                String authorName = scanner.nextLine();
                LinkedList<AudioBook> booksToDisplay = controller.getAudioBooksByAuthor(authorName);
                System.out.println("\nBooks from : " + authorName + "\n");
                for (int i = 0; i < booksToDisplay.size(); i++) {
                    System.out.println("\n" + booksToDisplay.get(i));
                }
                break;

                case "9" : // Change the content of the application
                controller.editDatabase();
                break;

                case "10" :// Quit the application
                System.out.println("\t\t Thank you for you time, have a nice day!\n");
                System.out.println("\t\t\t\t\tSigned by nope.\n\n\n");
                System.exit(0);
                break;
                case "h" ://Display the help
                controller.help();
                break;
                default:
                System.out.println("\nWrong command, press \"h\" for help.");
                break;
            }
        }
    }
}
