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

         String command = (String) input.readObject();  //read the object received through the stream and deserialize it
         System.out.println(command);

         switch (command) {
              case "p": //createPlaylistFromExisting();
                  //Scanner scanner = new Scanner (System.in);
                  output.writeObject("\nName of your new playlist :");
                  //String name = scanner.nextLine();
                  String name = (String) input.readObject();
                  output.writeObject("\nGenerating UUID");
                  UUID uuid = UUID.randomUUID();

                  LinkedList<Audio> audios = new LinkedList<Audio>();
                  output.writeObject("\nEnter the name of the songs you wish to add or press enter to finish : ");
                  do{
                      //String songname = scanner.nextLine();
                      String songname = (String) input.readObject();
                      boolean found = false;
                      for (int i = 0; i < this.elements.size(); i++) {
                          if (this.elements.get(i).getTitle().equals(songname)) {
                              audios.add(this.elements.get(i));
                              output.writeObject("\nAdded song : " + this.elements.get(i));
                              found = true;
                          }
                      }
                      if (!found) {
                          output.writeObject("\nNo song found.");
                      }
                  }while(songname.equals(""));
                  if (audios.size() == 0) {
                      output.writeObject("\nEmpty playlist, abort creation.");
                      return;
                  }
                  this.playlists.add(new Playlist(name, uuid, audios));
                  output.writeObject("\nPlaylist created");
                  break;
              case "-": //deletePlaylist();
                  //A mettre dans le client
                  //Scanner scanner = new Scanner (System.in);
                  output.writeObject("\nName of the playlist to delete :");
                  //String name = scanner.nextLine();
                  String name = (String) input.readObject();
                  boolean found = false;
                  for (int i = 0; i < this.playlists.size(); i++) {
                      if (this.playlists.get(i).getName().equals(name)) {
                          this.playlists.remove(i);
                          found = true;
                          output.writeObject("\nSuccessfully removed");
                      }
                      if (!found) {
                          output.writeObject("\nNo playlist found");
                      }
                  }
                  break;
              case "s": //save();
                  this.xmlEditor.writeElementXML("files/elements.xml", this.elements);
                  this.xmlEditor.writeAlbumXML("files/albums.xml", this.albums);
                  this.xmlEditor.writePlaylistXML("files/playlists.xml", this.playlists);
                  output.writeObject("\nSuccessfully saved");
                  break;
              case "d": //displayElements();
                  output.writeObject("\nExisting elements :\n");
                  for (int i = 0; i < this.elements.size(); i++) {
                      output.writeObject(this.elements.get(i) + "\n");
                  }
                  break;
              case "dab": //displayAudioBooksByAuthor();
                  LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
                  for (int i = 0; i < this.elements.size(); i++) {
                      if (this.elements.get(i) instanceof AudioBook) {
                          audioBooks.add((AudioBook)this.elements.get(i));
                      }
                  }
                  while(audioBooks.size() > 0) {
                      String author = audioBooks.get(0).getAuthor();
                      output.writeObject("\nAuthor : " + author+"\n");
                      for (int i = 0; i < audioBooks.size(); i++) {
                          if (audioBooks.get(i).getAuthor().equals(author)) {
                              output.writeObject(audioBooks.get(i) + "\n");
                              audioBooks.remove(i);
                              i--;
                          }
                      }
                  }
                  break;
              case "da": //displayAlbums();
                  output.writeObject("\nExisting albums :\n");
                  for (int i = 0; i < this.albums.size(); i++) {
                      output.writeObject(this.albums.get(i) + "\n");
                  }
                  break;
              case "dsa": //displaySpecificAlbum();
                  //Scanner scanner = new Scanner (System.in);
                  output.writeObject("\nName of the album to display :\n");
                  boolean found = false;
                  //String title = scanner.nextLine();  /* Album title entered by the user */
                  String title = (String) input.readObject();
                  for (int i = 0; i < this.albums.size(); i++) {
                      if (this.albums.get(i).getTitle().equals(title)) {
                          output.writeObject(this.albums.get(i) + "\n");
                          found = true;
                      }
                  }
                  if (!found) {
                      output.writeObject("No album found.\n");
                  }
                  break;
              case "dad":
                  try {
                      jMusicHub.displayAlbumByReleaseDate();
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
                  break;
              case "dag":
                  jMusicHub.displaySongByGenre();
                  break;
              case "dp":
                  jMusicHub.displayPlaylists();
                  break;
              case "dsp":
                  jMusicHub.displaySpecificPlaylist();
                  break;
              case "h":
                  jMusicHub.help();
                  break;
              case "q":
                  jMusicHub.save();
                  String object = "\n\nThank you to trust jMusicHub to manage your audio files :)";
              default:
                  String object = "\nWrong command, press \"h\" for help.";
                  break;
            }

         Student student = new Student(1234, "john.doe");
         output.writeObject(object);		//serialize and write the object to the stream
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
