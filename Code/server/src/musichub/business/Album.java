package business;

import java.util.LinkedList;
import java.util.UUID;


/** Album class representing an audio album
 *
 * Version : 1.6
 *
 * Date : 30/02/2001
 *
 * @author       Gaël Lejeune
 */
public class Album implements AudioList {

    /**
     * Title of the album
     */
    private String title;

    /**
     * Artist of the album
     */
    private String artist;

    /**
     * Duration of the album
     */
    private int duration;

    /**
     * Release date of the album
     */
    private String releaseDate;

    /**
     * Unique ID identifying the album
     */
    private UUID id;

    /**
     * Song list of the album
     * @see Song
     */
    private LinkedList<Song> songs;

    /**
     * Album constructor
     *
     * @param       title Title of the album
     * @param       artist Artist of the album
     * @param       duration Duration of the album
     * @param       releaseDate Release date of the album
     * @param       id Unique id of the album
     * @param       songs List of the album songs
     *
     * @author      Gaël Lejeune
     */
    public Album(String title, String artist, int duration, String releaseDate, UUID id, LinkedList<Song> songs) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.id = id;
        this.songs = songs;
    }

    /**
     * Accessor of the title
     * @return      String containing the album's title
     * @author      Gaël Lejeune
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Accessor of the artist
     * @return      String containing the album's artist
     * @author      Gaël Lejeune
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Accessor of the release date
     * @return      String containing the album's release date
     * @author      Gaël Lejeune
     */
    public String getReleaseDate() {
        return this.releaseDate;
    }

    /**
     * Accessor of the song list
     * @return      LinkedList containing the album's song list
     * @author      Gaël Lejeune
     */
    public LinkedList<Song> getSongs() {
        return this.songs;
    }

    /**
     * Accessor of the duration
     * @return      int containing the album's duration
     * @author      Gaël Lejeune
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Accessor of the unique id
     * @return      UUID containing the album's unique id
     * @author      Gaël Lejeune
     */
    public UUID getID() {
        return this.id;
    }

    /**
     * Override of the toString java method
     * @return      String containing the album's informations
     * @author      Gaël Lejeune
     */
    public String toString() {
        String str;
        str = "Album " + this.title + "\nBy " + this.artist + "\nDuration " + this.duration + "\nReleased " + this.releaseDate + "\nSongs : ";
        for (int i = 0; i < this.songs.size(); i++) {
            str += "\n" + this.songs.get(i);
        }
        return str;
    }

    /**
     * Allow the user to add a given song to the album
     * @param       audio Audio to add
     * @see         Audio
     * @author      Gaël Lejeune
     */
    @Override
    public void addAudio(Audio audio) {
        this.songs.add((Song)audio);
    }

}
