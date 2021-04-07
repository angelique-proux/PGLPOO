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
import java.net.*;

/** JMusicHub Class is the main class of the JMusicHub program.
*
*
* Version : 1.0
*
* Date : 30/02/2001
*
* @author Gaël Lejeune and Steve Chauvreau-Manat (based on the work of Angélique Proux & Manelle Nouar)
*/
public class JMusicHubController implements Controller {

    /**
    * XML editor allowing to read and write XML files
    * @see JMusicHubModel
    */
    private JMusicHubModel model;

    private View view;

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
    public JMusicHubController(JMusicHubModel model) {
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
    * @author Gaël Lejeune
    */
    public JMusicHubController(JMusicHubModel model, Socket socket) {
        this.model = model;
        this.view = new JMusicHubPassiveView(this,socket);

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
    * Displays all the registered audio elements
    * @see         Album
    * @author      Gaël Lejeune
    */
    public LinkedList<Audio> getElements() {
        return this.elements;
        // System.out.println("\nExisting elements :\n");
        // for (int i = 0; i < this.elements.size(); i++) {
        //     System.out.println(this.elements.get(i) + "\n");
        // }
    }

    /**
    * Displays all the registered albums
    * @see         Album
    * @author      Gaël Lejeune
    */
    public LinkedList<Album> getAlbums() {
        return this.albums;
        // System.out.println("\nExisting albums :\n");
        // for (int i = 0; i < this.albums.size(); i++) {
        //     System.out.println(this.albums.get(i) + "\n");
        // }
    }

    /**
    * Displays all the registered playlists
    * @see         Playlist
    * @author      Gaël Lejeune
    */
    public LinkedList<Playlist> getPlaylists() {
        return this.playlists;
        // System.out.println("\nExisting playlists :\n");
        // for (int i = 0; i < this.playlists.size(); i++) {
        //     System.out.println(this.playlists.get(i) + "\n");
        // }
    }

    /**
    * Asks and displays an specific registered album
    * @see         Album
    * @author      Gaël Lejeune
    */
    public Album getSpecificAlbum(String title) {
        boolean found = false; /* Album title entered by the user */
        for (int i = 0; i < this.albums.size(); i++) {
            if (this.albums.get(i).getTitle().equals(title)) {
                // System.out.println(this.albums.get(i) + "\n");
                found = true;
                return this.albums.get(i);
            }
        }
        return null;
    }

    /**
    * Asks and displays an specific registered playlist
    * @see         Playlist
    * @author      Gaël Lejeune
    */
    public Playlist getSpecificPlaylist(String name) {
        boolean found = false;
        for (int i = 0; i < this.playlists.size(); i++) {
            if (this.playlists.get(i).getName().equals(name)) {
                // System.out.println(this.playlists.get(i) + "\n");
                found = true;
                return this.playlists.get(i);
            }
        }
        return null;
    }

    /**
    * Display all audio books ordered by author
    * @see         AudioBook
    * @author      Gaël Lejeune and Steve Chauvreau-Manat
    */
    public LinkedList<Song> getSongsByArtist(String author) {
        LinkedList<Song> songs = new LinkedList<Song>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i) instanceof AudioBook) {
                songs.add((Song)this.elements.get(i));
            }
        }
        while(songs.size() > 0) {
            for (int i = 0; i < songs.size(); i++) {
                if (!songs.get(i).getArtist().equals(author)) {
                    songs.remove(i);
                    i--;
                    found = true;
                }
            }
        }
        if(!found) {
            return null;
        } else {
            return songs;
        }
    }

    /**
    * Display all audio books ordered by author
    * @see         AudioBook
    * @author      Gaël Lejeune and Steve Chauvreau-Manat
    */
    public LinkedList<AudioBook> getAudioBooksByAuthor(String author) {
        LinkedList<AudioBook> audioBooks = new LinkedList<AudioBook>();
        boolean found = false;
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i) instanceof AudioBook) {
                audioBooks.add((AudioBook)this.elements.get(i));
            }
        }
        while(audioBooks.size() > 0) {
            for (int i=0;i<audioBooks.size();i++) {
                if (!audioBooks.get(i).getAuthor().equals(author)) {
                    audioBooks.remove(i);
                    i--;
                    found = true;
                }
            }
        }
        if(!found) {
            return null;
        } else {
            return audioBooks;
        }
    }

    /**
    * Display the album list ordered by release date
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
    * Display the song list of an asked album, ordered by genre
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
    * Displays information and help about various commands
    * @author Steve Chauvreau-Manat
    */
    public String help() {
        return "\n- 1 : display all registered elements"
        +"\n- 2 : display all registered albums"
        +"\n- 3 : display all registered playlists"
        +"\n- 4 : display a specific registered album"
        +"\n- 5 : display a specific registered playlist"
        +"\n- 6 : display all artist's songs"
        +"\n- 7 : display all author's audiobooks"
        +"\n- 8 : display all albums release by date"
        +"\n- 9 : display all songs sorted by genre"
        +"\n- 10 : exit the jMusicHub"
        +"\n- h : help with details of previous commands";
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
    * @see         JMusicHubModel
    * @author      Gaël Lejeune
    */
    public void save() {
        this.model.writeElementXML("files/elements.xml", this.elements);
        this.model.writeAlbumXML("files/albums.xml", this.albums);
        this.model.writePlaylistXML("files/playlists.xml", this.playlists);
    }

    public void editDatabase() {
        while(true) {
            System.out.println(" What you want to do ? Hit a button to make changes.");
            System.out.println("h : show the help\t\t|\tm : return to the menu");
            System.out.println("Don't forget to save all your modifications.");
            Scanner scanner = new Scanner (System.in);
            String editCommand = scanner.nextLine();
            switch (editCommand) {
                case "h":
                System.out.println("Here are the commands you can use to make changes:");
                System.out.println("c : add a song\t\t\t|\tl : add an audiobook");
                System.out.println("a : add an album\t\t|\tp : create a new playlist");
                System.out.println("+ : add a song to an album\t|\t- : delete a playlist");
                System.out.println("s : save all modifications");
                break;

                case "c":// ajouter une chanson
                this.addSong();
                break;

                case "a":// ajouter un album
                this.addAlbum();
                break;

                case "+"://ajout d'une chanson existante à un album existant
                this.addSongToAlbum();
                break;

                case "l":// ajouter un livre audio
                this.addAudioBook();
                break;

                case "p":
                this.createPlaylistFromExisting();
                break;

                case "-":
                this.deletePlaylist();
                break;

                case "s":
                this.save();
                break;
            }
            System.out.println("\n\n\n");
        }
    }

    /**
    * Execution of the JMusicHub program and interaction with the user using a terminal
    * @param       args Arguments of the function
    * @author Gaël Lejeune and Steve Chauvreau-Manat
    */
    /*public static void main(String[] args) {
    System.out.println("\n\nWelcome in JMusicHub,");
    System.out.println("Reading library...\n\n");
    JMusicHub jMusicHub = new JMusicHub();
    System.out.println("Library loaded, type any command to begin using jMusicHub\nType \"h\" for help\n");
    Scanner scanner = new Scanner (System.in);
    while(true) {
    String command = scanner.nextLine();
    switch (command) {
    case "1" : // Show albums
    System.out.println("\t\t Album titles sorted by them date:");
    LinkedList<Album> albumList = jMusicHub.getAlbumByReleaseDate();
    for (int i = 0; i < albumList.size(); i++) {
    System.out.println("\n"+albumList.get(i) + "\n");
}
break;

case "2" : // Show songs
System.out.println("\t\t Song titles sorted by them genre:");
//TODO
jMusicHub.getSongByGenre();
break;

case "3" : // Show audiobooks
System.out.println("\t\t AudioBook titles sorted by them author:");
//TODO
jMusicHub.displayAudioBooksByAuthor();
break;

case "4" : // Show playlists
System.out.println("\t\t Playlist names sorted by alphabetical order:");
System.out.println("\nExisting playlists :\n");
for (int i = 0; i < jMusicHub.getPlaylists().size(); i++) {
System.out.println(jMusicHub.getPlaylists().get(i) + "\n");
}
break;

case "5" : // Select an album
System.out.println(jMusicHub.getSpecificAlbum().toString() + "\n");
break;

case "6" : // Select a playlist
System.out.println(jMusicHub.getSpecificPlaylist() + "\n");
break;

case "7" : // Select all the song of an artist
jMusicHub.getSongsByArtist();
// selectArtist7(util, sc);
break;

case "8" : // Select all the audiobooks of an author
jMusicHub.getAudioBooksByAuthor();
break;

case "9" : // Change the content of the application
jMusicHub.editDatabase(jMusicHub);
break;

case "10" :// Quit the application
System.out.println("\t\t Thank you for you time, have a nice day!\n");
System.out.println("\t\t\t\t\tSigned by nope.\n\n\n");
System.exit(0);
break;
case "h" ://Display the help
jMusicHub.help();
break;
default:
System.out.println("\nWrong command, press \"h\" for help.");
break;
}
}
}*/
}
