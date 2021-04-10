package util;

import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * AudioServer TODO
 *
 * Version : 1.0
 *
 * @author TODO
 */

public class AudioServer extends Thread {

    /**
     * TODO
     */
    private boolean isRunning;

    /**
     * TODO
     */
    private String content;

    /**
     * TODO
     */
    private int port;

    /**
     * TODO
     */
    private Socket socket;

    /**
     * AudioServer constructor
     *
     * @param       content TODO
     * @param       port TODO
     * @param       socket TODO
     *
     * @author      TODO
     */
    public AudioServer(String content, int port, Socket socket) {
        this.content = content;
        this.port = port;
        this.socket = socket;
        this.isRunning = false;
    }

    /**
     * TODO
     *
     * @see         Thread
     * @author      TODO
     */
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

    /**
     * TODO
     *
     * @return      TODO
     *
     * @author      TODO
     */
    public boolean isRunning() {
        return this.isRunning;
    }
}
