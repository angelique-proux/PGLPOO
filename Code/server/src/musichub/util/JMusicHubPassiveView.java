package util;

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

public class JMusicHubPassiveView implements View {
  private JMusicHubController controller;
  private ObjectInputStream input;
	private ObjectOutputStream output;
  private Socket socket;

  public JMusicHubPassiveView(JMusicHubController controller, Socket socket) {
      this.controller = controller;
      this.socket = socket;
  }

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
             output.writeObject(controller.getElements());
             output.writeObject("Which one would you like to hear? (Enter the number)");
             output.writeObject("What do you want? (Enter the number)\n1- Listen the playlist\n2- More informtion");
             if(((String) input.readObject())=="1") {
               output.writeObject(true);
             } else {
               output.writeObject(false);
             }
             break;

           case "2" : //Send all Albums
             output.writeObject("\t\tAlbums sorted by alphabetical order:\n");
             output.writeObject(controller.getAlbums());
             output.writeObject("Which one would you like to hear? (Enter the number)");
             output.writeObject("What do you want? (Enter the number)\n1- Listen the album\n2- More informtion");
             if(((String) input.readObject())=="1") {
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
             if(((String) input.readObject())=="1") {
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
               if(((String) input.readObject())=="1") {
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
               if(((String) input.readObject())=="1") {
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
               if(((String) input.readObject())=="1") {
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
               if(((String) input.readObject())=="1") {
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
             if(((String) input.readObject())=="1") {
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
             }
             output.writeObject("Which one would you like to hear? (Enter the number)");
             output.writeObject("What do you want? (Enter the number)\n1- Listen the song\n2- More informtion");
             if(((String) input.readObject())=="1") {
               output.writeObject(true);
             } else {
               output.writeObject(false);
             }
             break;

           case "10" :// Quit the application
             output.writeObject("\t\t Thank you for you time, have a nice day!\n\t\t\t\t\tSigned by nope.\n\n\n");
             System.exit(0);
             break;

           case "h" ://Display the help
             output.writeObject(this.controller.help());
             break;

           default:
             output.writeObject("\nWrong command, press \"h\" for help.");
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
