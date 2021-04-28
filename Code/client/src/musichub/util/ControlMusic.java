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
package util;

import business.*;
import java.util.LinkedList;
/**
 * TODO
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public interface ControlMusic {

  public void playMusicList();

  public void nextMusic();

  public void previousMusic();

  public void reset();

  public boolean isFinished();

  public void pauseMusic();

  public void restartMusic();

  public void stopMusic();
}
