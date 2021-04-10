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
 * SingletonMusic TODO
 *
 * Version : 1.0
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
  private static AudioServer music;

  private SingletonMusic() {
  }

  public static synchronized SingletonMusic getInstance(String ip,int port, Socket socket) {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
      music = new AudioServer(ip,port,socket);
      music.start();
    }
    return uniqueInstance;
  }

  public void startMusic() {
    music.start();
  }

  public void stopMusic() {
    music.stopThread();
    if(!music.isInterrupted()) {
      music = null;
      uniqueInstance = null;
    }
  }
}
