package business;

/** AudioList Interface represent object containing a list of song or audio
 *
 * Version : 1.0
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune and Steve Chauvreau-Manat
 */
public interface AudioList {

    /**
	 * Allow the user to add a given song to the AudioList
     * @param       audio Audio to add
	 * @see         Audio
     * @author Gaël Lejeune and Steve Chauvreau-Manat
	 */
    abstract void addAudio(Audio audio);
}
