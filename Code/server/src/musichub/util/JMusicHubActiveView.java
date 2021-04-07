package util;

import business.*;
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
public class JMusicHubActiveView implements View {

    private JMusicHubController controller;

    public JMusicHubActiveView(JMusicHubController controller) {
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
                try {
                    LinkedList<Album> albumList = controller.getAlbumByReleaseDate();
                    for (int i = 0; i < albumList.size(); i++) {
                        System.out.println("\n"+albumList.get(i) + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

                case "2" : // Show songs
                System.out.println("\nEnter an album's title : ");
                LinkedList<Song> songs = this.controller.getSongByGenre(scanner.nextLine());
                System.out.println("\t\t Song titles sorted by them genre:");
                Genre currentGenre = songs.get(0).getGenre();
                System.out.println("\nSongs with Genre : "+ currentGenre);
                for (int i = 0; i < songs.size() ; i++) {
                  if(!songs.get(i).getGenre().equals(currentGenre)) {
                    currentGenre = songs.get(i).getGenre();
                    System.out.println("\nSongs with Genre : "+ currentGenre+"\n");
                  }
                  System.out.println(songs.get(i));
                }
                break;

                case "3" : // Select all the audiobooks of an author
                System.out.println("Enter an author's name : ");
                String authorName = scanner.nextLine();
                LinkedList<AudioBook> booksToDisplay = controller.getAudioBooksByAuthor(authorName);
                System.out.println("\nBooks from : " + authorName + "\n");
                for (int i = 0; i < booksToDisplay.size(); i++) {
                    System.out.println("\n" + booksToDisplay.get(i));
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

                case "8" : // Change the content of the application
                controller.editDatabase();
                break;

                case "q" :// Quit the application
                  System.out.println("\t\t Thank you for you time, have a nice day!\n");
                  System.out.println("\t\t\t\t\tSigned by nope.\n\n\n");
                  System.exit(0);
                  break;
                case "h" ://Display the help
                  System.out.println(controller.help());
                  break;
                default:
                  System.out.println("\nWrong command, press \"h\" for help.");
                  break;
            }
        }
    }
}
