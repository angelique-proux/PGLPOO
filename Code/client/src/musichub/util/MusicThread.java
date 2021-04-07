package util;

import business.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MusicThread extends Thread {
  private Audio audio;
  private boolean running = true;
  private boolean nouvAudio = true;
  private AudioInputStream musicToListen;
  private SourceDataLine line;
  private boolean stopMusic = false;
  private byte[] samples = new byte[4096];

  public MusicThread(Audio audio) {
    this.audio = audio;
    this.musicToListen = getAudioInputStreamFromFile(audio.getContent());
    this.initialiseLine();
  }

  public void run() {
    Scanner scanner = new Scanner (System.in);
    int count;
    if(audio instanceof Song) {
      System.out.println((Song) audio);
    } else if(audio instanceof AudioBook) {
      System.out.println((AudioBook) audio);
    }
    try {
      this.line.open(musicToListen.getFormat());
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
    this.line.start();

    while(running) {
      try {
        count = this.musicToListen.read(samples, 0, samples.length);
        if (count==-1) {
          this.endThread();
          break;
        }
        if(!this.stopMusic) {
          this.line.write(samples, 0, count);
        }
      } catch(IOException ex) {
        ex.printStackTrace();
      }
    }
    this.line.drain();
    this.line.stop();
  }

  public void endThread() {
    this.running = false;
  }

  public void pauseRestart() {
    if(this.stopMusic) {
      this.stopMusic = false;
    } else {
      this.stopMusic = true;
    }
  }

  public static AudioInputStream getAudioInputStreamFromFile(String filepath) {
    if (filepath == null) {
      throw new IllegalArgumentException("filename is null");
    }

    try {
      // first try to read file from local file system
      File file = new File(filepath);
      if (file.exists()) {
        return AudioSystem.getAudioInputStream(file);
      }

      // give up
      else {
        throw new IllegalArgumentException("could not read '" + filepath + "'");
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("could not read '" + filepath + "'", e);
    }
    catch (UnsupportedAudioFileException e) {
      throw new IllegalArgumentException("file of unsupported audio format: '" + filepath + "'", e);
    }
  }

  public void initialiseLine() {
    try {
      AudioFormat audioFormat = musicToListen.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      line = (SourceDataLine) AudioSystem.getLine(info);
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }
}
