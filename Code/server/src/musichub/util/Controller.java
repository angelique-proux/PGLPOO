/*
 * Interface's name : Controller
 *
 * Description      : Interface is the interface representing a base controller
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

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


/**
 * Controller Interface is the interface representing a base controller
 *
 * Version : 1.0
 *
 * @author  Gaël Lejeune
 */
 public interface Controller {

     /**
     * Displays all the registered audio elements
     * @see         Album
     * @author      Gaël Lejeune
     */
     public LinkedList<Audio> getElements();

     /**
     * Displays all the registered albums
     * @see         Album
     * @author      Gaël Lejeune
     */
     public LinkedList<Album> getAlbums();

     /**
     * Displays all the registered playlists
     * @see         Playlist
     * @author      Gaël Lejeune
     */
     public LinkedList<Playlist> getPlaylists();

     /**
     * Asks and displays an specific registered album
     * @see         Album
     * @author      Gaël Lejeune
     */
     public Album getSpecificAlbum(String title);

     /**
     * Asks and displays an specific registered playlist
     * @see         Playlist
     * @author      Gaël Lejeune
     */
     public Playlist getSpecificPlaylist(String name);

     public LinkedList<String> getArtists();

     public LinkedList<String> getAuthors();

     public LinkedList<Genre> getGenres();

     public LinkedList<Category> getCategories();

     public LinkedList<Language> getLanguages();

     /**
     * Display all audio books ordered by author
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<Song> getSongsByArtist(String artist);

     /**
     * Display all audio books ordered by genre
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<Song> getSongsByGenre(Genre genre);

     /**
     * Display all audio books ordered by author
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<AudioBook> getAudioBooksByLanguage(Language language);

     /**
     * Display all audio books ordered by author
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<AudioBook> getAudioBooksByCategory(Category category);

     /**
     * Display all audio books ordered by author
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<AudioBook> getAudioBooksByAuthor(String author);

     /**
     * Display the album list ordered by release date
     * @exception   Exception Thrown the given String cannot be converted to date.
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<Album> getAlbumByReleaseDate() throws Exception;

     /**
     * Display the song list of an asked album, ordered by genre
     * @see         Album
     * @see         Song
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
     public LinkedList<Song> getSongByGenre(String title);

     /**
     * Displays information and help about various commands
     * @author Steve Chauvreau-Manat
     */
     public String helpPassive();

     /**
     * Displays information and help about various commands
     * @author Steve Chauvreau-Manat
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

     public void editDatabase();
 }
