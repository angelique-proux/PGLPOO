package business;

import java.util.UUID;


 /** Audio Abstract class representing an audio file
  *
  * @version 1.0
  *
  * @author Jean Michel D.
  */
public abstract class Audio {

	/**
     * Title of the audio file
     */
	protected String title;

	/**
     * Duration of the audio file
     */
    protected int duration;

	/**
     * Unique ID representing the audio file
     */
    protected UUID id;

	/**
     * Path of the audio file
     */
	protected String content;

	/**
     * Accessor of the title
     * @return      String containing the audio file's title
     * @author      Gaël Lejeune
     */
    public String getTitle() {
    	return this.title;
    }

	/**
     * Accessor of the duration
     * @return      Int containing the audio file's duration
     * @author      Gaël Lejeune
     */
    public int getDuration() {
    	return this.duration;
    }

	/**
     * Accessor of the unique ID
     * @return      UUID containing the audio file's unique ID
     * @author      Gaël Lejeune
     */
    public UUID getID() {
    	return this.id;
    }

	/**
     * Accessor of the content
     * @return      String containing the path of the audio file
     * @author      Gaël Lejeune
     */
    public String getContent() {
    	return this.content;
    }

}
