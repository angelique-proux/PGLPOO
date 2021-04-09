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

public class SingletonMusic {
  private static SingletonMusic uniqueInstance = null;
  private MusicThread music;

  private SingletonMusic() {
  }

  public static synchronized SingletonMusic getInstance() {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
    }
    return uniqueInstance;
  }

  public void startMusic(String ip,int port, Socket socket) {
    this.music = new MusicThread(ip,port,socket);
    music.start();
    if(!music.isAlive()) {
      music = null;
      uniqueInstance = null;
    }
  }

  public void pauseMusic() {
    music.pause();
  }

  public void restartMusic() {
    music.restart();
  }

  public void stopMusic() {
    music.stopThread();
    music.stop();
  }

  public boolean isRunning() {
    return music.isRunning();
  }
}
