/*
 * Class' name : Audio
 *
 * Description : Abstract class representing an audio file
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package business;

import java.util.UUID;
import java.io.Serializable;

 /** Audio Abstract class representing an audio file
  *
  * @version 1.0
  *
  * @author Gaël Lejeune
  */
public abstract class Audio implements Serializable		//must implement Serializable in order to be sent over a Socket
{

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
