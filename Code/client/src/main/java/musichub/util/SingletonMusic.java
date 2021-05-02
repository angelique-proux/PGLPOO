/*
 * Class' name : SingletonMusic
 *
 * Description : Singleton to have only one music playback per client at the same time
 *
 * Version     : 1.0
 *
 * Date        : 10/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import java.io.*;
import java.net.*;
import musichub.business.*;
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
 * @author Steve Chauvreau-Manat and Angélique Proux
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
  private static IMusicThread music;


  /**
   * Constructor of SingletonMusic
   *
   * @author	Steve Chauvreau-Manat
   */
  private SingletonMusic() {}

  /**
   * Create and return an instance of SingletonMusic and start the MusicThread
   *
   * @param     ip Client's name
   * @param     port Server's open port
   * @param     musicThread classe used to listen to music
   * @return    SingletonMusic
   *
   * @author    Steve Chauvreau-Manat and Angélique Proux
   */
  public static synchronized SingletonMusic getInstance(String ip, int port, IMusicThread musicThread) {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
      music = musicThread;
      music.setMusicThread(ip, port);
      music.start();
    }
    return uniqueInstance;
  }

  /**
   * Pause the music
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat and Angélique Proux
   */
  public void pauseMusic() {
    if (music!=null){
      music.pause();
    }
  }

  /**
   * Restarts the music if it has been paused
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat and Angélique Proux
   */
  public void restartMusic() {
    if (music!=null){
      music.restart();
    }
  }

  /**
   * Stops the thread and music playback and reset SingletonMusic
   * @see       MusicThread
   * @author    Steve Chauvreau-Manat and Angélique Proux
   */
  public void stopMusic() {
    if (music!=null) {
      music.stopThread();
      music = null;
    }
    uniqueInstance = null;
  }

  /**
   * Check if MusicThread is interrupted or null
   * @return    boolean
   * @author    Steve Chauvreau-Manat and Angélique Proux
   */
  public boolean isRunning() {
    if(music==null) {
      return false;
    } else if (music.isFinished() == true) {
      music = null;
      return false;
    } else {
      return true;
    }
  }

}
