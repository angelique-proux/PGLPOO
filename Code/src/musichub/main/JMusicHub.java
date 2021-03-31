package main;

import business.*;
import util.*;
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
 * @author Gaël Lejeune and Steve Chauvreau-Manat
 */
public class JMusicHub{

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

    /**
     * Album constructor
     * Initialize all the attributes and fill the lists by reading the XML files
     *
     * @author Gaël Lejeune
     */
    public JMusicHub() {
        this.xmlEditor = new XMLReaderWriter();
        /* Load of all the XML files */
        try {
            this.playlists = this.xmlEditor.readPlaylistXML("files/playlists.xml");
            this.albums = this.xmlEditor.readAlbumXML("files/albums.xml");
            this.elements = this.xmlEditor.readElementXML("files/elements.xml");
        } catch (MissingFileException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Display the album list ordered by release date
     * @exception   Exception Thrown the given String cannot be converted to date.
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void displayAlbumByReleaseDate() throws Exception {
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
                System.out.println("\n"+albums.get(albumIndex) + "\n");
            }
        }
    }

    /**
     * Display the song list of an asked album, ordered by genre
     * @see         Album
     * @see         Song
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void displaySongByGenre() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nName of the album to display :");
        boolean found = false;
        Album album = this.albums.get(0);
        String title = scanner.nextLine();  /* Album title entered by the user */
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                found = true;
                album = this.albums.get(i);
            }
        }
        if (found) {
            LinkedList<Song> songs = album.getSongs();
            while(songs.size() > 0) {
                Genre genre = songs.get(0).getGenre();
                System.out.println("\nSongs with genre : " + genre);
                for (int i = 0; i < songs.size(); i++) {
                    if (songs.get(i).getGenre().equals(genre)) {
                        System.out.println("\n"+songs.get(i) + "\n");
                        songs.remove(i);
                        i--;
                    }
                }
            }
        } else {
            System.out.println("\nNo album found.\n");
        }
    }


    /**
     * Displays all the registered playlists
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public void displayPlaylists() {
        System.out.println("\nExisting playlists :\n");
        for (int i = 0; i < this.playlists.size(); i++) {
            System.out.println(this.playlists.get(i) + "\n");
        }
    }

    /**
     * Asks and displays an specific registered playlist
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public void displaySpecificPlaylist() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nName of the playlist :\n");
        boolean found = false;
        String name = scanner.nextLine();
        for (int i = 0; i < this.playlists.size(); i++) {
            if (this.playlists.get(i).getName().equals(name)) {
                System.out.println(this.playlists.get(i) + "\n");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No playlist found.\n");
        }
    }

    /**
     * Displays all the registered albums
     * @see         Album
     * @author      Gaël Lejeune
     */
    public void displayAlbums() {
        System.out.println("\nExisting albums :\n");
        for (int i = 0; i < this.albums.size(); i++) {
            System.out.println(this.albums.get(i) + "\n");
        }
    }

    /**
     * Asks and displays an specific registered album
     * @see         Album
     * @author      Gaël Lejeune
     */
    public void displaySpecificAlbum() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nName of the album to display :\n");
        boolean found = false;
        String title = scanner.nextLine();  /* Album title entered by the user */
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                System.out.println(this.albums.get(i) + "\n");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No album found.\n");
        }
    }


    /**
     * Displays all the registered audio elements
     * @see         Album
     * @author      Gaël Lejeune
     */
    public void displayElements() {
        System.out.println("\nExisting elements :\n");
        for (int i = 0; i < this.elements.size(); i++) {
            System.out.println(this.elements.get(i) + "\n");
        }
    }

    /**
     * Display all audio books ordered by author
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void displayAudioBooksByAuthor() {
        LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i) instanceof AudioBook) {
                audioBooks.add((AudioBook)this.elements.get(i));
            }
        }
        while(audioBooks.size() > 0) {
            String author = audioBooks.get(0).getAuthor();
            System.out.println("\nAuthor : " + author+"\n");
            for (int i = 0; i < audioBooks.size(); i++) {
                if (audioBooks.get(i).getAuthor().equals(author)) {
                    System.out.println(audioBooks.get(i) + "\n");
                    audioBooks.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * Asks the information about a song to add it to the audio elements
     * @see         Song
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addSong() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nTitle :");
        String title = scan.nextLine();

        System.out.println("\nArtist :");
        String artist = scan.nextLine();

        System.out.println("\nDuration (in seconds):");
        String duration = scan.nextLine();
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        while (pattern.matcher(duration).matches()) {
            System.out.println("\nPlease enter a valid duration :");
            duration = scan.nextLine();
        }
        int durationInt = Integer.parseInt(duration);

        UUID uuid = UUID.randomUUID();

        System.out.println("\nPath :");
        String content = scan.nextLine();
        File file = new File(content);
        while(!file.exists()) {
            System.out.println("\nNo such file path, try again.\nPath : ");
            content = scan.nextLine();
            file = new File(content);
        }
        System.out.println("\nGenre : (JAZZ/CLASSIQUE/HipHop/ROCK/POP/RAP/METAL)");
        String genre = scan.nextLine().toUpperCase();
        while(!genre.equals("JAZZ") && !genre.equals("CLASSIQUE") && !genre.equals("HIPHOP") && !genre.equals("ROCK") && !genre.equals("POP") && !genre.equals("RAP") && !genre.equals("METAL")) {
            System.out.println("\nWrong genre, try again.\nGenre : ");
            genre = scan.nextLine();
            file = new File(content);
        }
        Genre musicGenre = Genre.valueOf(genre);
        Song songToAdd = new Song(title, artist, durationInt, uuid, content, musicGenre);
        System.out.println("\nPress \"y\" to add the following song, press anything else to abort : \n" + songToAdd);
        String answer = scan.nextLine();
        if(answer.equals("y")) {
            this.elements.add(songToAdd);
            System.out.println("\nSong registered.\n");
        }
    }

    /**
     * Asks the information about an audio book to add it to the audio elements
     * @see         AudioBook
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addAudioBook() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nTitle :");
        String title = scan.nextLine();

        System.out.println("\nAuthor :");
        String author = scan.nextLine();

        System.out.println("\nDuration (in seconds):");
        String duration = scan.nextLine();
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        while (pattern.matcher(duration).matches()) {
            System.out.println("\nPlease enter a valid duration :");
            duration = scan.nextLine();
        }
        int durationInt = Integer.parseInt(duration);

        UUID uuid = UUID.randomUUID();

        System.out.println("\nPath :");
        String content = scan.nextLine();
        File file = new File(content);
        while(!file.exists()) {
            System.out.println("\nNo such file path, try again.\nPath : ");
            content = scan.nextLine();
            file = new File(content);
        }

        System.out.println("\nLanguage : (FRANCAIS/ANGLAIS/ITALIEN/ESPAGNOL/ALLEMAND)");
        String language = scan.nextLine().toUpperCase();
        while(!language.equals("FRANCAIS") && !language.equals("ANGLAIS") && !language.equals("ITALIEN") && !language.equals("ESPAGNOL") && !language.equals("ALLEMAND")) {
            System.out.println("\nWrong language, try again.\nLanguage : ");
            language = scan.nextLine();
            file = new File(content);
        }
        Language audioBookLanguage = Language.valueOf(language);

        System.out.println("\nCategory : (JEUNESSE/ROMAN/THEATRE/DISCOURS/DOCUMENTAIRE)");
        String category = scan.nextLine().toUpperCase();
        while(!category.equals("JEUNESSE") && !category.equals("ROMAN") && !category.equals("THEATRE") && !category.equals("DISCOURS") && !category.equals("DOCUMENTAIRE")) {
            System.out.println("\nWrong category, try again.\nCategory : ");
            category = scan.nextLine();
            file = new File(content);
        }
        Category audioBookCategory = Category.valueOf(category);

        AudioBook audioBookToAdd = new AudioBook(title, author, durationInt, uuid, content, audioBookLanguage, audioBookCategory);
        System.out.println("\nPress \"y\" to add the following audio book, press anything else to abort : \n" + audioBookToAdd);
        String answer = scan.nextLine();
        if(answer.equals("y")) {
            System.out.println("\nAudio book registered.");
        }
    }

    /**
     * Asks the information about an album to add it to the audio elements
     * @see         Album
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
     */
    public void addAlbum() {
        Scanner scan = new Scanner(System.in);
        LinkedList<Song> songs = new LinkedList<Song>();
        System.out.println("\nAdding an album :\nTitle :");
        String title = scan.nextLine();

        System.out.println("\nArtist :");
        String artist = scan.nextLine();

        System.out.println("\nDuration (in seconds):");
        String duration = scan.nextLine();
        Pattern durationPattern = Pattern.compile(".*[^0-9].*");
        while (durationPattern.matcher(duration).matches()) {
            System.out.println("\nPlease enter a valid duration :");
            duration = scan.nextLine();
        }
        int durationInt = Integer.parseInt(duration);

        UUID uuid = UUID.randomUUID();

        System.out.println("\nDate (DD/MM/YYYY):");
        String date = scan.nextLine();
        Pattern datePattern = Pattern.compile("([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}");
        while (!datePattern.matcher(date).matches()) {
            System.out.println("\nPlease enter a valid date :");
            date = scan.nextLine();
        }

        System.out.println("\nHow many music is there in this album :");
        int numberMusic = Integer.parseInt(scan.nextLine());
        for(int i=0; i < numberMusic; i++) {
            this.addSong();
        }
        this.albums.add(new Album(title,artist,durationInt,date,uuid,songs));
    }

    /**
     * Asks and add an existing song to a chosen album
     * @see         Album
     * @see         Song
     * @author      Gaël Lejeune
     */
    public void addSongToAlbum() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nEnter the name of an album :");
        boolean found = false;
        String title = scanner.nextLine();
        int albumIndex = 0;
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                albumIndex = i;
                found = true;
            }
        }
        if (found) {
            System.out.println("\nEnter the name of the songs you wish to add or press enter to finish : ");
            String songname = "1";
            while (!songname.equals("")) {
                songname = scanner.nextLine();
                boolean songFound = false;
                if (!songname.equals("")) {
                    for (int i = 0; i < this.elements.size(); i++) {
                        if (this.elements.get(i).getTitle().equals(songname)) {
                            this.albums.get(albumIndex).addAudio(this.elements.get(i));
                            System.out.println("\nAdded song : " + this.elements.get(i));
                            songFound = true;
                        }
                    }
                    if (!songFound) {
                        System.out.println("\nNo song found.");
                    }
                }
            }
        } else {
            System.out.println("\nNo album found.");
        }
    }

    /**
     * Asks and add an existing audio to a chosen playlist
     * @see         Playlist
     * @see         Audio
     * @author      Gaël Lejeune
     */
    public void addSongToPlaylist() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nEnter the name of a playlist :");
        boolean found = false;
        String name = scanner.nextLine();
        int playlistIndex = 0;
        for (int i = 0; i < this.playlists.size(); i++) {
            if (this.playlists.get(i).getName().equals(name)) {
                playlistIndex = i;
                found = true;
            }
        }
        if (found) {
            System.out.println("\nEnter the name of the songs you wish to add or press enter to finish : ");
            String songname = "1";
            while (!songname.equals("")) {
                songname = scanner.nextLine();
                boolean songFound = false;
                for (int i = 0; i < this.elements.size(); i++) {
                    if (this.elements.get(i).getTitle().equals(songname)) {
                        this.playlists.get(playlistIndex).addAudio(this.elements.get(i));
                        System.out.println("\nAdded song : " + this.elements.get(i));
                        songFound = true;
                    }
                }
                if (!songFound) {
                    System.out.println("\nNo song found.");
                }
            }
        } else {
            System.out.println("\nNo playlist found.");
        }
    }

    /**
     * Asks the informations and the audios to create and register a playlist
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public void createPlaylistFromExisting() {
        Scanner scanner = new Scanner (System.in);

        System.out.println("\nName of your new playlist :");
        String name = scanner.nextLine();

        System.out.println("\nGenerating UUID");
        UUID uuid = UUID.randomUUID();

        LinkedList<Audio> audios = new LinkedList<Audio>();
        System.out.println("\nEnter the name of the songs you wish to add or press enter to finish : ");
        String songname = "1";
        while (!songname.equals("")) {
            songname = scanner.nextLine();
            boolean found = false;
            for (int i = 0; i < this.elements.size(); i++) {
                if (this.elements.get(i).getTitle().equals(songname)) {
                    audios.add(this.elements.get(i));
                    System.out.println("\nAdded song : " + this.elements.get(i));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("\nNo song found.");
            }
        }
        if (audios.size() == 0) {
            System.out.println("\nEmpty playlist, abort creation.");
            return;
        }
        this.playlists.add(new Playlist(name, uuid, audios));
        System.out.println("\nPlaylist created");
    }

    /**
     * Asks and delete the chosen playlist
     * @see         Playlist
     * @author      Gaël Lejeune
     */
    public void deletePlaylist() {
        Scanner scanner = new Scanner (System.in);
        System.out.println("\nName of the playlist to delete :");
        String name = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < this.playlists.size(); i++) {
            if (this.playlists.get(i).getName().equals(name)) {
                this.playlists.remove(i);
                found = true;
                System.out.println("\nSuccessfully removed");
            }
            if (!found) {
                System.out.println("\nNo playlist found");
            }
        }
    }

    /**
     * Create XML files containing the registered playlists, elements, and albums using the class XML editor
     * @see         XMLReaderWriter
     * @author      Gaël Lejeune
     */
    public void save() {
        this.xmlEditor.writeElementXML("files/elements.xml", this.elements);
        this.xmlEditor.writeAlbumXML("files/albums.xml", this.albums);
        this.xmlEditor.writePlaylistXML("files/playlists.xml", this.playlists);
    }

    /**
     * Displays information and help about various commands
     * @author Steve Chauvreau-Manat
     */
    public void help() {
        System.out.println("- c : add a new song");
        System.out.println("- l : add a new audio book");
        System.out.println("- a : add a new album");
        System.out.println("- + : add an existing song to an album");
        System.out.println("- p : Creation of a new playlist from existing songs and audiobooks");
        System.out.println("- - : delete a playlist");
        System.out.println("- s : save playlists, albums, songs and audiobooks in the respective xml files");

        System.out.println("- d : display all the registered elements");
        System.out.println("    - dab : display registered audio book ordered by author");
        System.out.println("    - da : display all the registered albums");
        System.out.println("    - dsa : display a specific registered album");
        System.out.println("    - dad : display registered albums ordered by release date");
        System.out.println("    - dag : display a registered album songs ordered by genre");
        System.out.println("    - dp : display all registered playlists");
        System.out.println("    - dsp : display a specific registered playlist");
        System.out.println("- q : exit the jMusicHub");
        System.out.println("- h : help with details of previous commands");
    }

    /**
     * Execution of the JMusicHub program and interaction with the user using a terminal
     * @param       args Arguments of the function
     * @author Gaël Lejeune and Steve Chauvreau-Manat
     */
    public static void main(String[] args) {
        System.out.println("\n\nWelcome in JMusicHub,");
        System.out.println("Reading library...\n\n");
        JMusicHub jMusicHub = new JMusicHub();
        System.out.println("Library loaded, type any command to begin using jMusicHub\nType \"h\" for help\n");
        Scanner scanner = new Scanner (System.in);
        while(true) {
            String command = scanner.nextLine();
            switch (command) {
                case "c":
                    jMusicHub.addSong();
                    break;
                case "a":
                    jMusicHub.addAlbum();
                    break;
                case "+":
                    jMusicHub.addSongToAlbum();
                    break;
                case "l":
                    jMusicHub.addAudioBook();
                    break;
                case "p":
                    jMusicHub.createPlaylistFromExisting();
                    break;
                case "-":
                    jMusicHub.deletePlaylist();
                    break;
                case "s":
                    jMusicHub.save();
                    break;
                case "d":
                    jMusicHub.displayElements();
                    break;
                case "dab":
                    jMusicHub.displayAudioBooksByAuthor();
                    break;
                case "da":
                    jMusicHub.displayAlbums();
                    break;
                case "dsa":
                    jMusicHub.displaySpecificAlbum();
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
                    System.out.println("\n\nAll your unsaved changes will be lost, continue ?(Press \"y\" to quit or anything else to abort.)");
                    String confirm = scanner.nextLine();
                    if (confirm.equals("y")) {
                        System.out.println("\n\nThank you to trust jMusicHub to manage your audio files :)");
                        return;
                    }
                default:
                    System.out.println("\nWrong command, press \"h\" for help.");
                    break;
            }
        }
    }
}
