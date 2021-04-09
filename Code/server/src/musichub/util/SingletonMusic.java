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
  private AudioServer music;

  private SingletonMusic() {
  }

  public static synchronized SingletonMusic getInstance() {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
    }
    return uniqueInstance;
  }

  public void startMusic(String ip,int port, Socket socket) {
    this.music = new AudioServer(ip,port,socket);
    music.start();
  }

  public void checkInstance() {
    if(this.music.isRunning()) {
      this.music = null;
      this.uniqueInstance = null;
    }
  }
}
