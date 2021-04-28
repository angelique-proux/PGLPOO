/*
 * Class' name : MusicThread
 *
 * Description : Thread to manage music playback
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package musichub.util;

import musichub.business.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;

/**
 * Thread to manage music playback
 *
 * @version 1.0
 *
 * @see Thread
 * @author Steve Chauvreau-Manat
 */
public class MusicThread extends Thread {
  /**
   * Server's open port
   */
  private int port;

  /**
   * Server's ip
   */
  private String ip;

  /**
   * Class that manages audio
   */
  private Clip clip;

  /**
   * Stream to read the audio data sent by the server
   */
  private InputStream in;

  /**
   * TODO
   */
  private AudioInputStream ais;

  /**
   * MusicThread constructor
   *
   * @param     ip server's ip
   * @param     port open port for the connection
   *
   * @author    Steve Chauvreau-Manat
   */
  public MusicThread(String ip,int port) {
    this.ip = ip;
    this.port = port;
  }

  /**
  * Audio playback
  *
  * @see         Thread
  * @author      Steve Chauvreau-Manat and Angélique Proux
  */
  public void run() {
    try (Socket socket = new Socket(this.ip,this.port)) {
      if (socket.isConnected()) {
        this.in = new BufferedInputStream(socket.getInputStream());
        this.ais = AudioSystem.getAudioInputStream(in);
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
  }

  /**
  * Pause the music
  * @author      Steve Chauvreau-Manat
  */
  public void pause() {
    this.clip.stop();
  }

  /**
  * Restarts the music if it has been paused
  * @author      Steve Chauvreau-Manat
  */
  public void restart() {
    this.clip.start();
  }

  /**
  * Stops the thread and music playback
  * @author      Steve Chauvreau-Manat
  */
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

  public void changeMusic() {
    this.clip.stop();
    this.clip.drain();
    this.clip.close();
    this.clip = AudioSystem.getClip();
    Thread.sleep(100);
    this.clip.open(ais);
    this.clip.start();
  }
}
