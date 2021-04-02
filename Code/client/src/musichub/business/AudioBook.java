package business;

import java.util.UUID;


 /** AudioBook Class representing an audio book and it's caracteristics
  *
  * @version 1.0
  *
  * @see Audio
  * @author Gaël Lejeune
  */
public class AudioBook extends Audio {
	/**
     * Author of the book
     */
	private String author;

	/**
	 * language of the book
	 * @see language
	 */
    private Language language;

	/**
	 * category of the book
	 * @see category
	 */
    private Category category;

	/**
     * AudioBook constructor
     *
     * @param       title Title of the book
     * @param       author Author of the book
     * @param       duration Duration of the audio book
	 * @param       id Unique id of the audio book
	 * @param       content Path of the audio book's file
	 * @param       language Language of the audio book
	 * @param       category Category of the audio book
     *
     * @author      Gaël Lejeune
     */
    public AudioBook(String title, String author, int duration, UUID id, String content, Language language, Category category) {
    	this.title = title;
    	this.author = author;
    	this.duration = duration;
    	this.id = id;
    	this.content = content;
    	this.language = language;
    	this.category = category;
    }

	/**
     * Accessor of the author
     * @return      String containing the audio book's author
     * @author      Gaël Lejeune
     */
    public String getAuthor() {
    	return this.author;
    }

	/**
     * Accessor of the Language
     * @return      language Language of the audio book
	 * @see			Language
     * @author      Gaël Lejeune
     */
    public Language getLanguage() {
    	return this.language;
    }

	/**
     * Accessor of the Language
     * @return      category Category of the audio book
	 * @see			Category
     * @author      Gaël Lejeune
     */
    public Category getCategory() {
    	return this.category;
    }

	/**
     * Override of the toString java method
     * @return      String containing the audio book's informations
     * @author      Gaël Lejeune
     */
	public String toString() {
		return this.title + " written by " + this.author + " : " + this.duration;
	}

}
