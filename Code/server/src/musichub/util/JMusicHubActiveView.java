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
                case "1" : //Show all Elements
                  System.out.println("\t\tSongs and AudioBooks sorted by alphabetical order:\n");
                  LinkedList<Audio> audios = controller.getElements();
                  for(int i=0;i<audios.size();i++) {
                    System.out.println("\n"+audios.get(i)+"\n");
                  }
                  break;

                case "2" : //Show all Albums
                  System.out.println("\t\tAlbums sorted by alphabetical order:\n\n");
                  LinkedList<Album> albums = controller.getAlbums();
                  for(int i=0;i<albums.size();i++) {
                    System.out.println("\n"+albums.get(i)+"\n");
                  }
                  break;

                case "3" : // Show all playlists
                  System.out.println("\t\tPlaylist names sorted by alphabetical order:\n\n");
                  for (int i = 0; i < controller.getPlaylists().size(); i++) {
                      System.out.println(controller.getPlaylists().get(i) + "\n");
                  }
                  break;

                case "4" : // Select and show an album
                  System.out.println("Enter an album's title :");
                  String albumName = scanner.nextLine();
                  System.out.println("\n\t\tSelected album "+albumName+" :\n\n");
                  Album albumToDisplay = controller.getSpecificAlbum(albumName);
                  System.out.println(albumToDisplay + "\n");
                  break;

                case "5" : // Select and show a playlist
                  System.out.println("Enter a playlist's name :");
                  String playlistName = scanner.nextLine();
                  System.out.println("\n\t\tThe selected playlist "+playlistName+" :\n\n");
                  Playlist playlistToDisplay = controller.getSpecificPlaylist(playlistName);
                  System.out.println(playlistToDisplay+"\n");
                  break;

                case "6" : // Select and show all selected artist's songs
                  System.out.println("Enter an artist's name :");
                  String artistName = scanner.nextLine();
                  System.out.println("\n\t\tAll the "+artistName+"\'s songs :\n\n");
                  LinkedList<Song> songsToDisplay = this.controller.getSongsByArtist(artistName);
                  for (int i = 0; i < songsToDisplay.size(); i++) {
                      System.out.println("\n" + songsToDisplay.get(i));
                  }
                  break;

                case "7" : // Select all author's audiobooks
                  System.out.println("Enter an author's name :");
                  String authorName = scanner.nextLine();
                  System.out.println("\n\t\tAll the "+authorName+"\'s audio books :\n\n");
                  LinkedList<AudioBook> booksToDisplay = this.controller.getAudioBooksByAuthor(authorName);
                  for (int i = 0; i < booksToDisplay.size(); i++) {
                      System.out.println("\n" + booksToDisplay.get(i));
                  }
                  break;

                case "8" : // Show all albums release by date
                  System.out.println("\t\tAll date-release-sorted albums :");
                  try {
                      LinkedList<Album> albumList = this.controller.getAlbumByReleaseDate();
                      for (int i = 0; i < albumList.size(); i++) {
                          System.out.println("\n"+albumList.get(i) + "\n");
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  break;

                case "9" : // Show all songs sorted by genre
                  System.out.println("Enter an album's title : ");
                  LinkedList<Song> songs = this.controller.getSongByGenre(scanner.nextLine());
                  System.out.println("\n\t\t Songs' titles sorted by genre:");
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

                case "10": //Show all Artists
                  System.out.println("\t\tAll artists' name :");
                  LinkedList<String> artistsName = this.controller.getArtists();
                  for(int i=0;i<artistName.size();i++) {
                    System.out.println("\n"+i+"- "+artistName+"\n");
                  }
                  break;

                case "" : // Change the content of the application
                  controller.editDatabase();
                  break;

                case "q" :// Quit the application
                  System.out.println("\t\t Thank you for you time, have a nice day!\n");
                  System.out.println("\t\t\t\t\tSigned by nope.\n\n\n");
                  System.exit(0);
                  break;
                case "h" ://Display the help
                  System.out.println(this.controller.helpActive());
                  break;
                default:
                  System.out.println("\nWrong command, press \"h\" for help.");
                  break;
            }
        }
    }
}
