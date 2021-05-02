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

public class MockMusicThread extends Thread implements IMusicThread {
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
    public MockMusicThread() {
    }

    /**
     * set MusicThread parameters
     *
     * @param   ip      Server's open port
     * @param   port    Server's ip
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
        try {
            this.in = new BufferedInputStream(new FileInputStream("Default.wav"));
            AudioInputStream ais = AudioSystem.getAudioInputStream(in);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
            this.clip.start();
            while(!Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
                if(isFinished()) {
                    this.clip.stop();
                    this.clip.drain();
                    this.clip.close();
                    stopThread();
                }
            }
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
    }

    /**
     * Restarts the music if it has been paused
     * @author      Steve Chauvreau-Manat
     */
    public void restart() {
    }

    /**
     * Stops the thread and music playback
     * @author      Steve Chauvreau-Manat
     */
    public void stopThread() {
        this.finished = true;
    }

    /**
     * Returns if the audio is finished or not
     * @author      Angélique Proux
     */
    public boolean isFinished(){
        return this.finished;
    }

}
