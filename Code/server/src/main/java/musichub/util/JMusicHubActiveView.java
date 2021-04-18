/*
 * Class' name : JMusicHubActiveView
 *
 * Description : JMusicHubActiveView is the view used when the server is in active mod
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package util;

import business.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * JMusicHubActiveView is the view used when the server is in active mod
 *
 * Version : 1.0
 *
 * @see       View
 * @author    Gaël Lejeune and Steve Chauvreau-Manat
 */
public class JMusicHubActiveView implements View {

    /**
     * Contains all the methods used by the View
     */
    private Controller controller;

    /**
     * JMusicHubPassiveView constructor
     *
     * @param     controller
     *
     * @author      Gaël Lejeune
     */
    public JMusicHubActiveView(Controller controller) {
        this.controller = controller;
    }

    /**
    * Execution of the JMusicHub program and interaction with the user using a terminal
    *
    * @author       Gaël Lejeune and Steve Chauvreau-Manat
    */
    public void display() {
        System.out.println("\n\nWelcome in JMusicHub,");
        System.out.println("Reading library...\n\n");
        System.out.println("Library loaded, type any command to begin using jMusicHub\nType \"h\" for help\n");
        Scanner scanner = new Scanner (System.in);
        int numberObjectByPage = 10;
        String[] command = new String[2];
        String input;
        while(true) {
            input = scanner.nextLine();
            if(input.contains(" ")) {
              command = input.split(" ");
            } else {
              command[0] = input;
            }
            switch(command[0]) {
                case "1" : //Show all Elements
                  int numberElements;
                  System.out.println("\t\t"+numberObjectByPage+" songs and audio books sorted by alphabetical order:\n");
                  LinkedList<Audio> audios = this.controller.getElements();
                  if(audios.size()>numberObjectByPage) {
                    if(command[1]==null) {
                      numberElements = numberObjectByPage;
                    } else {
                      numberElements = Integer.parseInt(command[1])*numberObjectByPage;
                    }
                  } else {
                    numberElements = audios.size();
                  }
                  for(int i=(numberElements-numberObjectByPage);i<numberElements;i++) {
                    if((i<audios.size())&&(i>=0)) {
                      System.out.println("\n"+audios.get(i)+"\n");
                    }
                  }
                  if(audios.size()>numberObjectByPage) {
                    int numberMaxPagesElements = audios.size()/numberObjectByPage+((audios.size()%numberObjectByPage==0)? 0 : 1);
                    System.out.println("\t\tPage ["+numberElements/10+"/"+numberMaxPagesElements+"]");
                    System.out.println("\nTo see the following pages: 1 [page]\ne.g. 2nd page: \'1 2\'");
                  }
                  break;

                case "2" : //Show all Albums
                  int numberAlbums;
                  System.out.println("\t\t"+numberObjectByPage+" albums sorted by alphabetical order:\n\n");
                  LinkedList<Album> albums = this.controller.getAlbums();
                  if(albums.size()>numberObjectByPage) {
                    if(command[1]==null) {
                      numberAlbums = numberObjectByPage;
                    } else {
                      numberAlbums = Integer.parseInt(command[1])*numberObjectByPage;
                    }
                  } else {
                    numberAlbums = albums.size();
                  }
                  for(int i=(numberAlbums-numberObjectByPage);i<numberAlbums;i++) {
                    if((i<albums.size())&&(i>=0)) {
                      System.out.println("\t"+albums.get(i)+"\n");
                    }
                  }
                  if(albums.size()>numberObjectByPage) {
                    int numberMaxPagesAlbums = albums.size()/numberObjectByPage+((albums.size()%numberObjectByPage==0)? 0 : 1);
                    System.out.println("\t\tPage ["+numberAlbums/10+"/"+numberMaxPagesAlbums+"]");
                    System.out.println("\nTo see the following pages: 1 [page]\ne.g. 2nd page: \'1 2\'");
                  }
                  break;

                case "3" : // Show all playlists
                  int numberPlaylists = Integer.parseInt(command[1]);
                  System.out.println("\t\tPlaylist names sorted by alphabetical order:\n\n");
                  LinkedList<Playlist> playlists = this.controller.getPlaylists();
                  if(playlists.size()>numberObjectByPage) {
                    if(command[1]==null) {
                      numberPlaylists = numberObjectByPage;
                    } else {
                      numberPlaylists = Integer.parseInt(command[1])*numberObjectByPage;
                    }
                  } else {
                    numberPlaylists = playlists.size();
                  }
                  for(int i=(numberPlaylists-numberObjectByPage);i<numberPlaylists;i++) {
                    if((i<playlists.size())&&(i>=0)) {
                      System.out.println("\t"+playlists.get(i)+"\n");
                    }
                  }
                  if(playlists.size()>numberObjectByPage) {
                    int numberMaxPagesPlaylists = playlists.size()/numberObjectByPage+((playlists.size()%numberObjectByPage==0)? 0 : 1);
                    System.out.println("\t\tPage ["+numberPlaylists/10+"/"+numberMaxPagesPlaylists+"]");
                    System.out.println("\nTo see the following pages: 1 [page]\ne.g. 2nd page: \'1 2\'");
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
                  for(int i=0;i<artistsName.size();i++) {
                    System.out.println("\n"+i+"- "+artistsName.get(i)+"\n");
                  }
                  System.out.println("\n"+artistsName.size()+"- None");
                  System.out.println("\n\nEnter a name's-artist number :");
                  int numberArtistName = Integer.parseInt(scanner.nextLine());
                  if(numberArtistName==artistsName.size()) {
                    break;
                  } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
                    System.out.println("\n\t\tAll the "+artistsName.get(numberArtistName)+"\'s songs :\n\n");
                    LinkedList<Song> songsToDisplayArtist = this.controller.getSongsByArtist(artistsName.get(numberArtistName));
                    for (int i = 0; i < songsToDisplayArtist.size(); i++) {
                        System.out.println("\n" + songsToDisplayArtist.get(i));
                    }
                    break;
                  } else {
                    System.out.println("\nInvalid number");
                    break;
                  }

                case "11": //Show all Authors
                  System.out.println("\t\tAll author's name :");
                  LinkedList<String> authorsName = this.controller.getAuthors();
                  for(int i=0;i<authorsName.size();i++) {
                    System.out.println("\n"+i+"- "+authorsName.get(i)+"\n");
                  }
                  System.out.println("\n"+authorsName.size()+"- None");
                  System.out.println("\n\nEnter a name's-author number :");
                  int numberAuthorName = Integer.parseInt(scanner.nextLine());
                  if(numberAuthorName==authorsName.size()) {
                    break;
                  } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
                    System.out.println("\n\t\tAll the "+authorsName.get(numberAuthorName)+"\'s audio books :\n\n");
                    LinkedList<AudioBook> booksToDisplayAuthor = this.controller.getAudioBooksByAuthor(authorsName.get(numberAuthorName));
                    for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
                        System.out.println("\n" + booksToDisplayAuthor.get(i));
                    }
                    break;
                  } else {
                    System.out.println("\nInvalid number");
                    break;
                  }

                case "12": //Show all Genres
                  System.out.println("\t\tAll genres :");
                  LinkedList<Genre> genres = this.controller.getGenres();
                  for(int i=0;i<genres.size();i++) {
                    System.out.println("\n"+i+"- "+genres.get(i)+"\n");
                  }
                  System.out.println("\n"+genres.size()+"- None");
                  System.out.println("\n\nEnter a genre's number :");
                  int numberGenre = Integer.parseInt(scanner.nextLine());
                  if(numberGenre==genres.size()) {
                    break;
                  } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
                    System.out.println("\n\t\tAll the "+genres.get(numberGenre)+"\'s songs :\n\n");
                    LinkedList<Song> songsToDisplayGenre = this.controller.getSongsByGenre(genres.get(numberGenre));
                    for (int i = 0; i < songsToDisplayGenre.size(); i++) {
                        System.out.println("\n" + songsToDisplayGenre.get(i));
                    }
                    break;
                  } else {
                    System.out.println("\nInvalid number");
                    break;
                  }

                case "13": //Show all Categories
                  System.out.println("\t\tAll categories :");
                  LinkedList<Category> categories = this.controller.getCategories();
                  for(int i=0;i<categories.size();i++) {
                    System.out.println("\n"+i+"- "+categories.get(i)+"\n");
                  }
                  System.out.println("\n"+categories.size()+"- None");
                  System.out.println("\n\nEnter a catagory's number :");
                  int numberCategory = Integer.parseInt(scanner.nextLine());
                  if(numberCategory==categories.size()) {
                    break;
                  } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
                    System.out.println("\n\t\tAll the "+categories.get(numberCategory)+"\'s audio books :\n\n");
                    LinkedList<AudioBook> audioBooksToDisplayCategory = this.controller.getAudioBooksByCategory(categories.get(numberCategory));
                    for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
                        System.out.println("\n" + audioBooksToDisplayCategory.get(i));
                    }
                    break;
                  } else {
                    System.out.println("\nInvalid number");
                    break;
                  }

                case "14": //Show all Languages
                  System.out.println("\t\tAll languages :");
                  LinkedList<Language> languages = this.controller.getLanguages();
                  for(int i=0;i<languages.size();i++) {
                    System.out.println("\n"+i+"- "+languages.get(i)+"\n");
                  }
                  System.out.println("\n"+languages.size()+"- None");
                  System.out.println("\n\nEnter a language's number :");
                  int numberLanguage = Integer.parseInt(scanner.nextLine());
                  if(numberLanguage==languages.size()) {
                    break;
                  } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
                    System.out.println("\n\t\tAll the "+languages.get(numberLanguage)+"\'s audio books :\n\n");
                    LinkedList<AudioBook> audioBooksToDisplayLanguage = this.controller.getAudioBooksByLanguage(languages.get(numberLanguage));
                    for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
                        System.out.println("\n" + audioBooksToDisplayLanguage.get(i));
                    }
                    break;
                  } else {
                    System.out.println("\nInvalid number");
                    break;
                  }

                case "edit" : // Change the content of the application
                  this.controller.editDatabase();
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
