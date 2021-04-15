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

package util.musicplayer;

import business.Audio;
import business.Song;

import java.net.Socket;
import java.util.LinkedList;
import java.io.*;

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
    private LinkedList<Audio> audioList;

    /**
     * TODO
     */
    private boolean nextMusic;

    /**
     * TODO
     */
    private boolean previousMusic;

    /**
     * TODO
     */
    private int port;

    /**
     * TODO
     */
    private Socket socket;


    /**
  	 * TODO
  	 *
     * @param   audio Audio we want to hear
  	 * @param	port Server's open port
     * @param   socket
  	 *
  	 * @author	Angélique Proux
  	 */
    public ControlMusicList(Audio audio, int port, Socket socket) {
        this.audioList = new LinkedList<>();
        this.audioList.add(audio);
        this.port = port;
        this.socket = socket;
    }

    /**
     * TODO
     *
     * @param   audioList Audios we want to hear
     * @param   port Server's open port
     * @param   socket
     *
     * @author	Angélique Proux
     */
    public ControlMusicList(LinkedList<Audio> audioList, int port, Socket socket) {
        this.audioList = new LinkedList<>();
        this.audioList = audioList;
        this.port = port;
        this.socket = socket;
    }

    /**
     * TODO
     *
     * @author	Angélique Proux
     */
    public void playMusicList() {
        for (int i=0 ; i < this.audioList.size(); i++) {
            SingletonMusic singletonMusic = SingletonMusic.getInstance(this.audioList.get(i).getContent(), this.port, this.socket);
            if (this.nextMusic) {
                this.nextMusic = false;
                continue;
            }
            if (this.previousMusic) {
                if (i!=0) {
                    i--;
                }
                this.previousMusic = false;
            }
        }
    }

    /**
     * TODO
     *
     * @author	Angélique Proux
     */
    public void nextMusic() {
            this.nextMusic = true;
    }

    /**
     * TODO
     *
     * @param   input
     *
     * @author	Angélique Proux
     */
    public void previousMusicAskByClient(ObjectInputStream input) {
        try {
            if (((String) input.readObject()).equals("previous")) {
                this.previousMusic = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
