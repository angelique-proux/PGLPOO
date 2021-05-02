package musichub.util;

/**
 * Interface for thread that manage music playback
 *
 * @version 1.0
 *
 * @see Thread
 * @author Angélique Proux
 */

public interface IMusicThread {

    /**
     * set MusicThread parameters
     *
     * @param   ip    Server's ip
     * @param   port  Server's open port
     *
     * @author    Angélique Proux
     */
    public void setMusicThread(String ip, int port);

    /**
     * Plays the audio until its end or user's action
     * @see         Thread
     * @author      Steve Chauvreau-Manat and Angélique Proux
     */
    public void run();

    /**
     * method herited from Thread
     */
    public void start();

    /**
     * Pause the music
     * @author      Steve Chauvreau-Manat
     */
    public void pause();

    /**
     * Restarts the music if it has been paused
     * @author      Steve Chauvreau-Manat
     */
    public void restart();

    /**
     * Stops the thread and music playback
     * @author      Steve Chauvreau-Manat
     */
    public void stopThread();

    /**
     * Returns if the audio is finished or not
     * @return      boolean     status of audio
     * @author      Angélique Proux
     */
    public boolean isFinished();

}
