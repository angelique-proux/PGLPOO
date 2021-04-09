package util;

import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioServer extends Thread {
  private boolean isRunning;
  private String content;
  private int port;
  private Socket socket;

  public AudioServer(String content, int port, Socket socket) {
    this.content = content;
    this.port = port;
    this.socket = socket;
    this.isRunning = false;
  }

  public void run() {
    this.isRunning = true;
    try (/*ServerSocket serverSocker = new ServerSocket(this.port);*/
    FileInputStream in = new FileInputStream(new File(this.content))) {
      //if(serverSocker.isBound()) {
        //Socket socket = serverSocker.accept();
        OutputStream out = socket.getOutputStream();
        byte buffer[] = new byte[2048];
        int count;
        while((count = in.read(buffer)) != -1) {
          out.write(buffer, 0, count);
        //}
      }
    } catch(IOException ex) {
      ex.printStackTrace();
    }
    this.isRunning = false;
  }

  public boolean isRunning() {
    return this.isRunning;
  }
}
