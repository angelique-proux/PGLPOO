package util;

import business.Audio;

import java.io.*;
import java.net.*;

public class SingletonMusic {
  private static SingletonMusic uniqueInstance = null;
  private MusicThread music;

  private SingletonMusic() {}

  public synchronized SingletonMusic getInstance(Audio audio) {
    if(this.uniqueInstance==null) {
      this.uniqueInstance = new SingletonMusic();
      this.music = new MusicThread(audio);
    }
    return this.uniqueInstance;
  }

  public void playMusic() {
    this.music.start();
  }

  public void stopMusic() {
    this.music.endThread();
    this.music = null;
    this.uniqueInstance = null;
  }
}
