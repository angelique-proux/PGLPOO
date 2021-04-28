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
<<<<<<< HEAD:Code/server/src/musichub/util/musicplayer/ControlMusic.java
package util.musicplayer;
=======

package musichub.util.musicplayer;
>>>>>>> 981c0ab93f7f5e8e8d852a16d863093d4589381f:Code/server/src/main/java/musichub/util/musicplayer/ControlMusic.java

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
