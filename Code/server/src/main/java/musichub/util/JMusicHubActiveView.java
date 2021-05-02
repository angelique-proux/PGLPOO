/*
 * Class' name : JMusicHubActiveView
 *
 * Description : JMusicHubActiveView is the view used when the server is in active mod
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
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
     * @param     controller TODO
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
        Scanner scanner = new Scanner(System.in);
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
                  showAllElements(command[1],numberObjectByPage);
                  break;

                case "2" : //Show all Albums
                  showAllAlbums(command[1],numberObjectByPage);
                  break;

                case "3" : // Show all playlists
                  showAllPlaylists(command[1],numberObjectByPage);
                  break;

                case "4" : // Select and show an album
                  selectShowAlbum(scanner);
                  break;

                case "5" : // Select and show a playlist
                  selectShowPlaylist(scanner);
                  break;

                case "6" : // Select and show all selected artist's songs
                  selectShowArtistSongs(scanner,command[1],numberObjectByPage);
                  break;

                case "7" : // Select all author's audiobooks
                  selectAuthorAudiobooks(scanner,command[1],numberObjectByPage);
                  break;

                case "8" : // Show all albums release by date
                  showAllAlbumsReleaseByDate(command[1],numberObjectByPage);
                  break;

                case "9" : // Show all songs sorted by genre
                  showAllSongsSortedByGenre(scanner);
                  break;

                case "10": //Show all Artists
                  showAllArtists(scanner);
                  break;

                case "11": //Show all Authors
                  showAllAuthors(scanner);
                  break;

                case "12": //Show all Genres
                  showAllGenres(scanner);
                  break;

                case "13": //Show all Categories
                  showAllCategories(scanner);
                  break;

                case "14": //Show all Languages
                  showAllLanguages(scanner);
                  break;

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

    /**
     * Display in the console all elements
     *
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         Audio
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllElements(String command,int numberObjectByPage) {
      int numberElements;
      LinkedList<Audio> audios = this.controller.getElements();
      if(audios!=null) {
        if(audios.size()>numberObjectByPage) {
          if(command==null) {
            numberElements = numberObjectByPage;
          } else {
            Pattern pattern = Pattern.compile(".*[^0-9].*");
            if(pattern.matcher(command).matches()) {
              numberElements = Integer.parseInt(command)*numberObjectByPage;
            } else {
              System.out.println("The second parameter isn't a number, please the next time enter a number");
              numberElements = numberObjectByPage;
            }
          }
        } else {
          numberElements = audios.size();
          numberObjectByPage = audios.size();
        }
        System.out.println("\t\t"+numberObjectByPage+" songs and audio books sorted by alphabetical order:\n");
        for(int i=(numberElements-numberObjectByPage);i<numberElements;i++) {
          if((i<audios.size())&&(i>=0)) {
            System.out.println("\n"+audios.get(i)+"\n");
          }
        }
        if(audios.size()>numberObjectByPage) {
          int numberMaxPagesElements = audios.size()/numberObjectByPage+((audios.size()%numberObjectByPage==0)? 0 : 1);
          System.out.println("\t\tPage ["+numberElements/10+"/"+numberMaxPagesElements+"]");
          System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
        }
      } else {
        System.out.println("\nNo elements found in the database");
      }
    }

    /**
     * Display in the console all albums
     *
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         Album
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllAlbums(String command,int numberObjectByPage) {
      int numberAlbums;
      LinkedList<Album> albums = this.controller.getAlbums();
      if(albums!=null) {
        if(albums.size()>numberObjectByPage) {
          if(command==null) {
            numberAlbums = numberObjectByPage;
          } else {
            Pattern pattern = Pattern.compile(".*[^0-9].*");
            if(pattern.matcher(command).matches()) {
              numberAlbums = Integer.parseInt(command)*numberObjectByPage;
            } else {
              System.out.println("The second parameter isn't a number, please the next time enter a number");
              numberAlbums = numberObjectByPage;
            }
          }
        } else {
          numberAlbums = albums.size();
          numberObjectByPage = albums.size();
        }
        System.out.println("\t\t"+numberObjectByPage+" albums sorted by alphabetical order:\n\n");
        for(int i=(numberAlbums-numberObjectByPage);i<numberAlbums;i++) {
          if((i<albums.size())&&(i>=0)) {
            System.out.println("\t"+albums.get(i)+"\n");
          }
        }
        if(albums.size()>numberObjectByPage) {
          int numberMaxPagesAlbums = albums.size()/numberObjectByPage+((albums.size()%numberObjectByPage==0)? 0 : 1);
          System.out.println("\t\tPage ["+numberAlbums/10+"/"+numberMaxPagesAlbums+"]");
          System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
        }
      } else {
        System.out.println("\nNo albums found in the database");
      }
    }

    /**
     * Display in the console all playlists
     *
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         Playlist
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllPlaylists(String command, int numberObjectByPage) {
      int numberPlaylists;
      LinkedList<Playlist> playlists = this.controller.getPlaylists();
      if(playlists!=null) {
        if(playlists.size()>numberObjectByPage) {
          if(command==null) {
            numberPlaylists = numberObjectByPage;
          } else {
            Pattern pattern = Pattern.compile(".*[^0-9].*");
            if(pattern.matcher(command).matches()) {
              numberPlaylists = Integer.parseInt(command)*numberObjectByPage;
            } else {
              System.out.println("The second parameter isn't a number, please the next time enter a number");
              numberPlaylists = numberObjectByPage;
            }
          }
        } else {
          numberPlaylists = playlists.size();
          numberObjectByPage = playlists.size();
        }
        System.out.println("\t\tPlaylist names sorted by alphabetical order:\n\n");
        for(int i=(numberPlaylists-numberObjectByPage);i<numberPlaylists;i++) {
          if((i<playlists.size())&&(i>=0)) {
            System.out.println("\t"+playlists.get(i)+"\n");
          }
        }
        if(playlists.size()>numberObjectByPage) {
          int numberMaxPagesPlaylists = playlists.size()/numberObjectByPage+((playlists.size()%numberObjectByPage==0)? 0 : 1);
          System.out.println("\t\tPage ["+numberPlaylists/10+"/"+numberMaxPagesPlaylists+"]");
          System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
        }
      } else {
        System.out.println("\nNo playlist found in the database");
      }
    }

    /**
     * Select an album and show detail
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Album
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void selectShowAlbum(Scanner scanner) {
      System.out.println("Enter an album's title :");
      String albumName = scanner.nextLine();
      Album album = this.controller.getSpecificAlbum(albumName);
      if(album!=null) {
        System.out.println("\n\t\tSelected album "+albumName+" :\n\n");
        System.out.println(album + "\n");
      } else {
        System.out.println("\nNo album found with this name in the database");
      }
    }

    /**
     * Select a playlist and show detail
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Playlist
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void selectShowPlaylist(Scanner scanner) {
        System.out.println("Enter a playlist's name :");
        String playlistName = scanner.nextLine();
        Playlist playlist = this.controller.getSpecificPlaylist(playlistName);
        if(playlist!=null) {
          System.out.println("\n\t\tThe selected playlist "+playlistName+" :\n\n");
          System.out.println(playlist+"\n");
        } else {
          System.out.println("\nNo playlist found with this name in the database");
        }
    }

    /**
     * Select a artist and show all his/her songs
     *
     * @param       scanner scan a specific input to receive information from the user
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         Song
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void selectShowArtistSongs(Scanner scanner,String command,int numberObjectByPage) {
      int numberSongs;
      System.out.println("Enter an artist's name :");
      String artistName = scanner.nextLine();
      LinkedList<Song> songsToDisplay = this.controller.getSongsByArtist(artistName);
      if(songsToDisplay!=null) {
        if(songsToDisplay.size()>numberObjectByPage) {
          if(command==null) {
            numberSongs = numberObjectByPage;
          } else {
            Pattern pattern = Pattern.compile(".*[^0-9].*");
            if(pattern.matcher(command).matches()) {
              numberSongs = Integer.parseInt(command)*numberObjectByPage;
            } else {
              System.out.println("The second parameter isn't a number, please the next time enter a number");
              numberSongs = numberObjectByPage;
            }
          }
        } else {
          numberSongs = songsToDisplay.size();
          numberObjectByPage = songsToDisplay.size();
        }
        System.out.println("\n\t\t+"+numberObjectByPage+" "+artistName+"\'s songs :\n\n");
        for (int i = (numberSongs-numberObjectByPage); i < numberSongs; i++) {
          if((i<songsToDisplay.size())&&(i>=0)) {
            System.out.println(songsToDisplay.get(i)+"\n");
          }
        }
        if(songsToDisplay.size()>numberObjectByPage) {
          int numberMaxPages = songsToDisplay.size()/numberObjectByPage+((songsToDisplay.size()%numberObjectByPage==0)? 0 : 1);
          System.out.println("\t\tPage ["+numberSongs/10+"/"+numberMaxPages+"]");
          System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
        }
      } else {
        System.out.println("\nNo "+artistName+" song's found in the database");
      }
    }

    /**
     * Select a author and show all his/her audio books
     *
     * @param       scanner scan a specific input to receive information from the user
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         AudioBook
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void selectAuthorAudiobooks(Scanner scanner,String command,int numberObjectByPage) {
      int numberAudioBooks;
      System.out.println("Enter an author's name :");
      String authorName = scanner.nextLine();
      LinkedList<AudioBook> booksToDisplay = this.controller.getAudioBooksByAuthor(authorName);
      if(booksToDisplay!=null) {
        if(booksToDisplay.size()>numberObjectByPage) {
          if(command==null) {
            numberAudioBooks = numberObjectByPage;
          } else {
            Pattern pattern = Pattern.compile(".*[^0-9].*");
            if(pattern.matcher(command).matches()) {
              numberAudioBooks = Integer.parseInt(command)*numberObjectByPage;
            } else {
              System.out.println("The second parameter isn't a number, please the next time enter a number");
              numberAudioBooks = numberObjectByPage;
            }
          }
        } else {
          numberAudioBooks = booksToDisplay.size();
          numberAudioBooks = booksToDisplay.size();
        }
        System.out.println("\n\t\t"+numberObjectByPage+" "+authorName+"\'s audio books :\n\n");
        for (int i =(numberAudioBooks-numberObjectByPage);i<numberAudioBooks;i++) {
          if((i<booksToDisplay.size())&&(i>=0)) {
            System.out.println(booksToDisplay.get(i)+"\n");
          }
        }
        if(booksToDisplay.size()>numberObjectByPage) {
          int numberMaxPages = booksToDisplay.size()/numberObjectByPage+((booksToDisplay.size()%numberObjectByPage==0)? 0 : 1);
          System.out.println("\t\tPage ["+numberAudioBooks/10+"/"+numberMaxPages+"]");
          System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
        }
      } else {
        System.out.println("\nNo "+authorName+" audio book's found in the database");
      }
    }

    /**
     * Show all albums release by date
     *
     * @param       command the command's second parameter
     * @param       numberObjectByPage number of objects displayed per page on the console
     *
     * @see         Album
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllAlbumsReleaseByDate(String command,int numberObjectByPage) {
      int numberAlbums;
      try {
        LinkedList<Album> albumList = this.controller.getAlbumByReleaseDate();
        if(albumList!=null) {
          if(albumList.size()>numberObjectByPage) {
            if(command==null) {
              numberAlbums = numberObjectByPage;
            } else {
              Pattern pattern = Pattern.compile(".*[^0-9].*");
              if(pattern.matcher(command).matches()) {
                numberAlbums = Integer.parseInt(command)*numberObjectByPage;
              } else {
                System.out.println("The second parameter isn't a number, please the next time enter a number");
                numberAlbums = numberObjectByPage;
              }
            }
          } else {
            numberAlbums = albumList.size();
            numberObjectByPage = albumList.size();
          }
          System.out.println("\t\t"+numberObjectByPage+" date-release-sorted albums :");
          for (int i=(numberAlbums-numberObjectByPage);i<numberAlbums;i++) {
            if((i<albumList.size())&&(i>=0)) {
              System.out.println(albumList.get(i)+"\n");
            }
          }
          if(albumList.size()>numberObjectByPage) {
            int numberMaxPages = albumList.size()/numberObjectByPage+((albumList.size()%numberObjectByPage==0)? 0 : 1);
            System.out.println("\t\tPage ["+numberAlbums/10+"/"+numberMaxPages+"]");
            System.out.println("\nTo see the following pages: 1 [page's number]\ne.g. 2nd page: \'1 2\'");
          }
        } else {
          System.out.println("\nNo album found in the database");
        }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }

    /**
     * Show all songs sorted by genre
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Song
     * @see         Genre
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllSongsSortedByGenre(Scanner scanner) {
      System.out.println("Enter an album's title : ");
      LinkedList<Song> songs = this.controller.getSongByGenre(scanner.nextLine());
      if(songs!=null) {
        System.out.println("\n\t\tSongs' titles sorted by genre:");
        Genre currentGenre = songs.get(0).getGenre();
        System.out.println("\nSongs with Genre : "+ currentGenre);
        for (int i = 0; i < songs.size() ; i++) {
          if(!songs.get(i).getGenre().equals(currentGenre)) {
            currentGenre = songs.get(i).getGenre();
            System.out.println("\nSongs with Genre : "+ currentGenre+"\n");
          }
          System.out.println(songs.get(i));
        }
      } else {
        System.out.println("\nNo song found in the database");
      }
    }

    /**
     * Show all artists and select one to display all his/her songs
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Song
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllArtists(Scanner scanner) {
      System.out.println("\t\tAll artists' name :");
      LinkedList<String> artistsName = this.controller.getArtists();
      if(artistsName!=null) {
        for(int i=0;i<artistsName.size();i++) {
          System.out.println("\n"+i+"- "+artistsName.get(i)+"\n");
        }
        System.out.println("\n"+artistsName.size()+"- None");
        System.out.println("\n\nEnter a name's-artist number :");
        int numberArtistName = Integer.parseInt(scanner.nextLine());
        if(numberArtistName==artistsName.size()) {
          System.out.println("Return to the main menu");
        } else if((numberArtistName<artistsName.size())&&(numberArtistName>=0)) {
          LinkedList<Song> songsToDisplayArtist = this.controller.getSongsByArtist(artistsName.get(numberArtistName));
          if(songsToDisplayArtist!=null) {
            System.out.println("\n\t\tAll the "+artistsName.get(numberArtistName)+"\'s songs :\n\n");
            for (int i = 0; i < songsToDisplayArtist.size(); i++) {
                System.out.println("\n" + songsToDisplayArtist.get(i));
            }
          } else {
            System.out.println("\nNo music from this artist in the database");
          }
        } else {
          System.out.println("\nInvalid number");
        }
      } else {
        System.out.println("\nNo artist with this name found in the database");
      }
    }

    /**
     * Show all authors and select one to display all his/her audio books
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         AudioBook
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllAuthors(Scanner scanner) {
      System.out.println("\t\tAll author's name :");
      LinkedList<String> authorsName = this.controller.getAuthors();
      if(authorsName!=null) {
        for(int i=0;i<authorsName.size();i++) {
          System.out.println("\n"+i+"- "+authorsName.get(i)+"\n");
        }
        System.out.println("\n"+authorsName.size()+"- None");
        System.out.println("\n\nEnter a name's-author number :");
        int numberAuthorName = Integer.parseInt(scanner.nextLine());
        if(numberAuthorName==authorsName.size()) {
          System.out.println("Return to the main menu");
        } else if((numberAuthorName<authorsName.size())&&(numberAuthorName>=0)) {
          LinkedList<AudioBook> booksToDisplayAuthor = this.controller.getAudioBooksByAuthor(authorsName.get(numberAuthorName));
          if(booksToDisplayAuthor!=null) {
            System.out.println("\n\t\tAll the "+authorsName.get(numberAuthorName)+"\'s audio books :\n\n");
            for (int i = 0; i < booksToDisplayAuthor.size(); i++) {
              System.out.println("\n" + booksToDisplayAuthor.get(i));
            }
          } else {
            System.out.println("\nNo audio book from this author in the database");
          }
        } else {
          System.out.println("\nInvalid number");
        }
      } else {
        System.out.println("\nNo author with this name found in the database");
      }
    }

    /**
     * Show all genres and select one to display all its songs
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Genre
     * @see         Song
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllGenres(Scanner scanner) {
      System.out.println("\t\tAll genres :");
      LinkedList<Genre> genres = this.controller.getGenres();
      if(genres!=null) {
        for(int i=0;i<genres.size();i++) {
          System.out.println("\n"+i+"- "+genres.get(i)+"\n");
        }
        System.out.println("\n"+genres.size()+"- None");
        System.out.println("\n\nEnter a genre's number :");
        int numberGenre = Integer.parseInt(scanner.nextLine());
        if(numberGenre==genres.size()) {
          System.out.println("Return to the main menu");
        } else if((numberGenre<genres.size())&&(numberGenre>=0)) {
          LinkedList<Song> songsToDisplayGenre = this.controller.getSongsByGenre(genres.get(numberGenre));
          if(songsToDisplayGenre!=null) {
            System.out.println("\n\t\tAll the "+genres.get(numberGenre)+"\'s songs :\n\n");
            for (int i = 0; i < songsToDisplayGenre.size(); i++) {
              System.out.println("\n" + songsToDisplayGenre.get(i));
            }
          } else {
            System.out.println("\nNo song with this genre found in the database");
          }
        } else {
          System.out.println("\nInvalid number");
        }
      } else {
        System.out.println("\nNo genre find in the database");
      }
    }

    /**
     * Show all categories and select one to display all its audio books
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Category
     * @see         AudioBook
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllCategories(Scanner scanner) {
      System.out.println("\t\tAll categories :");
      LinkedList<Category> categories = this.controller.getCategories();
      if(categories!=null) {
        for(int i=0;i<categories.size();i++) {
          System.out.println("\n"+i+"- "+categories.get(i)+"\n");
        }
        System.out.println("\n"+categories.size()+"- None");
        System.out.println("\n\nEnter a catagory's number :");
        int numberCategory = Integer.parseInt(scanner.nextLine());
        if(numberCategory==categories.size()) {
          System.out.println("Return to the main menu");
        } else if((numberCategory<categories.size())&&(numberCategory>=0)) {
          LinkedList<AudioBook> audioBooksToDisplayCategory = this.controller.getAudioBooksByCategory(categories.get(numberCategory));
          if(audioBooksToDisplayCategory!=null) {
            System.out.println("\n\t\tAll the "+categories.get(numberCategory)+"\'s audio books :\n\n");
            for (int i = 0; i < audioBooksToDisplayCategory.size(); i++) {
                System.out.println("\n" + audioBooksToDisplayCategory.get(i));
            }
          } else {
            System.out.println("\nNo audio book with this category found in the database");
          }
        } else {
          System.out.println("\nInvalid number");
        }
      } else {
        System.out.println("\nNo category found in the database");
      }
    }

    /**
     * Show all languages and select one to display all its audio books
     *
     * @param       scanner scan a specific input to receive information from the user
     *
     * @see         Language
     * @see         AudioBook
     * @see         JMusicHubController
     * @author      Steve CHAUVREAU-MANAT
     */
    private void showAllLanguages(Scanner scanner) {
      System.out.println("\t\tAll languages :");
      LinkedList<Language> languages = this.controller.getLanguages();
      if(languages!=null) {
        for(int i=0;i<languages.size();i++) {
          System.out.println("\n"+i+"- "+languages.get(i)+"\n");
        }
        System.out.println("\n"+languages.size()+"- None");
        System.out.println("\n\nEnter a language's number :");
        int numberLanguage = Integer.parseInt(scanner.nextLine());
        if(numberLanguage==languages.size()) {
          System.out.println("Return to the main menu");
        } else if((numberLanguage<languages.size())&&(numberLanguage>=0)) {
          LinkedList<AudioBook> audioBooksToDisplayLanguage = this.controller.getAudioBooksByLanguage(languages.get(numberLanguage));
          if(audioBooksToDisplayLanguage!=null) {
            System.out.println("\n\t\tAll the "+languages.get(numberLanguage)+"\'s audio books :\n\n");
            for (int i = 0; i < audioBooksToDisplayLanguage.size(); i++) {
                System.out.println("\n" + audioBooksToDisplayLanguage.get(i));
            }
          } else {
            System.out.println("\nNo audio book with this language found in the database");
          }
        } else {
          System.out.println("\nInvalid number");
        }
      } else {
        System.out.println("\nNo language found in the database");
      }
    }
}
