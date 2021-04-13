/*
 * Class' name : ControlMusicList
 *
 * Description : TODO
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package util;

import business.Audio;
import java.net.Socket;
import java.util.LinkedList;

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
  	 * description de la méthode.
  	 *
     * @param audio Audio we want to hear
  	 * @param	port Server's open port
     * @param
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
     * description de la méthode.
     *
     * @param audios Audios we want to hear
     * @param	port Server's open port
     * @param
     *
     * @author	Angélique Proux
     */
    public ControlMusicList(LinkedList<Audio> audioList, int port, Socket socket) {
        this.audioList = audioList;
        this.port = port;
        this.socket = socket;
    }

    public void playMusicList() {
        for (int i=0 ; i < this.audioList.size(); i++) {
            SingletonMusic singletonMusic = SingletonMusic.getInstance();
            singletonMusic.checkInstance();
            singletonMusic.startMusic(audioList.get(i).getContent(),this.port,this.socket);
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

    public void nextMusic() {
            this.nextMusic = true;
    }

    public void previousMusicAskByClient(ObjectInputStream input) {
        if (((String) input.readObject()).equals("previous")) {
            this.previousMusic = true;
        }
    }

}
