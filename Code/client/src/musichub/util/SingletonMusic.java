/*
 * Nom de classe : SingletonMusic
 *
 * Description   : TODO
 *
 * Version       : 1.0
 *
 * Date          : 10/04/2021
 *
 * Copyright     : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package util;

import java.io.*;
import java.net.*;
import business.*;
import javax.sound.sampled.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * TODO
 *
 * @version 1.0
 *
 * @author TODO
 */
public class SingletonMusic {

  /**
   * TODO
   */
  private static SingletonMusic uniqueInstance = null;

  /**
   * TODO
   */
  private MusicThread music;

  /**
   * Constructor of SingletonMusic
   *
   * @author	TODO
   */
  private SingletonMusic() {}

  /**
   * Acts as the singleton constructor and return an instance of SingletonMusic
   *
   * @return    SingletonMusic
   *
   * @author    Gaël Lejeune
   */
  public static synchronized SingletonMusic getInstance() {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
    }
    return uniqueInstance;
  }

  /**
   * TODO
   *
   * @param     ip TODO
   * @param     port TODO
   * @param     socket TODO
   *
   * @author    TODO
   */
  public void startMusic(String ip,int port, Socket socket) {
    this.music = new MusicThread(ip,port,socket);
    music.start();
    if(!music.isAlive()) {
      music = null;
      uniqueInstance = null;
    }
  }

  /**
   * TODO
   *
   * @author    TODO
   */
  public void pauseMusic() {
    music.pause();
  }

  /**
   * TODO
   *
   * @author    TODO
   */
  public void restartMusic() {
    music.restart();
  }

  /**
   * TODO
   *
   * @author    TODO
   */
  public void stopMusic() {
    music.stopThread();
    music.stop();
  }

  /**
   * TODO
   *
   * @author    TODO
   */
  public boolean isRunning() {
    return music.isRunning();
  }

  /**
   * TODO
   *
   * @author    TODO
   */
  public void checkInstance() {
    if(this.music.isRunning()) {
      this.music = null;
      this.uniqueInstance = null;
    }
  }
}
