/*
 * Class' name : ControlMusicList
 *
 * Description : TODO
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import java.net.Socket;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;

/**
 * TODO
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public class ControlMusicList implements ControlMusic {
  /**
   * TODO
   */
  private int port;

  /**
   * TODO
   */
  private String ip;

  /**
   * TODO
   */
  private SingletonMusic singletonMusic;

  /**
   * TODO
   *
   * @param   ip //TODO
   * @param	  port Server's open port
   *
   * @author	Angélique Proux
   */
  public ControlMusicList(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void playMusicList() {
    this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port);
  }


  /**
   * ESSAI
   */
  //private ObjectInputStream input;
  //private ObjectOutputStream output;

  public void playMusicList2(Scanner scan, ObjectInputStream input, ObjectOutputStream output) {
    try {
      System.out.println((String) input.readObject());
      int size = (int) input.readObject();
      int i=0;
      while (i<size) {
        if((boolean) input.readObject()){
          String choix;
          System.out.println("Que voulez-vous faire ?\n (previous/listen/next/end)");
          this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port+1);
          String next = scan.nextLine();
          output.writeObject(next);
          choix = (String) input.readObject();
          switch (choix) {
            case "next":
              this.singletonMusic.stopMusic();
              System.out.println("nouvelle musique");
              i++;
              break;
            case "previous":
              i--;
              this.singletonMusic.stopMusic();
              System.out.println("musique précédente");
              break;
            case "end":
              this.singletonMusic.stopMusic();
              System.out.println("Fin de l'écoute.");
              i=size;
              break;
            case "listen":
              while (this.singletonMusic.isRunning()){
                System.out.println("Enter a command : (play/pause/stop)");
                switch(scan.nextLine()) {
                  case "pause":
                    this.singletonMusic.pauseMusic();
                    break;
                  case "play":
                    this.singletonMusic.restartMusic();
                    break;
                  case "stop":
                    this.singletonMusic.stopMusic();
                    break;
                  default:
                    System.out.println("This is not a command");
                    break;
                }
              }
              i++;
              System.out.println("Ici la musique finit.");
              output.writeObject("Fin de la lecture sur le client.");
              break;
          }

        }
      }
      System.out.println("Fin de la liste.");
    } catch(IOException ioe) {
      ioe.printStackTrace();
    } catch(ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void nextMusic() {
    this.singletonMusic.stopMusic();
    try {
      Thread.sleep(1000);
    } catch(InterruptedException ie) {
      ie.printStackTrace();
    }
    this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port);
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void previousMusic() {
    this.singletonMusic.stopMusic();
    try {
      Thread.sleep(1000);
    } catch(InterruptedException ie) {
      ie.printStackTrace();
    }
    this.singletonMusic = SingletonMusic.getInstance(this.ip,this.port);
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void reset() {
    this.singletonMusic.stopMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void pauseMusic() {
    singletonMusic.pauseMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void restartMusic() {
    singletonMusic.restartMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public void stopMusic() {
    singletonMusic.stopMusic();
  }

  /**
   * TODO
   *
   * @author	Angélique Proux
   */
  public boolean isFinished() {
    return singletonMusic.isRunning();
  }
}
