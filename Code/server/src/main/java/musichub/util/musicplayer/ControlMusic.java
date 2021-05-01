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
   * TODO
   *
   * @param     audio one audio
   */
  public void addAudio(Audio audio);

  /**
   * TODO
   *
   * @param     audios audio list
   */
  public void addAudios(LinkedList<Audio> audios);

  /**
   * TODO
   *
   * @param     songs song list
   */
  public void addSongs(LinkedList<Song> songs);

  /**
   * TODO
   *
   * @param     audioBooks audio book list
   */
  public void addAudioBooks(LinkedList<AudioBook> audioBooks);

  /**
   * TODO
   *
   * @param     numberAudio number of audio to listen to
   */
  public void playMusicList(int numberAudio);

  /**
   * TODO
   */
  public void reset();

  /**
   * TODO
   */
  public void stopMusic();
}
