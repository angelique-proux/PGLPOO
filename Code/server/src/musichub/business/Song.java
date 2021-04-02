package business;

import java.util.UUID;

/** Song Class representing a song and it's caracteristics
 *
 * @version 1.0
 *
 * @see Audio
 * @author Gaël Lejeune
 */
public class Song extends Audio {
    /**
     * Genre of the song
     * @see Genre
     */
    private Genre genre;

    /**
     * Artist of the song
     */
    private String artist;

    /**
     * Song constructor
     *
     * @param       title Title of the book
     * @param       artist Artist of the song
     * @param       duration Duration of the song
	 * @param       id Unique id of the song
	 * @param       content Path of the song's file
	 * @param       genre Genre of the song
     *
     * @author      Gaël Lejeune
     */
    public Song(String title, String artist, int duration, UUID id, String content, Genre genre) {
    	this.title = title;
    	this.artist = artist;
    	this.duration = duration;
    	this.id = id;
    	this.content = content;
    	this.genre = genre;
    }

    /**
     * Accessor of the genre
     * @return      Genre of the song
     * @see         Genre
     * @author      Gaël Lejeune
     */
    public Genre getGenre() {
    	return this.genre;
    }

    /**
     * Accessor of the artist
     * @return      String containing the song's artist
     * @author      Gaël Lejeune
     */
    public String getArtist() {
    	return this.artist;
    }

    /**
     * Override of the toString java method
     * @return      String containing the songs's informations
     * @author      Gaël Lejeune
     */
    public String toString() {
        return this.title + " by " + this.artist
        + "\nDuration : " + this.duration
        + "\nGenre : " + this.genre;
    }
}
