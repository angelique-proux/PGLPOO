package util;

import java.io.*;
import java.net.*;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import business.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
  private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

  /**
   * XML editor allowing to read and write XML files
   * @see XMLReaderWriter
   */
  private XMLReaderWriter xmlEditor;

  /**
   * List of the registered playlists
   * @see Playlist
   */
  private LinkedList<Playlist> playlists;

  /**
   * List of the registered albums
   * @see Album
   */
  private LinkedList<Album> albums;

  private JMusicHub jMusicHub;

  /**
   * List of the registered audio elements
   * @see Audio
   */
  private LinkedList<Audio> elements;

  public ServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
		     //create the streams that will handle the objects coming through the sockets
         input = new ObjectInputStream(socket.getInputStream());
		     output = new ObjectOutputStream(socket.getOutputStream());

         output.writeObject("\nConnected to the server\nType any command to begin using jMusicHub\nType \"h\" for help\n"); //serialize and write the object to the stream
         String result = null;

         while(true) {
           String command = (String) input.readObject();  //read the object received through the stream and deserialize it
           System.out.println(command);
            switch (command) {
              case "1" : // Show albums public void displayAlbumByReleaseDate() throws Exception
                output.writeObject("\t\tAlbum titles sorted by them date:\nAlbums ordered by release date :\n\n");
                output.writeObject(jMusicHub.getAlbumByReleaseDate());
                break;

              case "2" : // Show songs public void displaySongByGenre()
                output.writeObject("\t\t Song titles sorted by them genre:\nName of the album to display :");
                //Scanner scanner = new Scanner (System.in);
                result = null;
                boolean found = false;
                Album album = this.albums.get(0);
                String title = (String) input.readObject();  /* Album title entered by the user */
                for (int i = 0; i < this.albums.size(); i++) {
                    if (this.albums.get(i).getTitle().equals(title)) {
                        found = true;
                        album = this.albums.get(i);
                    }
                }
                if(found) {
                    LinkedList<Song> songs = album.getSongs();
                    while(songs.size() > 0) {
                        Genre genre = songs.get(0).getGenre();
                        result = "\nSongs with genre : " + genre;
                        for (int i = 0; i < songs.size(); i++) {
                            if (songs.get(i).getGenre().equals(genre)) {
                                result+="\n"+songs.get(i) + "\n";
                                songs.remove(i);
                                i--;
                            }
                        }
                    }
                    output.writeObject(result);
                } else {
                    output.writeObject("\nNo album found.\n");
                }
                break;

              case "3" : // Show audiobooks public void displayAudioBooksByAuthor()
                result = "\t\t AudioBook titles sorted by them author:";
                LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
                for (int i = 0; i < this.elements.size(); i++) {
                    if (this.elements.get(i) instanceof AudioBook) {
                        audioBooks.add((AudioBook)this.elements.get(i));
                    }
                }
                while(audioBooks.size() > 0) {
                    String author = audioBooks.get(0).getAuthor();
                    result += "\nAuthor : " + author+"\n";
                    for (int i = 0; i < audioBooks.size(); i++) {
                        if (audioBooks.get(i).getAuthor().equals(author)) {
                            result+=audioBooks.get(i) + "\n";
                            audioBooks.remove(i);
                            i--;
                        }
                    }
                    output.writeObject(result);
                }
                break;

              case "4" : // Show playlists public void displayPlaylists()
                output.writeObject("\t\t Playlist names sorted by alphabetical order:\nExisting playlists :\n\n");
                output.writeObject(jMusicHub.getPlaylists());
                break;

              case "5" : // Select an album public void displaySpecificAlbum()
                output.writeObject("\nName of the album to display :\n");
                result = null;
                boolean found = false;
                String title = (String) input.readObject();  /* Album title entered by the user */
                for (int i = 0; i < this.albums.size(); i++) {
                    if (this.albums.get(i).getTitle().equals(title)) {
                        result += this.albums.get(i) + "\n";
                        found = true;
                    }
                }
                if (!found) {
                    result += "No album found.\n";
                }
                output.writeObject(result);
                break;

              case "6" : // Select a playlist public void displaySpecificPlaylist()
                output.writeObject("\nName of the playlist :\n");
                String name = (String) input.readObject();
                Playlist playlist = jMusicHub.getSpecificPlaylist(name);
                if (playlist==null) {
                    output.writeObject("No playlist found.\n");
                } else {
                  output.writeObject(playlist);
                }
                break;

              case "7" : // Select all the song of an artist
                //TODO
                // selectArtist7(util, sc);
                break;

              case "8" : // Select all the song of an author
                //TODO
                // selectAuthor8(util, sc);
                break;

              case "10" :// Quit the application
                output.writeObject("\t\t Thank you for you time, have a nice day!\n\t\t\t\t\tSigned by nope.\n\n\n");
                System.exit(0);
                break;

              case "h" ://Display the help
                jMusicHub.help();
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
        output.close();
        input.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
			}
    }
  }
}
