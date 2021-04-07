package util;

import java.io.*;
import java.net.*;
import business.*;

public class SingletonMusic {
  private static SingletonMusic uniqueInstance = null;
  private MusicThread music;

  private SingletonMusic() {}

  public static synchronized SingletonMusic getInstance() {
    if(uniqueInstance==null) {
      uniqueInstance = new SingletonMusic();
    }
    return uniqueInstance;
  }

  public void playMusic(Audio audio) {
    this.music = new MusicThread(audio);
    this.music.start();
  }

  public void stopMusic() {
    this.music.endThread();
    this.music = null;
    uniqueInstance = null;
  }
}
