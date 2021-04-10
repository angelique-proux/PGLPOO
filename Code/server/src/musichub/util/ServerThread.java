package util;

import java.net.*;
import business.*;

/**
 * ServerThread This thread is responsible to handle client connection.
 *
 * Version : 1.0
 *
 * @see Thread
 * @author TODO
 */
public class ServerThread extends Thread {

    /**
    * TODO
    */
    private Socket socket;

    /**
    * TODO
    */
    private JMusicHubModel model;

    /**
    * TODO
    */
    private JMusicHubController controller;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        this.model = new JMusicHubModel();
        this.controller = new JMusicHubController(model, socket);
    }
}
