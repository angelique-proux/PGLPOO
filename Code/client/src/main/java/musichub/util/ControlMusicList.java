/*
 * Class' name : ControlMusicList
 *
 * Description : Class that controls the playback of one audio at a time
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import java.net.Socket;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;


/**
 * Classe which control the play of the music
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public class ControlMusicList implements ControlMusic {
  /**
   * Server's open port
   */
  private int port;

  /**
   * Client's name
   */
  private String ip;

  /**
   * SingletonMusic Instance
   */
  private SingletonMusic singletonMusic;

  /**
   * Constructor of ControlMusicList
   *
   * @param   ip Client's name
   * @param	  port Server's open port
   *
   * @author	Angélique Proux
   */
  public ControlMusicList(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  /**
   * Create a singleton to receive an audio and play it
   *
   * @author  Angélique Proux
   */
  public void playMusicList() {
    this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port, new MusicThread());
  }

  /**
   * Pause the music
   *
   * @author  Angélique Proux
   */
  public void pauseMusic() {
    singletonMusic.pauseMusic();
  }

  /**
   * Restart the music
   *
   * @author  Angélique Proux
   */
  public void restartMusic() {
    singletonMusic.restartMusic();
  }

  /**
   * Stop the music
   *
   * @author  Angélique Proux
   */
  public void stopMusic() {
    singletonMusic.stopMusic();
  }

  /**
   * Return the status of the singleton Instance
   *
   * @return boolean
   *
   * @author  Angélique Proux
   */
  public boolean isFinished() {
    if (singletonMusic==null) {
      return true;
    } else {
      return !singletonMusic.isRunning();
    }
  }
}
