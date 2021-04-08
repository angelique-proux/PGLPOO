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

  public static synchronized SingletonMusic getInstance(AudioInputStream musicToListen) {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
      music = new MusicThread(musicToListen);
    }
    return uniqueInstance;
  }

  public void playMusic() {
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
    music.endThread();
  }
}
