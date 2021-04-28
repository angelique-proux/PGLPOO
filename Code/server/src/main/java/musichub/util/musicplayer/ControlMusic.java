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
  public void addAudio(Audio audio);

  public void addAudios(LinkedList<Audio> audios);

  public void addSongs(LinkedList<Song> songs);

  public void addAudioBooks(LinkedList<AudioBook> audioBooks);

  public void playMusicList();

  public void nextMusic();

  public void previousMusic();

  public void reset();

  public boolean isFinished();
}
