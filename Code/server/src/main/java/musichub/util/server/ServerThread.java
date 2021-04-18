/*
 * Class' name : ServerThread
 *
 * Description : Thread to manage the server/client software
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package util.server;

import java.net.*;
import util.*;
import business.*;

/**
 * ServerThread This thread is responsible to handle client connection.
 *
 * Version : 1.0
 *
 * @see       Thread
 * @author    Félicia Ionascu and Steve Chauvreau-Manat
 */
public class ServerThread extends Thread {

    /**
    * TODO
    */
    private Socket socket;

    /**
    * XML editor allowing to read and write XML files
    * @see  JMusicHubModel
    */
    private Model model;

    /**
    * Contains all the methods used by the View
    * @see  JMusicHubController
    */
    private Controller controller;

    /**
     * ServerThread constructor
     *
     * @param     socket TODO
     *
     * @author    Félicia Ionascu and Steve Chauvreau-Manat
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Start the server/client software
     * @see       Thread
     * @author    Félicia Ionascu and Steve Chauvreau-Manat
     */
    public void run() {
        this.model = new JMusicHubModel();
        this.controller = new JMusicHubController(model, socket);
    }
}
