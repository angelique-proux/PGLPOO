package util;

import business.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;

public class MusicThread extends Thread {
  private int port;
  private String ip;
  private Socket socket;
  private Clip clip;
  private InputStream in;

  public MusicThread(String ip,int port, Socket socket) {
    this.ip = ip;
    this.port = port;
    this.socket = socket;
  }

  public void run() {
    try (Socket socket = new Socket(this.ip,this.port)) {
      if (socket.isConnected()) {
        this.in = new BufferedInputStream(socket.getInputStream());
        AudioInputStream ais = AudioSystem.getAudioInputStream(in);
        this.clip = AudioSystem.getClip();
        Thread.sleep(100);
        this.clip.open(ais);
        this.clip.start();
        while(!Thread.currentThread().isInterrupted()) {
          Thread.sleep(100);
        }
      }
    } catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(UnsupportedAudioFileException uafe) {
      uafe.printStackTrace();
    } catch(LineUnavailableException lue) {
      lue.printStackTrace();
    } catch(InterruptedException ie) {
      ie.printStackTrace();
      Thread.currentThread().interrupted();
    }

    /*int count;
    String label = "Press \'p\' to pause the music\nPress \'p\' a second time to restart the music\nPress \'s\' to stop the music\n\n";
    if(this.music.getAudio() instanceof Song) {
      label += (Song) this.music.getAudio();
    } else if(this.music.getAudio() instanceof AudioBook) {
      label += (AudioBook) this.music.getAudio();
    }
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
    this.line.stop();*/
  }

  public void pause() {
    this.clip.stop();
  }

  public void restart() {
    this.clip.start();
  }

  public void stopThread() {
    this.clip.stop();
    this.clip.drain();
    this.clip.close();
    try {
      this.in.close();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
    Thread.currentThread().interrupt();
  }

  /*public static AudioInputStream getAudioInputStreamFromFile(String filepath) {
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
      AudioFormat audioFormat = getAudioFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      this.line = (SourceDataLine) AudioSystem.getLine(info);
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  private AudioFormat getAudioFormat() {
	   AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	   float sampleRate = 16000.0F;
     int sampleInbits = 16;
     int channels = 1;
     boolean signed = true;
     boolean bigEndian = false;
     return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
   }*/
}
