/*
 * Class' name : JMusicHubController
 *
 * Description : Controller of the JMusicHub program.
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
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collections;
import java.net.*;
import java.io.*;
import javax.sound.sampled.*;

/**
 * JMusicHubController Class is the controller of the JMusicHub program.
 *
 * Version : 1.0
 *
 * @see Controller
 * @author Gaël Lejeune
 */
public class JMusicHubController implements Controller {

    /**
     * XML editor allowing to read and write XML files
     * @see  JMusicHubModel
     */
    private Model model;

    /**
     * View of the application
     */
    private View view;

    /**
     * List of the registered playlists
     * @see  Playlist
     */
    private LinkedList<Playlist> playlists;

    /**
     * List of the registered albums
     * @see  Album
     */
    private LinkedList<Album> albums;

    /**
     * List of the registered audio elements
     * @see  Audio
     */
    private LinkedList<Audio> elements;

    /**
     * Album constructor
     * Initialize all the attributes and fill the lists by reading the XML files
     *
     * @param    model Model interfacing with the controller
     *
     * @author   Gaël Lejeune
     */
    public JMusicHubController(Model model) {
        this.model = model;
        this.view = new JMusicHubActiveView(this);

        /* Load of all the XML files */
        try {
            this.playlists = this.model.readPlaylistXML("files/playlists.xml");
            this.albums = this.model.readAlbumXML("files/albums.xml");
            this.elements = this.model.readElementXML("files/elements.xml");
        } catch (MissingFileException ex) {
            System.out.println(ex.getMessage());
        }
        this.view.display();
    }

    /**
     * Album constructor
     * Initialize all the attributes and fill the lists by reading the XML files
     *
     * @param       model Required database editing model file
     * @param       socket Connection socket between client and server
     * @param       port Server's listening port
     *
     * @author   Gaël Lejeune
     */
    public JMusicHubController(Model model, Socket socket, int port) {
        this.model = model;
        this.view = new JMusicHubPassiveView(this,socket,port);

        /* Load of all the XML files */
        try {
            this.playlists = this.model.readPlaylistXML("files/playlists.xml");
            this.albums = this.model.readAlbumXML("files/albums.xml");
            this.elements = this.model.readElementXML("files/elements.xml");
        } catch (MissingFileException ex) {
            System.out.println(ex.getMessage());
        }
        this.view.display();
    }

    /**
     * Returnss all the registered audio elements
     *
     * @return       LinkedList List of all the audio elements
     *
     * @see         Album
     * @author      Gaël Lejeune
     */
    public LinkedList<Audio> getElements() {
        return this.elements;
    }

    /**
     * Returnss all the registered albums
     *
     * @return       LinkedList List of all the registered albums
     *
     * @see         Album
     * @author      Gaël Lejeune
     */
    public LinkedList<Album> getAlbums() {
        return this.albums;
    }

    /**
     * Returnss all the registered playlists
     *
     * @return       LinkedList List of all the playlists
     *
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public LinkedList<Playlist> getPlaylists() {
        return this.playlists;
    }

    /**
     * Asks and displays an specific registered album
     *
     * @param       title Title of the desired album
     * @return      Album Desired album
     *
     * @see         Album
     * @author      Gaël Lejeune
     */
    public Album getSpecificAlbum(String title) {
        boolean found = false; /* Album title entered by the user */
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                found = true;
                return this.albums.get(i);
            }
        }
        return null;
    }

    /**
     * Asks and displays an specific registered playlist
     *
     * @param       name Name of the desired playlist
     * @return      Playlist Desired playlist
     *
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public Playlist getSpecificPlaylist(String name) {
        boolean found = false;
        for (int i = 0; i < this.playlists.size(); i++) {
            if (this.playlists.get(i).getName().equals(name)) {
                found = true;
                return this.playlists.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the registered artists list
     *
     * @return		  LinkedList of registered artists
     *
     * @see         Song
     * @author      Gaël Lejeune
     */
    public LinkedList<String> getArtists() {
        LinkedList<String> artists = new LinkedList<String>();
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof Song)&&(!artists.contains(((Song)this.elements.get(i)).getArtist()))) {
                artists.add(((Song)this.elements.get(i)).getArtist());
            }
        }
        Collections.sort(artists);
        return artists;
    }

    /**
     * Returns the registered authors list
     *
     * @return		  LinkedList of registered authors
     *
     * @see         AudioBook
     * @author      Gaël Lejeune
     */
    public LinkedList<String> getAuthors() {
        LinkedList<String> authors = new LinkedList<String>();
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(!authors.contains(((AudioBook)this.elements.get(i)).getAuthor()))) {
                authors.add(((AudioBook)this.elements.get(i)).getAuthor());
            }
        }
        Collections.sort(authors);
        return authors;
    }

    /**
     * Returns the registered genres list
     *
     * @return		  LinkedList of registered genres
     *
     * @see         Song
     * @author      Gaël Lejeune
     */
    public LinkedList<Genre> getGenres() {
        LinkedList<Genre> genres = new LinkedList<Genre>();
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof Song)&&(!genres.contains(((Song)this.elements.get(i)).getGenre()))) {
                genres.add(((Song)this.elements.get(i)).getGenre());
            }
        }
        Collections.sort(genres);
        return genres;
    }

    /**
     * Returns the registered categories list
     *
     * @return		  LinkedList of registered categories
     *
     * @see         AudioBook
     * @author      Gaël Lejeune
     */
    public LinkedList<Category> getCategories() {
        LinkedList<Category> categories = new LinkedList<Category>();
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(!categories.contains(((AudioBook)this.elements.get(i)).getCategory()))) {
                categories.add(((AudioBook)this.elements.get(i)).getCategory());
            }
        }
        Collections.sort(categories);
        return categories;
    }

    /**
     * Returns the registered languages list
     *
     * @return		  LinkedList of registered languages
     *
     * @see         AudioBook
     * @author      Gaël Lejeune
     */
    public LinkedList<Language> getLanguages() {
        LinkedList<Language> languages = new LinkedList<Language>();
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(!languages.contains(((AudioBook)this.elements.get(i)).getLanguage()))) {
                languages.add(((AudioBook)this.elements.get(i)).getLanguage());
            }
        }
        Collections.sort(languages);
        return languages;
    }

    /**
     * Returns all audio books ordered by author
     *
     * @param		    artist Desired artist
     * @return		  LinkedList List of the artist's songs
     *
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<Song> getSongsByArtist(String artist) {
        LinkedList<Song> songs = new LinkedList<Song>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof Song)&&(((Song)this.elements.get(i)).getArtist().equals(artist))) {
                songs.add((Song)this.elements.get(i));
                found = true;
            }
        }
        if(!found) {
            return null;
        } else {
            return songs;
        }
    }

    /**
     * Returns all audio books ordered by genre
     *
     * @param		    genre Desired genre
     * @return		  LinkedList List of the songs with the corresponding genre
     *
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<Song> getSongsByGenre(Genre genre) {
        LinkedList<Song> songs = new LinkedList<Song>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof Song)&&(((Song)this.elements.get(i)).getGenre().equals(genre))) {
                songs.add((Song)this.elements.get(i));
                found = true;
            }
        }
        if(!found) {
            return null;
        } else {
            return songs;
        }
    }

    /**
     * Returns all audio books ordered with a certain language
     *
     * @param		    language Desired language
     * @return		  LinkedList List of the elements with corresponding language
     *
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<AudioBook> getAudioBooksByLanguage(Language language) {
        LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(((AudioBook)this.elements.get(i)).getLanguage().equals(language))) {
                audioBooks.add((AudioBook)this.elements.get(i));
                found = true;
            }
        }
        if(!found) {
            return null;
        } else {
            return audioBooks;
        }
    }

    /**
     * Returns all audio books ordered with a certain category
     *
     * @param		    category Desired category
     * @return		  LinkedList List of audiobooks with corresponding category
     *
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<AudioBook> getAudioBooksByCategory(Category category) {
        LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(((AudioBook)this.elements.get(i)).getCategory().equals(category))) {
                audioBooks.add((AudioBook)this.elements.get(i));
                found = true;
            }
        }
        if(!found) {
            return null;
        } else {
            return audioBooks;
        }
    }

    /**
     * Returns all audio books ordered by author
     *
     * @param		    author Desired author
     * @return		  LinkedList List of all audiobooks from the specified author
     *
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<AudioBook> getAudioBooksByAuthor(String author) {
        LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if ((this.elements.get(i) instanceof AudioBook)&&(((AudioBook)this.elements.get(i)).getAuthor().equals(author))) {
                audioBooks.add((AudioBook)this.elements.get(i));
                found = true;
            }
        }
        if(!found) {
            return null;
        } else {
            return audioBooks;
        }
    }

    /**
     * Returns the album list ordered by release date
     *
     * @return		  LinkedList List of the albums, ordered by release date
     * @throws		  Exception Thrown when the date is not a real one
     *
     * @exception   Exception Thrown the given String cannot be converted to date.
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<Album> getAlbumByReleaseDate() throws Exception {
        LinkedList<Album> albumList = new LinkedList<Album>();
        Date datePrec = new SimpleDateFormat("dd/MM/yyyy").parse("0/00/0000");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(this.albums.get(0).getReleaseDate());
        Date dateTemp = new SimpleDateFormat("dd/MM/yyyy").parse(this.albums.get(0).getReleaseDate());
        int albumIndex = 0;
        boolean found = true;
        System.out.println("\nAlbums ordered by release date :");
        while (found) {
            found = false;
            date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/3000");
            for (int i = 0; i < this.albums.size() ; i++) {
                dateTemp = new SimpleDateFormat("dd/MM/yyyy").parse(this.albums.get(i).getReleaseDate());
                if (dateTemp.after(datePrec) && dateTemp.before(date)) {
                    date = dateTemp;
                    albumIndex = i;
                    found = true;
                }
            }
            datePrec = date;
            if (found == true) {
                // System.out.println("\n"+albums.get(albumIndex) + "\n");
                albumList.add(albums.get(albumIndex));
            }
        }
        return albumList;
    }

    /**
     * Returns the song list of an asked album, ordered by genre
     *
     * @param		    title Requested album's title
     * @return		  LinkedList List of the album songs ordered by genre
     *
     * @see         Album
     * @see         Song
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public LinkedList<Song> getSongByGenre(String title) {
        boolean found = false;
        Album album = this.albums.get(0); /* Album title entered by the user */
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                found = true;
                album = this.albums.get(i);
                break;
            }
        }
        if (found) {
            LinkedList<Song> songs = album.getSongs();
            LinkedList<Song> songsSort = new LinkedList<Song>();
            int j=0;
            while(songs.size() > 0) {
                Genre genre = songs.get(0).getGenre();
                for(int i = 0; i < songs.size(); i++) {
                    if(songs.get(i).getGenre().equals(genre)) {
                        songsSort.add(songs.get(i));
                        songs.remove(i);
                        i--;
                    }
                }
            }
            return songsSort;
        } else {
            return null;
        }
    }

    /**
     * Returns information and help about various commands
     *
     * @return String Help about various commands in passive mod
     *
     * @author Steve Chauvreau-Manat
     */
    public String helpPassive() {
        return "\n- 1 : display all registered elements"
        +"\n- 2 : display all registered albums"
        +"\n- 3 : display all registered playlists"
        +"\n- 4 : display the selected album"
        +"\n- 5 : display the selected playlist"
        +"\n- 6 : display all artist's songs"
        +"\n- 7 : display all author's audiobooks"
        +"\n- 8 : display all albums release by date"
        +"\n- 9 : display all songs sorted by genre"
        +"\n- 10 : diplay all registered artists"
        +"\n- 11 : diplay all registered authors"
        +"\n- 12 : diplay all registered genres"
        +"\n- 13 : diplay all registred categories"
        +"\n- 14 : diplay all registered languages"
        +"\n- q : exit the jMusicHub"
        +"\n- h : help with details of previous commands";
    }

    /**
     * Returns information and help about various commands
     *
     * @return String Help about various commands in active mod
     *
     * @author Steve Chauvreau-Manat
     */
    public String helpActive() {
        return "\n- 1 : display all registered elements"
        +"\n- 2 : display all registered albums"
        +"\n- 3 : display all registered playlists"
        +"\n- 4 : display the selected album"
        +"\n- 5 : display the selected playlist"
        +"\n- 6 : display all artist's songs"
        +"\n- 7 : display all author's audiobooks"
        +"\n- 8 : display all albums release by date"
        +"\n- 9 : display all songs sorted by genre"
        +"\n- 10 : diplay all registered artists"
        +"\n- 11 : diplay all registered authors"
        +"\n- 12 : diplay all registered genres"
        +"\n- 13 : diplay all registred categories"
        +"\n- 14 : diplay all registered languages"
        +"\n- edit : edit the database"
        +"\n- q : exit the jMusicHub"
        +"\n- h : help with details of previous commands"
        +"\n----------------------------------------------------------------"
        +"\nHere are the commands you can use to make changes:"
        +"\nc : add a song\t\t\t|\tl : add an audiobook"
        +"\na : add an album\t\t|\tp : create a new playlist"
        +"\n+ : add a song to an album\t|\t- : delete a playlist"
        +"\n-- : remove a song from a playlist\t|\ts : save all modifications"
        +"\nDon't forget to save all your modifications.";
    }

    /**
     * Add to the database an audio
     *
     * @param       audio the audio to be added to the database
     *
     * @see         Audio
     * @see         Song
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addAudioToDataBase(Audio audio) {
      if(audio instanceof Song) {
        this.elements.add((Song) audio);
      } else if(audio instanceof AudioBook) {
        this.elements.add((AudioBook) audio);
      }
    }

    /**
     * Add to the database an audio
     *
     * @param       album the album to be added to the database
     *
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addAlbumToDataBase(Album album) {
        this.albums.add(album);
    }

    /**
     * Add to the database an audio
     *
     * @param       playlist the playlist to be added to the database
     *
     * @see         Playlist
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addPlaylistToDataBase(Playlist playlist) {
        this.playlists.add(playlist);
    }

    /**
     * Find an album thanks to its name
     *
     * @param       title the album's title
     * @return      Album
     *
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public Album getAlbumByTitle(String title) {
      for (int i = 0; i < this.albums.size(); i++) {
        if (this.albums.get(i).getTitle().equals(title)) {
          return this.albums.get(i);
        }
      }
      return null;
    }

    /**
     * Add an existing song to a chosen album
     *
     * @param       album the album in which you want to add music
     * @param       songname the song's name we want to add to the album
     * @return      Audio
     *
     * @see         Album
     * @see         Song
     * @author      Gaël Lejeune
     */
    public Audio addSongToAlbum(Album album, String songname) {
      for (int i = 0; i < this.elements.size(); i++) {
        if (this.elements.get(i).getTitle().equals(songname)) {
          album.addAudio(this.elements.get(i));
          return this.elements.get(i);
        }
      }
      return null;
    }

    /**
     * Find a playlist thanks to its name
     *
     * @param       title the playlist's title
     * @return      Playlist
     *
     * @see         Playlist
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public Playlist getPlaylistByName(String name) {
      for (int i = 0; i < this.playlists.size(); i++) {
        if (this.playlists.get(i).getName().equals(name)) {
          return this.playlists.get(i);
        }
      }
      return null;
    }

    /**
     * Find an audio thanks to its name
     *
     * @param       title the audio's title
     * @return      Audio
     *
     * @see         Audio
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public Audio getAudioByTitle(String title) {
      for (int i = 0; i < this.elements.size(); i++) {
        if (this.elements.get(i).getTitle().equals(title)) {
          return this.elements.get(i);
        }
      }
      return null;
    }

    /**
     * Add an existing audio to a chosen playlist
     *
     * @param       playlist the playlist in which you want to add music
     * @param       songname the song's name we want to add to the playlist
     * @return      Song
     *
     * @see         Playlist
     * @see         Audio
     * @author      Gaël Lejeune
     */
    public Audio addAudioToPlaylist(Playlist playlist, String songname) {
      for (int i = 0; i < this.elements.size(); i++) {
        if (this.elements.get(i).getTitle().equals(songname)) {
          playlist.addAudio(this.elements.get(i));
          return this.elements.get(i);
        }
      }
      return null;
    }

    /**
     * Delete the chosen audio from a chosen playlist
     *
     * @param       playlisttitle the name of the playlist where you want to delete a music
     * @param       audioTitle the name of the audio tou want to remove from the playlist
     * @return      boolean
     *
     * @see         Playlist
     * @see         Audio
     * @author      Gaël Lejeune
     */
    public boolean removeAudioFromPlaylist(String playlistTitle, String audioTitle) {
      for (int i = 0; i < this.playlists.size(); i++) {
        if (this.playlists.get(i).getName().equals(playlistTitle)) {
          LinkedList<Audio> audioList = this.playlists.get(i).getAudios();
          for(int j=0;j<audioList.size();j++) {
            if(audioList.get(j).getTitle().equals(audioTitle)) {
              this.playlists.get(i).removeAudio(audioList.get(j));
              return true;
            }
          }
        }
      }
      return false;
    }

    /**
     * Asks and delete the chosen playlist
     *
     * @param       title the playlist's name we want to delete
     * @return      boolean
     *
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public boolean deletePlaylist(String name) {
      for (int i = 0; i < this.playlists.size(); i++) {
        if (this.playlists.get(i).getName().equals(name)) {
          this.playlists.remove(i);
          return true;
        }
      }
      return false;
    }

    /**
     * Create XML files containing the registered playlists, elements, and albums using the class XML editor
     * @see         JMusicHubModel
     * @author      Gaël Lejeune
     */
    public void save() {
        this.model.writeElementXML("files/elements.xml", this.elements);
        this.model.writeAlbumXML("files/albums.xml", this.albums);
        this.model.writePlaylistXML("files/playlists.xml", this.playlists);
    }
}
