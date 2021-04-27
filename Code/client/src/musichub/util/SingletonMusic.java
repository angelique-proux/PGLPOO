/*
 * Class' name : SingletonMusic
 *
 * Description : Singleton to have only one music playback per client at the same time
 *
 * Version     : 1.0
 *
 * Date        : 10/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package musichub.util;

import java.io.*;
import java.net.*;
import jmusichub.business.*;
import javax.sound.sampled.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Singleton to manage audio-playback threads
 *
 * @version 1.0
 *
 * @author Steve Chauvreau-Manat
 */
public class SingletonMusic {

  /**
   * Singleton Instance
   */
  private static SingletonMusic uniqueInstance = null;

  /**
   * Class that allows to manage the music
   * @see MusicThread
   */
  private static MusicThread music;

  /**
   * Constructor of SingletonMusic
   *
   * @author	Steve Chauvreau-Manat
   */
  private SingletonMusic() {}

  /**
   * Create and return an instance of SingletonMusic and start the MusicThread
   * @return    SingletonMusic
   * @author    Steve Chauvreau-Manat
   */
  public static synchronized SingletonMusic getInstance(String ip, int port, Socket socket) {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
      music = new MusicThread(ip,port,socket);
      music.start();
    }
    return uniqueInstance;
  }

  /**
   * Pause the music
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat
   */
  public void pauseMusic() {
    music.pause();
  }

  /**
   * Restarts the music if it has been paused
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat
   */
  public void restartMusic() {
    music.restart();
  }

  /**
   * Stops the thread and music playback and reset SingletonMusic
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat
   */
  public void stopMusic() {
    music.stopThread();
    if(!music.isInterrupted()) {
      music = null;
      uniqueInstance = null;
    }
  }

  /**
   * Check if MusicThread is interrupted
   * @return    boolean
   * @author    Steve Chauvreau-Manat
   */
  public boolean isRunning() {
    if(music==null) {
      return false;
    } else {
      return true;
    }
  }
}
