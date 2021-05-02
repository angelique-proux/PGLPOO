/*
 * Interface's name : ControlMusic
 *
 * Description      : Interface for classes that control the playback of one audio at a time
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */
package musichub.util;

import musichub.business.*;
import java.util.LinkedList;

/**
 * Interface for classes which control the play of the music
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public interface ControlMusic {

  /**
   * Create a singleton to receive an audio and play it
   *
   * @author  Angélique Proux
   */
  public void playMusicList();

  /**
   * Return the status of the singleton Instance
   *
   * @return boolean
   *
   * @author  Angélique Proux
   */
  public boolean isFinished();

  /**
   * Pause the music
   *
   * @author  Angélique Proux
   */
  public void pauseMusic();

  /**
   * Restart the music
   *
   * @author  Angélique Proux
   */
  public void restartMusic();

  /**
   * Stop the music
   *
   * @author  Angélique Proux
   */
  public void stopMusic();
}
