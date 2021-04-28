/*
 * Class' name : ServerThread
 *
 * Description : Thread to manage the server/client software
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.server;

import java.net.*;
import musichub.util.*;
import musichub.business.*;

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
     * TODO
     */
    private int port;

    /**
     * ServerThread constructor
     *
     * @param     socket TODO
     *
     * @author    Félicia Ionascu and Steve Chauvreau-Manat
     */
    public ServerThread(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    /**
     * Start the server/client software
     * @see       Thread
     * @author    Félicia Ionascu and Steve Chauvreau-Manat
     */
    public void run() {
        Model model = new JMusicHubModel();
        Controller controller = new JMusicHubController(model,this.socket,this.port);
    }
}
