/*
 * Class' name : MusicThread
 *
 * Description : Thread to manage music playback
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Thread to manage music playback
 *
 * @version 1.0
 *
 * @see Thread
 * @author Steve Chauvreau-Manat and Angélique Proux
 */

public class MusicThread extends Thread implements IMusicThread {
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
   * Keep the state of the audio playing
   */
  private boolean finished;

  /**
   * MusicThread constructor
   *
   * @author    Steve Chauvreau-Manat
   */
  public MusicThread() {
  }

  /**
   * set MusicThread parameters
   *
   * @param ip Server's ip
   * @param port Server's open port
   *
   * @author    Angélique Proux
   */
  public void setMusicThread(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  /**
   * Plays the audio until its end or user's action
   * @see         Thread
   * @author      Steve Chauvreau-Manat and Angélique Proux
   */
  public void run() {
    this.finished = false;
    try (Socket socket = new Socket(this.ip,this.port)) {
      if (socket.isConnected()) {
        this.in = new BufferedInputStream(socket.getInputStream());
        AudioInputStream ais = AudioSystem.getAudioInputStream(in);
        this.clip = AudioSystem.getClip();
        this.clip.open(ais);
        this.clip.start();
        while(!Thread.currentThread().isInterrupted()) {
          Thread.sleep(100);
          if(this.clip.getMicrosecondPosition()>=this.clip.getMicrosecondLength()) {
            stopThread();
          }
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
    } catch (InterruptedException e) {
      e.printStackTrace();
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
      this.finished = true;
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
    Thread.currentThread().interrupt();
  }

  /**
   * Returns if the audio is finished or not
   * @author      Angélique Proux
   */
  public boolean isFinished(){
    return this.finished;
  }
}
