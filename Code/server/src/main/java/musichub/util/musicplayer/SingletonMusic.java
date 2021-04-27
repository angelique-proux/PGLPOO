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

package musichub.util.musicplayer;

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
 * @author Steve Chauvreau-Manat
 */
public class SingletonMusic {

  /**
   * Singleton Instance
   */
  private static SingletonMusic uniqueInstance = null;

  /**
   * Class that allows to manage audio
   * @see AudioServerThread
   */
  private static AudioServerThread music;

  /**
   * Constructor of SingletonMusic
   *
   * @author	Steve Chauvreau-Manat
   */
  private SingletonMusic() {
  }

  /**
   * Create and return an instance of SingletonMusic and start AudioServerThread
   *
   * @return    SingletonMusic
   * @param     audio //TODO
   * @param     port //TODO
   * @param     socket //TODO
   *
   * @author    Steve Chauvreau-Manat
   */
  public static synchronized SingletonMusic getInstance(String audio, int port, Socket socket) {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
      music = new AudioServerThread(audio,port,socket);
      music.start();
    }
    return uniqueInstance;
  }

  /**
   * Stops the thread and music playback and reset SingletonMusic
   * @see       AudioServerThread
   * @author    Steve Chauvreau-Manat
   */
  public void stopMusic() {
    music.stopThread();
    if(!music.isInterrupted()) {
      music = null;
      uniqueInstance = null;
    }
  }
}
