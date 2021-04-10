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
  private static MusicThread music = null;

  private SingletonMusic() {
  }

  public static synchronized SingletonMusic getInstance(String ip,int port, Socket socket) {
    if((uniqueInstance==null)&&(music==null)) {
      uniqueInstance = new SingletonMusic();
      music = new MusicThread(ip,port,socket);
      music.start();
    }
    return uniqueInstance;
  }

  public void startMusic() {
    music.start();
  }

  public void pauseMusic() {
    music.pause();
  }

  public void restartMusic() {
    music.restart();
  }

  public void stopMusic() {
    music.stopThread();
    if(!music.isInterrupted()) {
      music = null;
      uniqueInstance = null;
    }
  }

  public boolean isRunning() {
    if(music==null) {
      return false;
    } else {
      return true;
    }
  }
}
