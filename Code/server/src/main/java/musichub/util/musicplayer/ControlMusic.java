/*
 * Interface's name : ControlMusic
 *
 * Description      : TODO
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.musicplayer;

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
   * Add an audio to the list
   *
   * @param     audio to add
   *
   * @author  Angélique Proux
   */
  public void addAudio(Audio audio);

  /**
   * Add audios to the list
   *
   * @param     audios to add
   *
   * @author  Angélique Proux
   */
  public void addAudios(LinkedList<Audio> audios);

  /**
   * Add songs to the list
   *
   * @param     songs to add
   *
   * @author  Angélique Proux
   */
  public void addSongs(LinkedList<Song> songs);

  /**
   * Add audiobooks to the list
   *
   * @param     audioBooks to
   *
   * @author  Angélique Proux
   */
  public void addAudioBooks(LinkedList<AudioBook> audioBooks);

  /**
   * play the current list of audios
   *
   * @param numberAudio of the playlist added and wanted to listen to
   *
   * @author  Angélique Proux
   */
  public void playMusicList(int numberAudio);

  /**
   * clear the list
   *
   * @author  Angélique Proux
   */
  public void reset();

  /**
   * stop the listened music
   *
   * @author  Angélique Proux
   */
  public void stopMusic();
}
