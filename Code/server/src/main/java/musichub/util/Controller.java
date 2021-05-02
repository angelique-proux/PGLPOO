/*
 * Interface's name : Controller
 *
 * Description      : Interface is the interface representing a base controller
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Controller Interface is the interface representing a base controller
 *
 * Version : 1.0
 *
 * @author  Gaël Lejeune
 */
 public interface Controller {

     /**
      * Returns all the registered audio elements
      *
      * @return      LinkedList list of the audio elements
      *
      * @see         Audio
      * @author      Gaël Lejeune
      */
     public LinkedList<Audio> getElements();

     /**
      * Returns all the registered albums
      *
      * @return      LinkedList list of audio elements
      *
      * @see         Album
      * @author      Gaël Lejeune
      */
     public LinkedList<Album> getAlbums();

     /**
      * Returns all the registered playlists
      *
      * @return      LinkedList list of the albums
      *
      * @see         Playlist
      * @author      Gaël Lejeune
      */
     public LinkedList<Playlist> getPlaylists();

     /**
      * Asks and displays a specific registered albums
      *
      * @return      Playlist asked albums
      * @param       title asked playlist name
      *
      * @see         Album
      * @author      Gaël Lejeune
      */
     public Album getSpecificAlbum(String title);

     /**
      * Asks and displays a specific registered playlist
      *
      * @return      Playlist asked playlist
      * @param       name asked playlist name
      *
      * @see         Playlist
      * @author      Gaël Lejeune
      */
     public Playlist getSpecificPlaylist(String name);

     /**
      * Returns all the registered artists
      *
      * @return      LinkedList list of the artists
      *
      * @author      Gaël Lejeune
      */
     public LinkedList<String> getArtists();

     /**
      * Returns all the registered authors
      *
      * @return      LinkedList list of the authors
      *
      * @author      Gaël Lejeune
      */
     public LinkedList<String> getAuthors();

     /**
      * Returns all the registered genres
      *
      * @return      LinkedList list of the genres
      *
      * @see         Genre
      * @author      Gaël Lejeune
      */
     public LinkedList<Genre> getGenres();

     /**
      * Returns all the registered categories
      *
      * @return      LinkedList list of the categories
      *
      * @see         Category
      * @author      Gaël Lejeune
      */
     public LinkedList<Category> getCategories();

     /**
      * Returns all the registered languages
      *
      * @return      LinkedList list of the languages
      *
      * @see         Language
      * @author      Gaël Lejeune
      */
     public LinkedList<Language> getLanguages();

     /**
      * Returns all songs of an artist
      *
      * @return      LinkedList list of the artist's songs
      * @param       artist Asked artist
      *
      * @see         Song
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<Song> getSongsByArtist(String artist);

     /**
      * Returns all songs of a genre
      *
      * @return      LinkedList list of the genre's songs
      * @param       genre Asked genre
      *
      * @see         Genre
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<Song> getSongsByGenre(Genre genre);

     /**
      * Returns all audiobooks of a language
      *
      * @return      LinkedList list of the language's audiobooks
      * @param       language Asked language
      *
      * @see         AudioBook
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<AudioBook> getAudioBooksByLanguage(Language language);

     /**
      * Returns all audiobooks of a category
      *
      * @return      LinkedList list of the category's audiobooks
      * @param       category Asked category
      *
      * @see         AudioBook
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<AudioBook> getAudioBooksByCategory(Category category);

     /**
      * Returns all audiobooks of an author
      *
      * @return      LinkedList list of the author's audiobooks
      * @param       author Asked author
      *
      * @see         AudioBook
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<AudioBook> getAudioBooksByAuthor(String author);

     /**
      * Returns all albums ordered by release date
      *
      * @return      LinkedList list of the ordered albums
      * @throws      Exception Thrown when the date is not a real one
      *
      * @see         Album
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<Album> getAlbumByReleaseDate() throws Exception;

     /**
      * Returns all songs of an album ordered by genre
      *
      * @return      LinkedList list of the ordered songs
      * @param       title Title of the album
      *
      * @see         Album
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public LinkedList<Song> getSongByGenre(String title);

     /**
      * Returns help about the software's usage in passive mode
      *
      * @return      String Help informations as a string
      *
      * @author      Steve Chauvreau-Manat
      */
     public String helpPassive();

     /**
      * Returns help about the software's usage in active mode
      *
      * @return      String Help informations as a string
      *
      * @author      Steve Chauvreau-Manat
      */
     public String helpActive();

     /**
      * Asks the information about a song to add it to the audio elements
      * @see         Song
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public void addSong();

     /**
      * Asks the information about an audio book to add it to the audio elements
      * @see         AudioBook
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public void addAudioBook();

     /**
      * Asks the information about an album to add it to the audio elements
      * @see         Album
      * @author      Gaël Lejeune and Steve Chauvreau-Manat
      */
     public void addAlbum();

     /**
      * Asks and add an existing song to a chosen album
      * @see         Album
      * @see         Song
      * @author      Gaël Lejeune
      */
     public void addSongToAlbum();

     /**
      * Asks and add an existing audio to a chosen playlist
      * @see         Playlist
      * @see         Audio
      * @author      Gaël Lejeune
      */
     public void addSongToPlaylist();

     /**
      * Asks the informations and the audios to create and register a playlist
      * @see         Playlist
      * @author      Gaël Lejeune
      */
     public void createPlaylistFromExisting();

     /**
      * Asks the informations and the audios to remove a song from a playlist
      * @see         Playlist
      * @author      Gaël Lejeune
      */
     public void removeSongFromPlaylist();

     /**
      * Asks and delete the chosen playlist
      * @see         Playlist
      * @author      Gaël Lejeune
      */
     public void deletePlaylist();

     /**
      * Create XML files containing the registered playlists, elements, and albums using the class XML editor
      * @see         JMusicHubModel
      * @author      Gaël Lejeune
      */
     public void save();

     /**
      * Shows the different options to edit the database
      * @see         JMusicHubModel
      * @author      Gaël Lejeune
      */
     public void editDatabase();
 }
