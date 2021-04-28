/*
 * Class' name : AudioServerThread
 *
 * Description  : Thread to manage music playback
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.musicplayer;

import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Thread to send audio data to the client
 *
 * Version : 1.0
 *
 * @author TODO
 */

public class AudioServerThread extends Thread {
  /**
   * The path to the audio file
   */
  private String content;

  /**
   * Server's open port
   */
  private int port;

  /**
   * Stream to send the audio data to the client
   */
  private OutputStream out;

  /**
   * TODO
   */
  private FileInputStream in;

  /**
   * TODO
   */
  private byte buffer[] = new byte[2048];

  /**
   * AudioServerThread constructor
   *
   * @param       content Audio file path
   * @param       port Open port
   *
   * @author      Steve Chauvreau-Manat
   */
  public AudioServerThread(String content, int port) {
    this.content = content;
    this.port = port;
  }

  /**
   * Opens a socket to send the read data from the audio to the client
   * @see         Thread
   * @author      Steve Chauvreau-Manat and Angélique Proux
   */
  public void run() {
    try(ServerSocket serverSocker = new ServerSocket(this.port);
    this.in = new FileInputStream(new File(this.content))) {
      if(serverSocker.isBound()) {
        Socket socket = serverSocker.accept();
        this.out = socket.getOutputStream();
        int count;
        while(((count = in.read(buffer))!=-1)&&!(Thread.currentThread().isInterrupted())) {
          this.out.write(buffer, 0, count);
        }
      }
    } catch(IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Stops the thread and music playback
   * @author      Steve Chauvreau-Manat
   */
  public void stopThread() {
    try {
      this.out.close();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
    Thread.currentThread().interrupt();
  }

  /**
   * TODO
   * @author      Steve Chauvreau-Manat
   */
  public void changeMusic(String content) {
    this.buffer = new byte[2048];
    try {
      this.in = new FileInputStream(new File(content));
    } catch(IOException ex) {
      ex.printStackTrace();
    }
  }
}
