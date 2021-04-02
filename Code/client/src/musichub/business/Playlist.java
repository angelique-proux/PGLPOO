package business;

import java.util.LinkedList;
import java.util.UUID;

/** Playlist class representing a playlist containing audios
 *
 * Version : 1.0
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune
 */
public class Playlist implements AudioList {

	/**
     * Name of the playlist
     */
	private String name;

	/**
     * Unique ID identifying the playlist
     */
	private UUID id;

	/**
     * Audio list of the playlist
     * @see Audio
     */
	private LinkedList<Audio> audios = new LinkedList<Audio>();

	/**
     * Playlist constructor
     *
     * @param       name Name of the album
     * @param       id Unique id of the album
     * @param       audios List of the album audios
     *
     * @author      Gaël Lejeune
     */
	public Playlist(String name, UUID id, LinkedList<Audio> audios) {
		this.name = name;
		this.id = id;
		this.audios = audios;
	}

	/**
     * Accessor of the name
     * @return      String containing the playlist's name
     * @author      Gaël Lejeune
     */
	public String getName() {
		return this.name;
	}

	/**
     * Accessor of the audio list
     * @return      LinkedList containing the playlist's audio list
     * @author      Gaël Lejeune
     */
	public LinkedList<Audio> getAudios() {
		return this.audios;
	}

	/**
     * Accessor of the unique id
     * @return      UUID containing the playlist's unique id
     * @author      Gaël Lejeune
     */
	public UUID getID() {
    	return this.id;
    }

	/**
     * Override of the toString java method
     * @return      String containing the playlist's informations
     * @author      Gaël Lejeune
     */
	public String toString() {
		String str;
		str = "Playlist " + this.name;
		for (int i = 0; i < this.audios.size(); i++) {
			str += "\n" + this.audios.get(i);
		}
		return str;
	}

	/**
	 * Allow the user to add a given audio file to the playlist
	 * @param       audio Audio to add
	 * @see         Audio
	 * @author      Gaël Lejeune
	 */
	@Override
	public void addAudio(Audio audio) {
        this.audios.add(audio);
    }
}
