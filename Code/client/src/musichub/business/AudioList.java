/*
 * Interface's name : AudioList
 *
 * Description      : Interface represent object containing a list of song or audio
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package business;

import java.io.Serializable;

/** AudioList Interface represent object containing a list of song or audio
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune and Steve Chauvreau-Manat
 */
public interface AudioList extends Serializable		//must implement Serializable in order to be sent over a Socket
{

    /**
	 * Allow the user to add a given song to the AudioList
     *
     * @param       audio Audio to add
     *
	 * @see         Audio
     * @author      Gaël Lejeune and Steve Chauvreau-Manat
	 */
    abstract void addAudio(Audio audio);
}
