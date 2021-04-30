/*
 * Class' name : ControlMusicList
 *
 * Description : TODO
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import java.net.Socket;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;


/**
 * TODO
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public class ControlMusicList implements ControlMusic {
  /**
   * TODO
   */
  private int port;

  /**
   * TODO
   */
  private String ip;

  /**
   * TODO
   */
  private SingletonMusic singletonMusic;

  /**
   * TODO
   *
   * @param   ip //TODO
   * @param	  port Server's open port
   *
   * @author	Angélique Proux
   */
  public ControlMusicList(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void playMusicList() {
    this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port);
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void reset() {
    this.singletonMusic.stopMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void pauseMusic() {
    singletonMusic.pauseMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void restartMusic() {
    singletonMusic.restartMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void stopMusic() {
    singletonMusic.stopMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public boolean isFinished() {
    if (singletonMusic==null) {
      return true;
    } else {
      return !singletonMusic.isRunning();
    }
  }
}
