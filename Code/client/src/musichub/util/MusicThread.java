package util;

import business.*;
import java.util.Scanner;

public class MusicThread extends Thread {
  private boolean running = true;
  private Audio audio;

  public MusicThread(Audio audio) {
    this.audio = audio;
  }

  public void run() {
    Scanner scanner = new Scanner (System.in);
    if(audio instanceof Song) {
      System.out.println((Song) audio);
    } else if(audio instanceof AudioBook) {
      System.out.println((AudioBook) audio);
    }
    while(running) {
        System.out.println("Write \"pause\" if you want to pause th music\nWrite \"stop\" if you want to stop the music\n");
        switch(scanner.nextLine()) {
          case "pause":
            break;
          case "stop":
            this.endThread();
            break;
          default:
            System.out.println("This is not a command");
            break;
        }
    }
  }

  public void endThread() {
    this.running = false;
  }
}
