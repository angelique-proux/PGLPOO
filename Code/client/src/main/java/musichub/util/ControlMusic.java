/*
 * Interface's name : ControlMusic
 *
 * Description      : TODO
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */
package musichub.util;

import musichub.business.*;
import java.util.LinkedList;
/**
 * TODO
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public interface ControlMusic {

  /**
   * TODO
   */
  public void playMusicList();

  /**
   * TODO
   */
  public void nextMusic();

  /**
   * TODO
   */
  public void previousMusic();

  /**
   * TODO
   */
  public void reset();

  /**
   * TODO
   *
   * @return boolean //TODO
   */
  public boolean isFinished();

  /**
   * TODO
   */
  public void pauseMusic();

  /**
   * TODO
   */
  public void restartMusic();

  /**
   * TODO
   */
  public void stopMusic();
}
