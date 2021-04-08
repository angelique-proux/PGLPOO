package util;

import business.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JLabel;

public class MusicThread extends Thread {
  private boolean running = true;
  private boolean stopMusic = false;
  private AudioInputStream musicToListen;
  private SourceDataLine line;
  private byte[] samples = new byte[4096];

  public MusicThread(AudioInputStream musicToListen) {
    this.musicToListen = musicToListen;
    this.initialiseLine();
  }

  public void run() {
    int count;
    String label = "Press \'p\' to pause the music\nPress \'p\' a second time to restart the music\nPress \'s\' to stop the music\n\n";
    /*if(this.music.getAudio() instanceof Song) {
      label += (Song) this.music.getAudio();
    } else if(this.music.getAudio() instanceof AudioBook) {
      label += (AudioBook) this.music.getAudio();
    }*/
    //new ConsolMusic(this,new JLabel(label));
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

  public void pause() {
    this.stopMusic = false;
  }

  public void restart() {
    this.stopMusic = true;
  }

  public boolean getStatus() {
    return this.stopMusic;
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
