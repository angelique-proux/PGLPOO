package util;

import java.net.*;
import business.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
  private Socket socket;
  private JMusicHubModel model;
  private JMusicHubController controller;

  public ServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    this.model = new JMusicHubModel();
    this.controller = new JMusicHubController(model, socket);
  }
}
